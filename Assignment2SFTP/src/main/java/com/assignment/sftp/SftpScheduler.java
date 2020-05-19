package com.assignment.sftp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.assignment.sftp.model.CSVData;
import com.assignment.sftp.model.FileRecord;
import com.assignment.sftp.service.CSVDataService;
import com.assignment.sftp.service.FileRecordService;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SftpScheduler {

  @Value("${sftp.host}")
  private String host;

  @Value("${sftp.port}")
  private int port;

  @Value("${sftp.user}")
  private String userName;

  @Value("${sftp.password}")
  private String password;

  @Value("${sftp.remote.directory}")
  private String remoteDir;

  @Value("${sftp.local.directory.download}")
  private String localDir;

  @Autowired
  private FileRecordService fileRecordService;

  @Autowired
  private CSVDataService csvDataService;

  private static final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(8);

  @Scheduled(fixedDelay = 900000) // 15 min scheduler
  private void sftpReader() {
    try {
      JSch jsch = new JSch();
      Session session = createSFTPSession(jsch);
      Channel channel = session.openChannel("sftp");
      channel.connect();
      ChannelSftp sftpChannel = (ChannelSftp) channel;
      sftpChannel.cd(remoteDir);

      List<ChannelSftp.LsEntry> fileList = sftpChannel.ls("*.csv");
      // remove already processed files from the list
      List<String> processedFiles = fileRecordService.findAll().stream().map(FileRecord::getFileName)
          .collect(Collectors.toList());
      fileList.removeIf(file -> processedFiles.contains(file.getFilename().trim().toLowerCase()));
      if (fileList.isEmpty()) {
        // No new files to process
        return;
      }
      // create local download directory if not exist
      File localPathDir = new File(localDir);
      if (!localPathDir.exists()) {
        localPathDir.mkdir();
      }
      for (ChannelSftp.LsEntry entry : fileList) {
        String fileName = entry.getFilename();
        String localDownloadFilePath = localDir + "\\" + fileName;
        // Download file to local path
        sftpChannel.get(fileName, localDownloadFilePath);
        log.info("File Downloaded: {}", localDownloadFilePath);
        saveFileRecord(fileName);
        // Parse CSV and save data in thread pool
        executor.submit(() -> {
          parseCSVAndSaveToDB(localDownloadFilePath);
        });
      }
      sftpChannel.exit();
      session.disconnect();
    } catch (JSchException e) {
      log.error("JSchException", e);
    } catch (SftpException e) {
      log.error("SftpException", e);
    }
  }

  private void saveFileRecord(String fileName) {
    FileRecord file = new FileRecord();
    file.setFileName(fileName.toLowerCase().trim());
    fileRecordService.save(file);
  }

  private void parseCSVAndSaveToDB(String fileLocation) {
    try (BufferedReader bufReader = new BufferedReader(new FileReader(fileLocation))) {
      List<CSVData> dataList = new ArrayList();
      parseCSVData(bufReader, dataList);
      // Save csv data to DB
      csvDataService.saveAll(dataList);
    } catch (IOException e) {
      log.error("Exception in parseCSVAndSaveToDB", e);
    }
  }

  private void parseCSVData(BufferedReader bufReader, List<CSVData> dataList) throws IOException {
    String line = null;
    while ((line = bufReader.readLine()) != null) {
      StringTokenizer st = new StringTokenizer(line, ",");
      CSVData csvData = new CSVData();
      csvData.setResultTime(st.nextToken().trim());
      csvData.setGranularityPeriod(Integer.parseInt(st.nextToken().trim()));
      csvData.setObjectName(st.nextToken().trim());
      csvData.setCellId(Integer.parseInt(st.nextToken().trim()));
      csvData.setCallAttemps(Integer.parseInt(st.nextToken().trim()));
      dataList.add(csvData);
    }
  }

  private Session createSFTPSession(JSch jsch) throws JSchException {
    Session session;
    session = jsch.getSession(userName, host, port);
    session.setPassword(password);
    java.util.Properties config = new java.util.Properties();
    config.put("StrictHostKeyChecking", "no");
    session.setConfig(config);
    session.connect();
    return session;
  }
}

package com.assignment.sftp.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.sftp.model.FileRecord;
import com.assignment.sftp.repository.FileRecordRepository;
import com.assignment.sftp.service.FileRecordService;

@Service
public class FileRecordServiceImpl implements FileRecordService {

  @Autowired
  private FileRecordRepository fileRecordRepository;

  @Override
  public void save(FileRecord entity) {
    fileRecordRepository.save(entity);
  }

  @Override
  public void saveAll(List<FileRecord> entities) {
    fileRecordRepository.saveAll(entities);
  }

  @Override
  public void count() {
    fileRecordRepository.count();
  }

  @Override
  public List<FileRecord> findAll() {
    return fileRecordRepository.findAll();
  }

  @Override
  public Optional<FileRecord> findByFileName(String fileName) {
    return fileRecordRepository.findByFileName(fileName.toLowerCase().trim());
  }
}
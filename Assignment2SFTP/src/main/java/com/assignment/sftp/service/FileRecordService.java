package com.assignment.sftp.service;

import java.util.List;
import java.util.Optional;

import com.assignment.sftp.model.FileRecord;

public interface FileRecordService {

  void save(FileRecord entity);

  void saveAll(List<FileRecord> entities);

  void count();

  Optional<FileRecord> findByFileName(String fileName);

  List<FileRecord> findAll();
}
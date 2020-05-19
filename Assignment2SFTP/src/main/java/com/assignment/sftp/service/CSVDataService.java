package com.assignment.sftp.service;

import java.util.List;

import com.assignment.sftp.model.CSVData;

public interface CSVDataService {

  void save(CSVData entity);

  void saveAll(List<CSVData> entities);

}
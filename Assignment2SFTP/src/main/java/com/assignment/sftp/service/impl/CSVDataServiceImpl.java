package com.assignment.sftp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.sftp.model.CSVData;
import com.assignment.sftp.repository.CSVDataRepository;
import com.assignment.sftp.service.CSVDataService;

@Service
public class CSVDataServiceImpl implements CSVDataService {

  @Autowired
  private CSVDataRepository csvDataRepository;

  @Override
  public void save(CSVData entity) {
    csvDataRepository.save(entity);
  }

  @Override
  public void saveAll(List<CSVData> entities) {
    csvDataRepository.saveAll(entities);
  }

}
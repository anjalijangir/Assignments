package com.assignment.sftp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.sftp.model.CSVData;

@Repository
public interface CSVDataRepository extends JpaRepository<CSVData, Long> {

}
package com.assignment.sftp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SftpApp {

  public static void main(String[] args) {
    SpringApplication.run(SftpApp.class, args);
  }

}

# Assignments 2

Design a system (Python/Java) and write the  code  which can download hundreds  of csv files and parse and put them in Database(You can choose mySQL or MongoDB etc). You have been given only one Machine with Linux ( with Multi-core CPU – 8 vCPU) installed. File should be downloaded from remote server at some IP using SFTP.  And remote server allows maximum only one SFTP connection at a time from any remote machine(One IP only One SFTP  connection allowed).
The format of data  in CSV File is given below. And like below there will be thousands of different files. Cell Id and Timestamp would be unique for each row.  Each file name is also unique as illustrated below.

Files will have Names Perf-1-2016-10-21 13:45.csv or  Perf-1-2016-10-21 13:30.csv  i.e. the format of file is   <FileNo>-TimeStamp.csv

Please note that every 15 mins new set of thousand files will come .We need to be selective and only download those new 1000 files which we have not downloaded from a that particular location.    Also, please note that customer tomorrow can increase the files from 1000 to 5000 so it is expected that you design for scalability of the system. Please come up with small Design draft and code.
 

A Sample File csv is pasted below.  There may be thousands of files like this.

Result Time,Granularity Period,Object Name,Cell ID,CallAttemps

2016-10-21 13:45,15,"LIMRNC03/BSC6900UCell:Label=LHU30047c1_Mandingo”,30047,20

2016-10-21 13:45,15,"LIMRNC03/BSC6900UCell:Label=LHU29267c1_Las_Moras”,99267,30

2016-10-21 3:45,15,"LIMRNC03/BSC6900UCell:Label=LHU29277c1_Huanuco_Centro”,29277,40

# Solution

The code is performing follwoing things:

1. Creating a spring scheduler which runs every 15 mins to download the new files
2. Creating a SFTP session
3. Getting file list from sftp remote directory and checking if the file alredy exist in MySQL file_record table.
4. Downloading the unprocessed files and create entry in MySQL file_record table.
5. For each download file create a runnable task to parse the csv file and save the record into the MySQL csv_data table.

 

# How to run

Please update the MySQL and SFTP server configurations before running the application in the application.properties file.
example:

MySQL:

spring.datasource.url=jdbc:mysql://localhost:3306/testdb?useSSL=false 
spring.datasource.username=root 
spring.datasource.password=Test#123 

SFTP:

sftp.host=127.0.0.1 
sftp.port=22 
sftp.user=demo 
sftp.password=password 
sftp.remote.directory= \data 
sftp.local.directory.download= ${java.io.tmpdir}\csvDownload 



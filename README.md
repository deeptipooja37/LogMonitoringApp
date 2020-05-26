"# LogMonitoringApp" Application reads a file which is being written frequently and Monitors the changes on dashboard. Regarding how many log were INFO, ERROR and WARNING in last monitoring interval.

Configure the file path and monitoring interval in src/main/resources/resource/config.properties file. If you want to test this and want to make frequent writes to the file

Provide configuration params in file at location : src/main/resources/resource/config.properties
filePath=<absolute path to log file ex: C:\\log\\log.log   or /user/dir/log.log>
interval=<value>0>
useEmbeddedFile=<true(default) or false> // if true, reads the file embedded within the project and also writes random logs 
  
Steps to run the project :

Step 1
Command to compile package: mvn install 

Step 2
Command to deploy package to embadded tomcat: mvn tomcat7:run 

Step 3
Application is available at: http://localhost:8080/

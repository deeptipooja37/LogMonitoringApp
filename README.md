"# LogMonitoringApp" Application reads a file which is being written frequently and Monitors the changes on dashboard. Regarding how many log were INFO, ERROR and WARNING in last monitoring interval.

For Default configuration, There is a log.file and utility which writes random logs to the log file attached. If you want to test it with some othe file change filePath and set useEmbeddedFile=false


Provide configuration in file at location : src/main/resources/resource/config.properties


filePath=<absolute path to log file>
ex: C:\\log\\log.log   or /user/dir/log.log
  
interval=<10>  
  
useEmbeddedFile=<true(default) or false> 
// if true, reads the file embedded within the project and also writes random logs 
  
  
  
Steps to run the project :

Step 1
Command to compile package
$ mvn install 

Step 2
Command to deploy package to embadded tomcat
$ mvn tomcat7:run 

Step 3
Application is available at: http://localhost:8080/

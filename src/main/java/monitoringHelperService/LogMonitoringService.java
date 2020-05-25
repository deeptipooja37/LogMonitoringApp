package monitoringHelperService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.plaf.synth.SynthSpinnerUI;
import javax.websocket.Session;

import helperServiceTest.WriteToLogFile;
import model.Level;
import model.LogEntry;

/*
 * 		Singleton service for reading and sending message to HTML
 * **/
public class LogMonitoringService{
	
	private static InputStream inputStream;
	private static String targetFilePath;
	private static int interval;
	private final static String PROP_FILE_NAME = "config.properties";
	private RandomAccessFile in;
	private volatile static LogMonitoringService instance;

	public static LogMonitoringService getInstance() {
		if (instance == null) {
			synchronized (LogMonitoringService.class) {
				if (instance == null) {
					instance = new LogMonitoringService();
				}
			}
		}
		return instance;
	}
	private LogMonitoringService(){}
	
	static{
		init();
		
		/*	for using default settings 
		 *  writing to the default file
		 * */
		WriteToLogFile.create();
	}

	public void setInterval(String interval) {
		try {
			
			int monitoringInterval = Integer.parseInt(interval);
			if(monitoringInterval < 0){
				System.out.print("Invalid interval");
			}

			this.interval = monitoringInterval;
		} catch (Exception e) {
			System.out.println("SetInterval failed: Invalid interval passed:");
		}
	}
	private static void init() {
		 try {
		    	Properties prop = new Properties();
				inputStream = LogMonitoringService.class.getClassLoader().getResourceAsStream(PROP_FILE_NAME);
	 
				if (inputStream != null) {
					prop.load(inputStream);
				} else {
					throw new FileNotFoundException("property file '" + PROP_FILE_NAME + "' not found in the classpath");
				}

				String resourcePath = prop.getProperty("filePath"); 
				
				targetFilePath = resourcePath;
				interval = Integer.parseInt(prop.getProperty("interval"));
				
				validateInputParameters();
				
//			    System.out.println(prop.getProperty("filePath"));  
//			    System.out.println(prop.getProperty("interval")); 
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}  
	}

	public static void validateInputParameters()  {
		if(targetFilePath ==null || targetFilePath.length()==0){
			System.out.println("Invalid file path, please provide a valid file path");
			try {
				throw new InvalidConfigurationException();
			} catch (InvalidConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(interval <0){
			System.out.println("Invalid monitoring interval, please provide a valid monitoring interval");
			try {
				throw new InvalidConfigurationException();
			} catch (InvalidConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void readAndPushMessage(Session session) {

		try {

			in = new RandomAccessFile(targetFilePath, "r");
			 String line;
	    	 Set<String> set = new HashSet<String>();

		      while(true) {
		         if((line = in.readLine()) != null) {
		            set.add(line);
		         } else {
		        	 // Window size
		        	 Set<String> trimSet =	 set.stream()
		        	 .filter(e-> {
		        		 return e.length()>0;
		        	 }).collect(Collectors.toSet());
		        	 
		        	 Map<Level, Set<LogEntry>> resultMap =
		        	trimSet.stream()
		        	 .map((row)->{
		        		 return new LogEntry(row);
		        	 })
		        	 .filter(e-> {
		        		 Date now = new Date();
		        		 Calendar calendar=Calendar.getInstance();
		        		 calendar.setTime(now);
		        		 calendar.set(Calendar.SECOND,(calendar.get(Calendar.SECOND)-interval));

		        		 Date startTime = calendar.getTime();
		        		 if (e.getTime().after(startTime) ) {
							return true;
		        		 } 
		        		 return false;
		        	 })
		        	 .collect(Collectors.groupingBy(LogEntry::getSeverity, Collectors.toSet()));
		        	 
		        	 Map<Level, Integer> result =formatResultMap(resultMap);
		        	 StringBuilder sb = new StringBuilder();
		        	 for (Level level : result.keySet()) {
		        		 sb.append(result.get(level)+",");
						System.out.print(level+":"+result.get(level)+" ");
					 }
		        	 System.out.print("interval:"+interval);
					session.getBasicRemote().sendText(sb.toString()+String.valueOf(interval));

		        	 System.out.println();
		        	 Thread.sleep(100); 
		         }
		      }
		} catch (FileNotFoundException e) {
			System.out.println("invalid file path: please give a valid file path\n for windows=> C:\\logs\\log.log \n for linux=> /user/dir/log.log");
		} catch (IOException e) {
			System.out.println("Error while reading file");;
		} catch (InterruptedException e) {
			System.out.println("Thread Sleep Interrupted");
		}
	}

	private Map<Level, Integer> formatResultMap(Map<Level, Set<LogEntry>> resultMap) {
		Map<Level, Integer> result = new HashMap<>();
		Level[] allLevel = new Level[]{Level.ERROR, Level.WARNING, Level.INFO };
		for (int i = 0; i < allLevel.length; i++) {
			Level key = allLevel[i];
			result.put(key, resultMap.containsKey(key)?resultMap.get(key).size():0);
		}
		return result;
	}
	public String getFilePath() {
		return targetFilePath;
	}

	public int getInterval() {
		return interval;
	}


}

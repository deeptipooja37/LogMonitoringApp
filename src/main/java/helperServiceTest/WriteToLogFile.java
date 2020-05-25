package helperServiceTest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import monitoringHelperService.LogMonitoringService;


public class WriteToLogFile {
	static Logger logger = Logger.getLogger("MyLog");  
	 static FileHandler fh;  
	 private static InputStream inputStream;
	private static String targetFilePath;
	private final static String PROP_FILE_NAME = "config.properties";
	private RandomAccessFile in;
	static boolean	useDefaultSetting =true;
	
	public static void create() {  
		
		 try {
		    	Properties prop = new Properties();
				inputStream = WriteToLogFile.class.getClassLoader().getResourceAsStream(PROP_FILE_NAME);
	 
				if (inputStream != null) {
					prop.load(inputStream);
				} else {
					throw new FileNotFoundException("property file '" + PROP_FILE_NAME + "' not found in the classpath");
				}
				
				targetFilePath = prop.getProperty("filePath");
				useDefaultSetting = Boolean.parseBoolean(prop.getProperty("useEmbeddedFile"));

			} catch (IOException e1) {
				e1.printStackTrace();
			}  

		 try {
			fh = new FileHandler(targetFilePath);
			 logger.addHandler(fh);
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}  
	       
    	Runnable runnable = () -> {
    		
    		while(true){
    			
    			 try {
    				 if (useDefaultSetting) {
    					 writeToFile();
    						Thread.sleep(100);
					}
    				
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
	    };

	    Thread thread = new Thread(runnable);
	    thread.start();
	}

	private static void writeToFile() {
		    try {  
		       
		        SimpleFormatter formatter = new SimpleFormatter() {
		            private static final String format =  "%1$tF %1$tT %2$-7s %3$s %n";

		            @Override
		            public synchronized String format(LogRecord lr) {
		                return String.format(format,
		                        new Date(lr.getMillis()),
		                        lr.getLevel().getLocalizedName(),
		                        lr.getMessage()
		                );
		            }
		        };
		        fh.setFormatter(formatter); 

		        // the following statement is used to log any messages 
		        Random rand = new Random(); 
		        ArrayList<String> cList = new ArrayList<String>();
		        cList.add("ERROR");
		        cList.add("WARNING");
		        cList.add("INFO");
		        double randomDouble = Math.random();
		        Random random = new Random();

		        int randomint = random.nextInt(10)%3;

		        String i= cList.get(randomint); 
		        switch (i) {
				case "ERROR":
			        logger.log(Level.SEVERE, "ERROR:  some message"); 
					break;
				case "WARNING":
			        logger.warning("some message");  		
					break;
				case "INFO":
			        logger.info("some message");  
					break;
				default:
					break;
				} 

		       
		    } catch (SecurityException e) {  
		        e.printStackTrace();  
		    } 

	}

}

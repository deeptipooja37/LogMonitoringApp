package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 *  Model used to map each row before computation
 */
public class LogEntry {

	private String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
	private Date time;
	private Level severity;
	
	public Date getTime() {
		return time;
	}

	public Level getSeverity() {
		return severity;
	}
	public LogEntry() {
	
	}

	public LogEntry(String row) {
		String dateString = row.substring(0, 19);
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		try {
			this.time = sdf.parse(dateString);
			if(row.contains("INFO")){
				this.severity=Level.INFO;
			}else if(row.contains("ERROR")){
				this.severity=Level.ERROR;
			}else if(row.contains("WARNING")){
				this.severity=Level.WARNING;
			}
		} catch (ParseException e) {
			System.out.println("Error while parsing log entry");
		}
	}
}

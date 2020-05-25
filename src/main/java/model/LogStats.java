package model;

/*
 * Model class used to send to client
 */
public class LogStats {

	private int errorCount;
	private int warningCount;
	private int infoCount;
	public LogStats(int errorCount, int warningCount, int infoCount) {
		this.errorCount = errorCount;
		this.warningCount = warningCount;
		this.infoCount = infoCount;
	}
	public int getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}
	public int getWarningCount() {
		return warningCount;
	}
	public void setWarningCount(int warningCount) {
		this.warningCount = warningCount;
	}
	public int getInfoCount() {
		return infoCount;
	}
	public void setInfoCount(int infoCount) {
		this.infoCount = infoCount;
	}
	
}

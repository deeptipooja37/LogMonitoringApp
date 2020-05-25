package monitoringHelperService;

public class InvalidConfigurationException extends Exception {

	public InvalidConfigurationException() {
		super("\n Provided invalid input parameters in configuration file\n monitoringInterval>=0 and file path in format(C:\\logs\\log.log<Windows>  or /user/dir/log.log<Linux>)");
		// TODO Auto-generated constructor stub
	}

}

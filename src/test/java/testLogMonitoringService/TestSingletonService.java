package testLogMonitoringService;


import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import monitoringHelperService.InvalidConfigurationException;
import monitoringHelperService.LogMonitoringService;
import monitoringHelperService.OSDetector;

public class TestSingletonService {

	@Rule
	  public final ExpectedException exception = ExpectedException.none();

	
	@Test
	public void testSingletonLogMonitoringService(){
		LogMonitoringService instance1 = LogMonitoringService.getInstance();
		LogMonitoringService instance2 = LogMonitoringService.getInstance();

        boolean isSingolton = instance1==instance2;
		assertEquals(true, isSingolton);

   }
}

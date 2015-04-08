/**
 * 
 */
package logger.tests;

import java.io.File;
import java.io.RandomAccessFile;
import java.time.LocalDateTime;

import logger.LogDate;
import logger.LogDate.LoggingType;
import logger.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rifffish.Transaction;
import rifffish.Rifffish.PaymentMethod;

/**
 * @author Cory Ebner
 *
 */
public class SetTimeLoggerTest {
	final String RIFFFISH_API_KEY = "rsh_rDWPv1x18utNfeDOqmeQrgtt";
	private Logger logger = null;
	private LocalDateTime today = null;
	private int day;
	private int hour;
	private int minute;
	

	@Before
	public void setUp() throws Exception {
		// Local dev testing, API Key will need to be regenerated
		today = LocalDateTime.now();
		day = today.getDayOfMonth();
		hour = today.getHour();
		minute = today.getMinute();
	}


	@After
	public void tearDown() throws Exception {
		logger = null;
		File theFile = new File("LoggerLog.txt");
		File temporaryFileName = new File("temporaryLog.txt");
    	RandomAccessFile temporaryFile= new RandomAccessFile(temporaryFileName , "rw");

    	temporaryFile.close();
    	               
    	if(theFile.exists())
    		theFile.delete();
    	
    	temporaryFileName.renameTo(theFile);
	}

	@Test
	public void DailyLoggerTest(){
		logger = new Logger(RIFFFISH_API_KEY, new LogDate(LoggingType.DAILY, this.day, this.hour, this.minute + 1), 4);
		
		Transaction t = new Transaction(21, PaymentMethod.COIN, true);
		System.out.println("Transaction @ " + t.getTimestamp());
		logger.log(t);
		
		try {
			Thread.currentThread();
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Transaction y = new Transaction(21, PaymentMethod.COIN, true);
		System.out.println("Transaction @ " + y.getTimestamp());
		logger.log(y);
		
		while(logger.getLocalLog().getTransactionsInLocalLog() > 0){
			
		}
	}
}

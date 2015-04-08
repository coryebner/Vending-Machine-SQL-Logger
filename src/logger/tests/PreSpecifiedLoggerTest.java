/**
 * 
 */
package logger.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.RandomAccessFile;

import logger.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import rifffish.Rifffish.PaymentMethod;
import rifffish.Transaction;

/**
 * @author Cory Ebner
 *
 */
public class PreSpecifiedLoggerTest {
	final String RIFFFISH_API_KEY = "rsh_rDWPv1x18utNfeDOqmeQrgtt";
	private Logger logger = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		File theFile = new File("LoggerLog.txt");
		File temporaryFileName = new File("temporaryLog.txt");
    	RandomAccessFile temporaryFile= new RandomAccessFile(temporaryFileName , "rw");

    	temporaryFile.close();
    	               
    	if(theFile.exists())
    		theFile.delete();
    	
    	temporaryFileName.renameTo(theFile);
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Local dev testing, API Key will need to be regenerated
		logger = new Logger(RIFFFISH_API_KEY, 3, 4);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		logger = null;
	}
	
	/**
	 * Test method for pushing locallog to server
	 */
	@Test
	public void testPushLog() {
		for(int i = 0; i < 2; i++){
			Transaction t = new Transaction(21, PaymentMethod.PAYPAL, true);
			System.out.println("Transaction: " + t.timestamp);
			logger.log(t);
		}
		
		Transaction z = new Transaction(21, PaymentMethod.CREDIT_CARD, true);
		System.out.println("Transaction: " + z.timestamp);
		logger.log(z);

		for(int i = 0; i < logger.getThreads().size(); i++){
			try {
				logger.getThreads().get(i).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		logger.log(z);
		assertEquals(null, logger.lastError);
		
	}
}

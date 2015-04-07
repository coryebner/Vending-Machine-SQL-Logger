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
import org.junit.Test;

import rifffish.Problem;
import rifffish.Rifffish.PaymentMethod;
import rifffish.Rifffish.ProblemTypes;
import rifffish.Rifffish.StockoutTypes;
import rifffish.Stockout;
import rifffish.Transaction;

/**
 * @author Cory Ebner
 *
 */
public class LoggerTest {
	final String RIFFFISH_API_KEY = "rsh_rDWPv1x18utNfeDOqmeQrgtt";
	private Logger logger = null;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Local dev testing, API Key will need to be regenerated
		//logger = new Logger(true, 0);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		logger = null;
		File theFile = new File("log.txt");
		File temporaryFileName = new File("temporaryLog.txt");
    	RandomAccessFile temporaryFile= new RandomAccessFile(temporaryFileName , "rw");

    	temporaryFile.close();
    	               
    	theFile.delete();
    	temporaryFileName.renameTo(theFile);
	}

	/**
	 * Test method for {@link logger.Logger#log(rifffish.Transaction)}.
	 */
	@Test
	public void testLogTransaction() {
		logger = new Logger(RIFFFISH_API_KEY, 0, 4);
		Transaction t = new Transaction(21, PaymentMethod.COIN, true);
		System.out.println(t.timestamp);
		logger.log(t);
		assertEquals(null, logger.lastError);
	}

	/**
	 * Test method for problem
	 */
	@Test
	public void testLogProblem() {
		logger = new Logger(RIFFFISH_API_KEY,0, 4);
		Problem t = new Problem(ProblemTypes.FAIL);
		System.out.println(t.timestamp);
		logger.log(t);
		assertEquals(null, logger.lastError);
	}
	
	/**
	 * Test method for pushing locallog to server
	 */
	@Test
	public void testPushLog() {
		logger = new Logger(RIFFFISH_API_KEY, 1, 4);
		Transaction t = new Transaction(1, PaymentMethod.COIN, true);
		System.out.println("Transaction: " + t.timestamp);
		logger.log(t);
		assertEquals(null, logger.lastError);
		
		try {
			Thread.currentThread().sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Test method for {@link logger.Logger#log(rifffish.Transaction)}.
	 */
	@Test
	public void testOfflineLogTransaction() {
		logger = new Logger(4);
		Transaction t = new Transaction(1, PaymentMethod.COIN, true);
		//t.id = 4;
		System.out.println("Transaction happened at: " + t.timestamp);
		logger.log(t);
		assertEquals(null, logger.lastError);
	}

	/**
	 * Test method for problem
	 */
	@Test
	public void testOfflineLogProblem() {
		logger = new Logger(4);
		Problem t = new Problem(ProblemTypes.FAIL);
		t.machine_id = 1;
		System.out.println("Problem:" + t.timestamp);
		logger.log(t);
		assertEquals(null, logger.lastError);
	}
	
	/**
	 * Test method for offline stockout
	 */
	@Test
	public void testOfflineLogStockout() {
		logger = new Logger(4);
		Stockout t = new Stockout(21, StockoutTypes.OUTOFSTOCK);
		System.out.println("Stockout Offline: " + t.timestamp);
		logger.log(t);
		assertEquals(null, logger.lastError);
	}
	
	/**
	 * Test method for stockout
	 */
	@Test
	public void testLogStockout() {
		logger = new Logger(RIFFFISH_API_KEY,0, 4);
		Stockout t = new Stockout(21, StockoutTypes.ALMOSTOUT);
		System.out.println("Stockout: " + t.timestamp);
		logger.log(t);
		assertEquals(null, logger.lastError);
	}
}

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
public class OfflineLoggerTests {
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
		logger = new Logger();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		logger = null;
	}
	
	/**
	 * Test method for {@link logger.Logger#log(rifffish.Transaction)}.
	 */
	@Test
	public void testOfflineLogTransaction() {
		Transaction t = new Transaction(1, PaymentMethod.COIN, true);
		System.out.println("Transaction happened at: " + t.timestamp);
		logger.log(t);
		assertEquals(null, logger.lastError);
	}

	/**
	 * Test method for problem
	 */
	@Test
	public void testOfflineLogProblem() {
		Problem t = new Problem(ProblemTypes.FAIL);
		System.out.println("Problem:" + t.timestamp);
		logger.log(t);
		assertEquals(null, logger.lastError);
	}
	
	/**
	 * Test method for offline stockout
	 */
	@Test
	public void testOfflineLogStockout() {
		Stockout t = new Stockout(21, StockoutTypes.OUTOFSTOCK);
		System.out.println("Stockout Offline: " + t.timestamp);
		logger.log(t);
		assertEquals(null, logger.lastError);
	}
}

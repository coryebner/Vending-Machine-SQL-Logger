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
public class ImmediateLoggerTest {
	final String RIFFFISH_API_KEY = "rsh_rDWPv1x18utNfeDOqmeQrgtt";
	private Logger logger = null;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Local dev testing, API Key will need to be regenerated
		logger = new Logger(RIFFFISH_API_KEY, 0, 4);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		logger = null;
	}
	
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
	 * Test method for {@link logger.Logger#log(rifffish.Transaction)}.
	 */
	@Test
	public void testLogTransaction() {
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
		Problem t = new Problem(ProblemTypes.FAIL);
		System.out.println(t.timestamp);
		logger.log(t);
		assertEquals(null, logger.lastError);
	}
	
	/**
	 * Test method for stockout
	 */
	@Test
	public void testLogStockout() {
		Stockout t = new Stockout(21, StockoutTypes.ALMOSTOUT);
		System.out.println("Stockout: " + t.timestamp);
		logger.log(t);
		assertEquals(null, logger.lastError);
	}
	
	/**
	 * Tests sending pending logs to the server in the event logs are not sent to the server previously
	 */
	@Test
	public void testPendingInstantlogs(){
		Stockout s = new Stockout(21, StockoutTypes.OUTOFSTOCK);
		logger.getLocalLog().printToLocalLog(s);
		Problem p = new Problem(ProblemTypes.FAIL);
		p.machine_id = 4;
		logger.getLocalLog().printToLocalLog(p);
		Transaction t = new Transaction(21, PaymentMethod.COIN, true);
		
		logger.log(t);
		
		for(int i = 0; i < logger.getThreads().size(); i++){
			try {
				logger.getThreads().get(i).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

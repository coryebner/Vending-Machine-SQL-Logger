/**
 * 
 */
package logger.tests;

import static org.junit.Assert.*;
import logger.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rifffish.Problem;
import rifffish.Rifffish;
import rifffish.Transaction;
import rifffish.Rifffish.PaymentMethod;
import schemes.ImmediateScheme;

/**
 * @author Cory Ebner
 *
 */
public class LoggerTest {
	private Logger logger = null;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Local dev testing, API Key will need to be regenerated
		logger = new Logger(true, 0);
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
	public void testLogTransaction() {
		Transaction t = new Transaction(1, PaymentMethod.COIN, true);
		//t.id = 4;
		System.out.println(t.timestamp);
		logger.log(t);
		assertEquals(null, logger.lastError);
	}

//	/**
//	 * Test method for problem
//	 */
//	@Test
//	public void testLogProblem() {
//		Problem t = new Problem("An error occured");
//		System.out.println(t.timestamp);
//		logger.log(t);
//		assertEquals(null, logger.lastError);
//	}
}

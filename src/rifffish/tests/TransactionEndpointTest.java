package rifffish.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import rifffish.Rifffish;
import rifffish.Transaction;
import rifffish.Rifffish.PaymentMethod;

public class TransactionEndpointTest {
	private Rifffish r = null;
	
	@Before
	public void setUp() throws Exception {
		// Local dev testing, API Key will need to be regenerated
		r = new Rifffish("rsh_WTYxjQcwJhF1a26nPibqLwtt", "http://localhost:3000/api");
	}

	@Test
	public void testWellFormedTransactionStatusPassed() {
		System.out.println(r.log(new Transaction(1, 1, PaymentMethod.COIN, true)));
		assertEquals(null, r.log(new Transaction(1, 1, PaymentMethod.COIN, true)));
	}
	
	@Test
	public void testWellFormedTransactionStatusFailed() {
		assertEquals(null, r.log(new Transaction(1, 1, PaymentMethod.COIN, false)));
	}
	
	@Test
	public void testMalformedTransactionStatusPassed() {
		assertEquals("Error -> 400 - Bad Request. Transaction Malformed.", 
				(r.log(new Transaction(99999999, 9999999, PaymentMethod.COIN, true))).toString());
	}
	
	@Test
	public void testMalformedTransactionStatusFailed() {
		assertEquals("Error -> 400 - Bad Request. Transaction Malformed.", 
				(r.log(new Transaction(99999999, 9999999, PaymentMethod.COIN, false))).toString());
	}
	
	@Test
	public void testNegativeMalformedTransactionStatusFailed() {
		assertEquals("Error -> 400 - Bad Request. Transaction Malformed.", 
				(r.log(new Transaction(-1, -1, PaymentMethod.COIN, false))).toString());
	}
	
	@Test
	public void testPartialTransactionStatusFailed() {
		assertEquals("Error -> 400 - Bad Request. Transaction Malformed.", 
				(r.log(new Transaction(null, null, PaymentMethod.COIN, false))).toString());
	}
	
	@Test
	public void testBadAPIKey() {
		// Local dev testing, API Key will need to be regenerated
		r = new Rifffish("BAD_KEY", "http://localhost:3000/api");
		assertEquals("Error -> 400 - Bad Request. Transaction Malformed.", 
				(r.log(new Transaction(null, null, PaymentMethod.COIN, false))).toString());
	}
	
	@Test
	public void testNonWorkingURL() {
		// Local dev testing, API Key will need to be regenerated
		r = new Rifffish("rsh_WTYxjQcwJhF1a26nPibqLwtt", "http://localhostErrorURL/api");
		assertEquals("Error -> 400 - Bad Request. Transaction Malformed.", 
				(r.log(new Transaction(null, null, PaymentMethod.COIN, false))).toString());
	}

}

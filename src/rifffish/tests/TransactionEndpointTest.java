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
		r = new Rifffish("rsh_enkfnekfne", "http://localhost:3000/api");
	}

	@Test
	public void testWellFormedTransactionStatusPassed() {
		assertEquals(null, r.log(new Transaction(1, 1, PaymentMethod.COIN, true)));
	}
	
	@Test
	public void testWellFormedTransactionStatusFailed() {
		assertEquals(null, r.log(new Transaction(1, 1, PaymentMethod.COIN, false)));
	}
	
	@Test
	public void testWellMalformedTransactionStatusPassed() {
		assertEquals(null, r.log(new Transaction(99999999, 9999999, PaymentMethod.COIN, true)));
	}
	
	@Test
	public void testWellMalformedTransactionStatusFailed() {
		assertEquals(null, r.log(new Transaction(99999999, 9999999, PaymentMethod.COIN, false)));
	}

}

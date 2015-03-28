package rifffish;

import rifffish.Rifffish.PaymentMethod;

/**
 * 
 * @author tamcgoey
 *
 */
public class Transaction {
	public Integer id = null;
	public Integer machine_id = null;
	public Integer product_id = null;
	public Integer quantity = null;
	public Integer payment_method = null;
	public Boolean status = null;
	public String timestamp = null;
	public Object error = null;
	
	private boolean validated = false;
	
	public Transaction(Integer MachineId, Integer ProductId, PaymentMethod PaymentMethod, Boolean PaymentStatus) {
		machine_id = MachineId;
		product_id = ProductId;
		payment_method = parsePaymentMethod(PaymentMethod);
				
	}
	
	private Integer parsePaymentMethod(PaymentMethod pMethod) {
		switch (pMethod) {
			case COIN: 
				return 1;
				
			case CREDIT_CARD:				
				return 2;
				
			default:
				return 0;
		}
	}
}


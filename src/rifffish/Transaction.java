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
	
	/**
	 * Transaction Object. All params are required.
	 * See API/SDK documentation for more information
	 * 
	 * @param MachineId, Integer from the Machine ID
	 * @param ProductId, Integer from the Product ID
	 * @param PaymentMethod, ENUM of a PaymentMethod see API for more details
	 * @param PaymentStatus, Boolean of whether the payment was completed or not
	 */
	public Transaction(Integer MachineId, Integer ProductId, PaymentMethod PaymentMethod, Boolean PaymentStatus) {
		machine_id = MachineId;
		product_id = ProductId;
		payment_method = parsePaymentMethod(PaymentMethod);
		quantity = 1;
		status = PaymentStatus;	
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


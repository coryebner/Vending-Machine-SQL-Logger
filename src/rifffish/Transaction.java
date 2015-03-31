package rifffish;

import rifffish.Rifffish.PaymentMethod;

/**
 * Represents a Transaction Object
 * (converted from json to a Java object - gson)
 */
public class Transaction {
	public Integer id = null;
	public Integer product_id = null;
	public Integer quantity = null;
	public String payment_method = null;
	public Boolean status = null;
	public String timestamp = null;
	public Object error = null;
	
	/**
	 * Transaction Object. All params are required.
	 * See API/SDK documentation for more information
	 * 
	 * @param ProductId, Integer from the Product ID
	 * @param PaymentMethod, ENUM of a PaymentMethod see API for more details
	 * @param PaymentStatus, Boolean of whether the payment was completed or not
	 */
	public Transaction(Integer ProductId, PaymentMethod PaymentMethod, Boolean PaymentStatus) {
		product_id = ProductId;
		payment_method = parsePaymentMethod(PaymentMethod);
		quantity = 1;
		status = PaymentStatus;	
	}
	
	private String parsePaymentMethod(PaymentMethod pMethod) {
		switch (pMethod) {
			case COIN: 
				return "coins";
				
			case CREDIT_CARD:				
				return "credit_cards";
				
			default:
				return null;
		}
	}
}


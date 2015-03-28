import rifffish.*;
import rifffish.Rifffish.PaymentMethod;


public class Main {
	public static void main(String[] args) {
		Rifffish r = new Rifffish("rsh_WTYxjQcwJhF1a26nPibqLwtt");
				
		Transaction t = new Transaction(1, 1, PaymentMethod.COIN, true);
		t.machine_id = 1;
		t.product_id = 1;
		t.quantity = 1;
		t.payment_method = 2;
		t.status = true;
		r.log(t); 		
	}
}

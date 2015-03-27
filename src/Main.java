import rifffish.*;


public class Main {
	public static void main(String[] args) {
		Rifffish r = new Rifffish("rsh_zft5hPlzHLAHfLHmJzeekwtt");
				
		Transaction t = new Transaction();
		t.machine_id = 1;
		t.product_id = 1;
		t.quantity = 1;
		t.payment_method = 2;
		t.status = true;
		r.log(t); 		
	}
}

import rifffish.*;


public class Main {
	public static void main(String[] args) {
		Rifffish r = new Rifffish("rsh_pANlj9XQGDg7y2lNrSUIQgtt", "1");
				
		Transaction t = new Transaction();
		t.machine_id = 2;
		t.product_id = 2;
		t.quantity = 1;
		t.payment_method = 4;
		t.status = true;
		
		r.log(t); 		
	}
}

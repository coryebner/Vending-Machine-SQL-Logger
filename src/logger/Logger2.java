package logger;

import rifffish.Transaction;

/**
 * 
 * @author cory
 * 
 * The client side log functions.
 *
 */

public class Logger2{
	
	private String scheme;
	private int vendingMachineID;
	
	
	/**
	 * Constructor for log
	 * 
	 * @param Scheme How often to send logs to the server. Options = OFFLINE, IMMEDIATLY, PRESPECIFIED, SETTIME
	 */
	
	Logger2(String Scheme, String Args[]){
		// Check if scheme is valid
		if(Scheme.equals("OFFLINE")){
			
		}
		else if(Scheme.equals("IMMEDIATLY")){
			
		}
		else if(Scheme.equals("PRESPECIFIED")){
			
		}
		else if(Scheme.equals("SETTIME")){
			
		}
		else{
			//error
		}
		
		// get VendingMachineID from the server
	}
	
	//Log for transactions
	public void Log(Transaction t){
		
	}
	
	//Log for Out of Service
	
	
	//Log for problem
}
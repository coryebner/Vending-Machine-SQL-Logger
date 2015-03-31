package Logger;

import Schemes.ImmediateScheme;
import Schemes.OfflineScheme;
import Schemes.PrespecifiedScheme;
import Schemes.SetTimeScheme;
import rifffish.Transaction;

/**
 * 
 * @author cory
 * 
 * The client side log functions.
 *
 */

public class Logger{
	
	private Object scheme;
	private int vendingMachineID;
	
	/**
	 * Creates a logger that uses a default Offline logging scheme
	 */
	Logger(){
		this.scheme = new OfflineScheme();
	}
	
	/**
	 * Creates a logger that uses an Offline logging scheme
	 */
	Logger(OfflineScheme scheme){
		this.scheme = new OfflineScheme();
	}
	
	/**
	 * Creates a logger that uses an immediate logging scheme to a remote server
	 * 
	 * @param Scheme How often to send logs to the server. Options = OFFLINE, IMMEDIATLY, PRESPECIFIED, SETTIME
	 */
	Logger(ImmediateScheme scheme){
		this.scheme = scheme;
		
		// get VendingMachineID from the server
	}
	
	/**
	 * Creates a logger that sends logs to a remote server after a set amount of transactions
	 * 
	 * @param scheme
	 */
	Logger(PrespecifiedScheme scheme){
		this.scheme = scheme;
		
	}
	
	/**
	 * Creates a Logger that sends the logs to a remote server at a set time
	 * 
	 * @param scheme
	 */
	Logger(SetTimeScheme scheme){
		this.scheme = scheme;
	}
	
	/**
	 * Logs transactions
	 * 
	 * @param t
	 */
	public void Log(Transaction t){
		
	}
	
//	/**
//	 * Logs an out of service
//	 * 
//	 * @param o
//	 */
//	public void Log(OutOfService o){
//		
//	}
//	
//	/**
//	 * Logs a problem
//	 * 
//	 * @param p
//	 */
//	public void Log(Problem p){
//		
//	}
}
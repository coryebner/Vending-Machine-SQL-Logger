package logger;

import localLog.io.LocalLog;
import localLog.io.LocalLogReader;
import localLog.io.LocalLogWriter;
import rifffish.Error;
import rifffish.Problem;
import rifffish.Rifffish;
import rifffish.Transaction;

/**
 * 
 * @author cory
 * 
 * The client side log functions for interactiing with the rifffish database.
 *
 */

public class Logger{
	
	private static final String RIFFFISH_API_URL = "http://rifffish.com/api";
	private static final String API_KEY = "rsh_rDWPv1x18utNfeDOqmeQrgtt";
	private int vendingMachineID;
	private Rifffish r = null;
	public Error lastError = null;
	private int numberOfTransactions = 0;
	public boolean threadRunning = false;
	private LogDate date = null;
	private LocalLog localLog;
	
	/**
	 * Creates a logger that uses a default Offline logging scheme
	 */
	public Logger(){
		this(false, -1);
	}
	
	/**
	 * Creates a logger that sends logs to a remote server after a set amount of transactions
	 * 
	 * @param numberOfTransactions the number of transactions that need to occur before they are sent to the server. 0 = immediately
	 */
	public Logger(boolean internetEnabled, int numberOfTransactions){
		localLog = new LocalLog();
		
		if(internetEnabled){
			r = new Rifffish(API_KEY, RIFFFISH_API_URL);
			this.numberOfTransactions = numberOfTransactions;
		}else{
			this.numberOfTransactions = -1;
		}
	}
	
	/**
	 * Creates a Logger that sends the logs to a remote server at a set time
	 * 
	 * @param internetEnabled, true/false
	 * @param date the set time logging scheme to use
	 */
	public Logger(boolean internetEnabled, LogDate date){
		localLog = new LocalLog();
		
		if(internetEnabled){
			r = new Rifffish(API_KEY, RIFFFISH_API_URL);
			
		}else{
			this.numberOfTransactions = -1;
		}
	}
	
	/**
	 * Log for Transactions 
	 * Logs a Transaction to our API
	 * @param transaction, A transaction that is being logged
	 * @return Error, returns null when transaction was logged successfully, 
	 * 		   else returns an Error (which could be parsed or just printed)
	 */
	public void log(Transaction t) {
		
		if (date == null) {
			if(numberOfTransactions == -1 || numberOfTransactions > 0){
				LocalLogWriter w1 = new LocalLogWriter(localLog,t);
				w1.start();
			}else if (numberOfTransactions == 0) {
				// Send to server
				lastError = r.log(t);

				// Add transaction to the local log because it didn't get sent to the server
				if (lastError != null) {
					LocalLogWriter w1 = new LocalLogWriter(localLog,t);
					w1.start();
				}
			}
			
			if(localLog.getNumLines() >= numberOfTransactions && numberOfTransactions > 0){
				//Read from file and send each line to the server
				LocalLogReader r1 = new LocalLogReader(r, localLog);
				r1.start();
			}
		}
 	}
	
	/**
	 * Log for Problems 
	 * Logs a Problem to our API
	 * @param Problem, A problem that is being logged
	 * @return Error, returns null when problem was logged successfully, 
	 * 		   else returns an Error (which could be parsed or just printed)
	 */
	public void log(Problem t) {		
		if (numberOfTransactions != -1) {
			// Send to server
			lastError = r.log(t);

			// Add Problem to the local log because it didn't get sent to the server
			if (lastError != null) {
				LocalLogWriter w1 = new LocalLogWriter(localLog,t);
				w1.start();
			}
		}else{
			LocalLogWriter w1 = new LocalLogWriter(localLog,t);
			w1.start();
		}
 	}
}

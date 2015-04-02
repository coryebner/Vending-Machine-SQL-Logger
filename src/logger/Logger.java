package logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;

import localLogging.LocalLog;
import retrofit.RestAdapter;
import rifffish.Error;
import rifffish.Problem;
import rifffish.Rifffish;
import rifffish.Transaction;
import rifffish.endpoints.TransactionsService;

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
	private int currentTransactions = 0;
	public boolean threadRunning = false;
	private LogDate date = null;
	
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
		new LocalLog().printToLocalLog(t);
		
		if (date == null) {
			if (numberOfTransactions == 0) {
				System.out.println("sending to server");

				// TODO: Send to server

				lastError = r.log(t);

				// TODO: Add error to the local log
				if (lastError != null) {
					System.out.println(lastError);
					new LocalLog().printToLocalLog(new Problem(lastError.toString()));
				}
			}else if(currentTransactions >= numberOfTransactions){
				//TODO: Read from file and send each line to the server
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
		new LocalLog().printToLocalLog(t);

		System.out.println("sending to server");

		// TODO: Send to server

		lastError = r.log(t);

		// TODO: Add error to the local log
		if (lastError != null) {
			System.out.println(lastError);
			new LocalLog().printToLocalLog(new Problem(lastError.toString()));
		}
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

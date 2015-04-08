package logger;

import java.util.ArrayList;
import localLog.io.LocalLog;
import localLog.io.LocalLogReader;
import localLog.io.LocalLogWriter;
import rifffish.Error;
import rifffish.Problem;
import rifffish.Rifffish;
import rifffish.Stockout;
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
	private int vendingMachineID;
	private Rifffish r = null;
	public Error lastError = null;
	private int numberOfTransactions = 0;
	private LogDate date = null;
	private LocalLog localLog;
	ArrayList<Thread> threads = new ArrayList<Thread>();
	
	/**
	 * Creates a logger that uses a default Offline logging scheme
	 */
	public Logger(){
		this(null, -1, 0);
	}
	
	/**
	 * Creates a logger that sends logs to a remote server after a set amount of transactions. 
	 * Not guaranteed to log on the exact specified transaction due to threading.
	 * 
	 * @param numberOfTransactions the number of transactions that need to occur before they are sent to the server. 0 = immediately sent to the server
	 * @param internetEnabled true/false
	 */
	public Logger(String rifffish_api_key, int numberOfTransactions, int machineId){
		localLog = new LocalLog();
		vendingMachineID = machineId;
		
		if(rifffish_api_key != null){
			r = new Rifffish(rifffish_api_key, RIFFFISH_API_URL);
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
	public Logger(String rifffish_api_key, LogDate date, int machineId){
		localLog = new LocalLog();
		vendingMachineID = machineId;
		this.date = date;
		
		if(rifffish_api_key != null){
			r = new Rifffish(rifffish_api_key, RIFFFISH_API_URL);
			if(date != null){
				SetTimeLogger s1 = new SetTimeLogger(r, localLog, date);
				s1.start();
			}
			
		}else{
			this.numberOfTransactions = -1;
		}
	}
	
	/**
	 * Log for Transactions 
	 * Logs a Transaction to our API
	 * @param t, A transaction that is being logged
	 */
	public void log(Transaction t) {
		t.id = vendingMachineID;
		
		if(numberOfTransactions == -1 || numberOfTransactions > 0){
			LocalLogWriter w1 = new LocalLogWriter(localLog,t);
			threads.add(w1);
			w1.start();
		}
		
		if (date == null) {
			if (numberOfTransactions == 0) {
				// Send to server
				lastError = r.log(t);

				// Add transaction to the local log because it didn't get sent to the server
				if (lastError != null) {
					LocalLogWriter w1 = new LocalLogWriter(localLog,t);
					threads.add(w1);
					w1.start();
				}
			}
			
			processWaitingLogs();
		}
		else{
			LocalLogWriter w1 = new LocalLogWriter(localLog,t);
			threads.add(w1);
			w1.start();
		}
 	}
	
	/**
	 * Log for Problems 
	 * Logs a Problem to our API
	 * @param t A problem that is being logged
	 */
	public void log(Problem t) {
		t.machine_id = vendingMachineID;
		
		if (numberOfTransactions != -1) {
			// Send to server
			lastError = r.log(t);

			// Add Problem to the local log because it didn't get sent to the server
			if (lastError != null) {
				LocalLogWriter w1 = new LocalLogWriter(localLog,t);
				threads.add(w1);
				w1.start();
			}
			processWaitingLogs();
		}else{
			LocalLogWriter w1 = new LocalLogWriter(localLog,t);
			threads.add(w1);
			w1.start();
		}
 	}
	
	/**
	 * Log for Stockouts 
	 * Logs a Stockout to our API
	 * @param t  A Stockout that is being logged
	 */
	public void log(Stockout t) {		
		if (numberOfTransactions != -1) {
			// Send to server
			lastError = r.log(t);

			// Add Stockout to the local log because it didn't get sent to the server
			if (lastError != null) {
				LocalLogWriter w1 = new LocalLogWriter(localLog,t);
				threads.add(w1);
				w1.start();
			}
			processWaitingLogs();
		}else{
			LocalLogWriter w1 = new LocalLogWriter(localLog,t);
			threads.add(w1);
			w1.start();
		}
 	}
	
	private void processWaitingLogs(){
		if((localLog.getTransactionsInLocalLog() >= numberOfTransactions) && (numberOfTransactions != -1)){
			//Read from file and send each line to the server
			LocalLogReader r1 = new LocalLogReader(r, localLog);
			threads.add(r1);
			r1.start();
		}
	}

	/**
	 * @return the localLog
	 */
	public LocalLog getLocalLog() {
		return localLog;
	}

	/**
	 * @return the threads
	 */
	public ArrayList<Thread> getThreads() {
		return threads;
	}
}

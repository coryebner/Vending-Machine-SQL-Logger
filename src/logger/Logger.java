package logger;

import java.sql.Timestamp;

import org.hamcrest.core.IsInstanceOf;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RequestInterceptor.RequestFacade;
import rifffish.Error;
import rifffish.Rifffish;
import rifffish.Transaction;
import rifffish.endpoints.TransactionsService;
import schemes.ImmediateScheme;
import schemes.OfflineScheme;
import schemes.PrespecifiedScheme;
import schemes.SetTimeScheme;

/**
 * 
 * @author cory
 * 
 * The client side log functions for interactiing with the rifffish database.
 *
 */

public class Logger{
	
	private static final String RIFFFISH_API_URL = "http://rifffish.com/api";
	private static final String API_KEY = "rsh_WTYxjQcwJhF1a26nPibqLwtt";
	private Object scheme;
	private int vendingMachineID;
	private RestAdapter restAdapter = null;
	private Rifffish r = null;
	
	/**
	 * Creates a logger that uses a default Offline logging scheme
	 */
	Logger(){
		this.scheme = new OfflineScheme();
		r = new Rifffish(API_KEY, RIFFFISH_API_URL);
	}
	
	/**
	 * Creates a logger that uses an Offline logging scheme
	 */
	Logger(OfflineScheme scheme){
		this.scheme = new OfflineScheme();
		r = new Rifffish(API_KEY, RIFFFISH_API_URL);
	}
	
	/**
	 * Creates a logger that uses an immediate logging scheme to a remote server
	 * 
	 * @param Scheme How often to send logs to the server. Options = OFFLINE, IMMEDIATLY, PRESPECIFIED, SETTIME
	 */
	Logger(ImmediateScheme scheme, String apiKey){
		this.scheme = scheme;
		r = new Rifffish(API_KEY, RIFFFISH_API_URL);
		
		//TODO: get VendingMachineID from the server
	}
	
	/**
	 * Creates a logger that sends logs to a remote server after a set amount of transactions
	 * 
	 * @param scheme
	 */
	Logger(PrespecifiedScheme scheme, String apiKey){
		this.scheme = scheme;
		r = new Rifffish(API_KEY, RIFFFISH_API_URL);
		
	}
	
	/**
	 * Creates a Logger that sends the logs to a remote server at a set time
	 * 
	 * @param scheme
	 */
	Logger(SetTimeScheme scheme, String apiKey){
		this.scheme = scheme;
		r = new Rifffish(API_KEY, RIFFFISH_API_URL);
	}
	
	/**
	 * Log for Transactions 
	 * Logs a Transaction to our API
	 * @param transaction, A transaction that is being logged
	 * @return Error, returns null when transaction was logged successfully, 
	 * 		   else returns an Error (which could be parsed or just printed)
	 */
	public void log(Transaction t) {
		Error error = null;
		
		//TODO add transaction to the local log
		
		if(scheme instanceof ImmediateScheme){
			sendTransaction(t);
			
		}else if(scheme instanceof PrespecifiedScheme){
			
		}else if(scheme instanceof SetTimeScheme){
			
		}
		
		//TODO: Add error to the local log
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
	
	private Error sendTransaction(Transaction t){
		Error error = null;
		
		TransactionsService service = restAdapter.create(TransactionsService.class);		
		
		try {
			service.createTransaction(t);
		} catch(Exception e) {
			error = new Error("400 - Bad Request. Transaction Malformed.");
		}
		
		return error;
	}
}
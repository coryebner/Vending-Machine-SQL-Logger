package logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RequestInterceptor.RequestFacade;
import rifffish.Error;
import rifffish.Problem;
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
	
	private static final String RIFFFISH_API_URL = "http://rifffish.com/";
	private static final String API_KEY = "rsh_rDWPv1x18utNfeDOqmeQrgtt";
	private Object scheme;
	private int vendingMachineID;
	private RestAdapter restAdapter = null;
	private Rifffish r = null;
	public Error lastError = null;
	
	/**
	 * Creates a logger that uses a default Offline logging scheme
	 */
	public Logger(){
		this.scheme = new OfflineScheme();
		r = new Rifffish(API_KEY, RIFFFISH_API_URL);
	}
	
	/**
	 * Creates a logger that uses an Offline logging scheme
	 */
	public Logger(OfflineScheme scheme){
		this.scheme = new OfflineScheme();
		r = new Rifffish(API_KEY, RIFFFISH_API_URL);
	}
	
	/**
	 * Creates a logger that uses an immediate logging scheme to a remote server
	 * 
	 * @param Scheme An immediate scheme which sends logs to the server immediately
	 */
	public Logger(ImmediateScheme scheme){
		this.scheme = scheme;
		r = new Rifffish(API_KEY, RIFFFISH_API_URL);
		restAdapter = r.getRestAdapter();
		
		//TODO: get VendingMachineID from the server
	}
	
	/**
	 * Creates a logger that sends logs to a remote server after a set amount of transactions
	 * 
	 * @param scheme
	 */
	public Logger(PrespecifiedScheme scheme){
		this.scheme = scheme;
		r = new Rifffish(API_KEY, RIFFFISH_API_URL);
		
	}
	
	/**
	 * Creates a Logger that sends the logs to a remote server at a set time
	 * 
	 * @param scheme
	 */
	public Logger(SetTimeScheme scheme){
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
		printToLocalLog(t);
		
		if(scheme instanceof ImmediateScheme){
			System.out.println("sending to server");
			
			// TODO: Send to server
			
			lastError = sendTransaction(t);
			
			//TODO: Add error to the local log
			if(lastError != null){
				System.out.println(lastError);
				printToLocalLog(new Problem(lastError.toString()));
			}
			
			
		}else if(scheme instanceof PrespecifiedScheme){
			
		}else if(scheme instanceof SetTimeScheme){
			
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
	
	/**
	 * 
	 * @param t Transaction Object
	 * @return error
	 */
	private Error sendTransaction(Transaction t){
		Error error = null;
		
		TransactionsService service = restAdapter.create(TransactionsService.class);		
		
		try {
			service.createTransaction(t);
		} catch(Exception e) {
			error = new Error("400 - Bad Request. Transaction Malformed.");
			System.out.println(e);
		}
		
		return error;
	}
	
	/**
	 * Prints a transaction to a local log file
	 * 
	 * @param t a transaction object
	 */
	private void printToLocalLog(Transaction t){
		  try(    FileWriter fw = new FileWriter("log.txt", true);
		          BufferedWriter bw = new BufferedWriter(fw);
		          PrintWriter out = new PrintWriter(bw)){

			  out.println(t.getId() + "," + t.getProduct_id() + "," + t.getQuantity() + "," + t.getPayment_method() + "," + t.getStatus() + "," + t.getTimestamp() + "," + t.getError());

		  }  
		  catch( IOException e ){
		      System.out.println("Failed to print to file");// File writing/opening failed at some stage.
		  }
	}
	
	/**
	 * Prints a Problem to a local log file
	 * 
	 * @param t a Problem object
	 */
	private void printToLocalLog(Problem t){
		  try(    FileWriter fw = new FileWriter("log.txt", true);
		          BufferedWriter bw = new BufferedWriter(fw);
		          PrintWriter out = new PrintWriter(bw)){

			  out.println(t.getId() + "," + t.getDescription() + "," + t.getTimestamp());

		  }  
		  catch( IOException e ){
		      // File writing/opening failed at some stage.
		  }
	}
}

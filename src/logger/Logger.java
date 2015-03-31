package logger;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RequestInterceptor.RequestFacade;
import rifffish.Transaction;
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
	private Object scheme;
	private int vendingMachineID;
	private RestAdapter restAdapter = null;
	
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
	Logger(ImmediateScheme scheme, String apiKey){
		this.scheme = scheme;
		
		// get VendingMachineID from the server
	}
	
	/**
	 * Creates a logger that sends logs to a remote server after a set amount of transactions
	 * 
	 * @param scheme
	 */
	Logger(PrespecifiedScheme scheme, String apiKey){
		this.scheme = scheme;
		
	}
	
	/**
	 * Creates a Logger that sends the logs to a remote server at a set time
	 * 
	 * @param scheme
	 */
	Logger(SetTimeScheme scheme, String apiKey){
		this.scheme = scheme;
	}
	
	public void Rifffish(final String API_TOKEN, String API_URL) {
		RequestInterceptor requestInterceptor = new RequestInterceptor() {
			@Override
				public void intercept(RequestFacade request) {
					request.addHeader("Content-Type", "application/json");
					request.addHeader("X-RIFFFISH-TOKEN", API_TOKEN);
				}
			};
			
		restAdapter = new RestAdapter.Builder()
	    .setEndpoint(API_URL)
	    .setRequestInterceptor(requestInterceptor)
	    .build();
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
package rifffish;

import java.sql.Timestamp;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import rifffish.endpoints.ProblemService;
import rifffish.endpoints.TransactionsService;

public class Rifffish {
	private RestAdapter restAdapter = null;
	private final static String RIFFFISH_API_URL = "http://rifffish.com/api";
	
	public static enum PaymentMethod {COIN, CREDIT_CARD};
	
	/**
	 * Rifffish is a powerful way to manage your vending machines
	 * See API/SDK documentation for more information
	 * 
	 * @param API_TOKEN, found in profile page under API keys
	 */
	public Rifffish(final String API_TOKEN) {
		this(API_TOKEN, RIFFFISH_API_URL);		
	}
	
	public Rifffish(final String API_TOKEN, String API_URL) {
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
	 * Log for Transactions 
	 * Logs a Transaction to our API
	 * @param transaction, A transaction that is being logged
	 * @return Error, returns null when transaction was logged successfully, 
	 * 		   else returns an Error (which could be parsed or just printed)
	 */
	public Error log(Transaction t) {
		Error error = null;
		
		TransactionsService service = restAdapter.create(TransactionsService.class);		
		
		try {
			service.createTransaction(t);
		} catch(Exception e) {
			error = new Error("400 - Bad Request. Transaction Malformed.");
		}
		
		return error;
 	}
	
	/**
	 * Log for Problems
	 * Logs a Problem to our API
	 * @param problem, A problem that is being logged
	 * @return Error, returns null when problem was logged successfully, 
	 * 		   else returns an Error (which could be parsed or just printed)
	 */
	public Error log(Problem p) {
		Error error = null;
		
		ProblemService service = restAdapter.create(ProblemService.class);		
		
		try {
			service.createProblem(p);
		} catch(Exception e) {
			System.out.println(e);
			error = new Error("400 - Bad Request. Problem Malformed.");
		}
		
		return error;
 	}
}

package rifffish;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import rifffish.endpoints.TransactionsService;

public class Rifffish {
	private RestAdapter restAdapter = null;
	private final static String RIFFFISH_API_URL = "http://localhost:3000/api";
	
	private String machineId = "-1";
	
	public Rifffish(final String API_TOKEN, String MACHINE_ID) {
		RequestInterceptor requestInterceptor = new RequestInterceptor() {
			@Override
				public void intercept(RequestFacade request) {
					request.addHeader("Authorization", "Token token=\"" + API_TOKEN + "\"");
					request.addHeader("Content-Type", "application/json");
				}
			};
			
		restAdapter = new RestAdapter.Builder()
	    .setEndpoint(RIFFFISH_API_URL)
	    .setRequestInterceptor(requestInterceptor)
	    .build();
		
		this.machineId = MACHINE_ID;
	}
	
	public void log(Transaction t) {
		TransactionsService service = restAdapter.create(TransactionsService.class);
		service.createTransaction(t);
	}
	
	public void returnLast25Transactions() {
		TransactionsService service = restAdapter.create(TransactionsService.class);
		Transactions t = service.transactions();
		
		System.out.println(t.transactions.size());
		
 	}
	
	
}

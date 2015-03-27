package rifffish;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import rifffish.endpoints.TransactionsService;

public class Rifffish {
	private RestAdapter restAdapter = null;
	private final static String RIFFFISH_API_URL = "http://localhost:3000/api";
		
	public Rifffish(final String API_TOKEN) {
		RequestInterceptor requestInterceptor = new RequestInterceptor() {
			@Override
				public void intercept(RequestFacade request) {
					request.addHeader("X-RIFFFISH-TOKEN", API_TOKEN);
				}
			};
			
		restAdapter = new RestAdapter.Builder()
	    .setEndpoint(RIFFFISH_API_URL)
	    .setRequestInterceptor(requestInterceptor)
	    .build();		
	}
	
	public void log(Transaction t) {
		TransactionsService service = restAdapter.create(TransactionsService.class);
		Transaction tOut = service.createTransaction(t);
		
		System.out.println(tOut.id);
		
		System.out.println(tOut.error);
		
 	}
	

	
}

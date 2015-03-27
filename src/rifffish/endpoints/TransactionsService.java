package rifffish.endpoints;


import retrofit.http.Body;
import retrofit.http.POST;
import rifffish.Transaction;

public interface TransactionsService {	
	@POST("/log/transactions")
	Transaction createTransaction(@Body Transaction transaction);
}

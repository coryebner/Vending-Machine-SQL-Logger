package rifffish.endpoints;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import rifffish.Transaction;
import rifffish.Transactions;

public interface TransactionsService {
	@Headers({"Authorization: Token token=\"rsh_pANlj9XQGDg7y2lNrSUIQgtt\"",
			 "Content-Type: application/json"})
	
	@GET("/log/transactions/{transaction_id}")
	Transaction transactionById(@Path("transaction_id") String transaction_id);
	
	@GET("/log/transactions")
	Transactions transactions();
	
	@POST("/log/transactions")
	Transaction createTransaction(@Body Transaction transaction);
	
}

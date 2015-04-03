package rifffish.endpoints;


import retrofit.http.Body;
import retrofit.http.POST;
import rifffish.Stockout;

public interface StockoutService {	
	@POST("/log/stockouts")
	Stockout createStockout(@Body Stockout stockout);
}

package rifffish.endpoints;


import retrofit.http.Body;
import retrofit.http.POST;
import rifffish.Problem;

public interface ProblemService {	
	@POST("/log/problems")
	Problem createTransaction(@Body Problem problem);
}

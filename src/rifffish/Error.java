package rifffish;

/**
 * An Error Object
 * Errors are not always exceptional
 * 
 * Supports System.Out printing
 *
 */
public class Error {
	private String errorMessage = "";
	
	public Error(String error) {
		errorMessage = error;
	}
	
	public String toString() {
		return "Error -> " + errorMessage;
	}
}

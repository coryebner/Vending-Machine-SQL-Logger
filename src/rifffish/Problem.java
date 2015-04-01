package rifffish;

import rifffish.Rifffish.PaymentMethod;

/**
 * Represents a Problem Object
 * (converted from json to a Java object - gson)
 */
public class Problem {
	public Integer id = null;
	public String description;
	public String timestamp = null;
	
	/**
	 * Transaction Object. All params are required.
	 * See API/SDK documentation for more information
	 * 
	 * @param description a brief description of the problem
	 */
	public Problem(String description) {
		this.description = description;	
	}
	
}


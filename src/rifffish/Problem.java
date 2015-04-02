package rifffish;

import java.sql.Timestamp;

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
		java.util.Date date = new java.util.Date();
		this.timestamp = (new Timestamp(date.getTime())).toString();
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}
	
}


package schemes;

public class Scheme{
	private String type;
	private int batchNumber = 0;
	private int day;
	private int hour;
	private int minute;
	private enum Schemes { OFFLINE, IMMEDIATLY, PRESPECIFIED, SETTIME}
	
	/**
	 * Creates a Offline or immediate type logging scheme
	 * 
	 * @param ConfigType OFFLINE or IMMEDIATLY
	 */
	Scheme(String ConfigType){
		if(ConfigType.equals("Offline"))
			type = "Offline";
		else if( ConfigType.equals("IMMEDIATLY"))
			type = "IMMEDIATLY";
		else
			type = null;//change to Error
	}
	
	/**
	 * Creates a PRESPECIFIED batch logger
	 * 
	 * @param Configtype PRESPECIFIED
	 * @param number How many logged messages to be stored before they are sent to the server
	 */
	Scheme(String Configtype, int number){
		if(Configtype.equals("PRESPECIFIED")){
			type = "PRESPECIFIED";
		}
		else
			type = null; //Change to error
	}
	
	Scheme(String ConfigType, int day, int hour, int minute){
		if(ConfigType.equals("SETTIME"))
			type = "SETTIME";
		else
			type = null; //Change to error
	}
}
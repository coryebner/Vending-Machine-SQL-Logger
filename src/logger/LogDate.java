package logger;

public class LogDate{
	private int day;
	private int hour;
	private int minute;
	private LoggingType type;
	public static enum LoggingType {DAILY, WEEKLY, MONTHLY};
	
	/**
	 * Date object to store info needed for Set time logging
	 * 
	 * @param type A LoggingType enum
	 * @param startDay Day to send logs to the server. All future logs are based on this date. Valid values: 1-31
	 * @param startHour Hour to send the logs to the server; Valid values: 0-23
	 * @param startMinute Minute to send the logs to the server; 0-59
	 */
	public LogDate(LoggingType type, int startDay, int startHour, int startMinute){
		this.type = type;
		this.day = startDay;
		this.hour = startHour;
		this.minute = startMinute;
	}

	public int getDay() {
		return day;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	public String getType() {
		String result = null;
		
		if(type.equals(LoggingType.DAILY))
			result = "DAILY";
		else if(type.equals(LoggingType.WEEKLY))
			result = "WEEKLY";
		else if(type.equals(LoggingType.MONTHLY))
			result = "MONTHLY";
		
		return result;
	}


}
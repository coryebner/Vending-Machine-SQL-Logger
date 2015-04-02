package logger;

public class LogDate{
	private int day;
	private int hour;
	private int minute;
	private LoggingType type;
	private static enum LoggingType {Daily, WEEKLY, MONTHLY};
	
	public LogDate(LoggingType type, int startDay, int startHour, int startMinute){
		this.type = type;
		this.day = startDay;
		this.hour = startHour;
		this.minute = startMinute;
	}
}
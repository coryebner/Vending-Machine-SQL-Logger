package logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;

import localLog.io.LocalLog;
import rifffish.Rifffish;

public class SetTimeLogger extends Thread{
	private LocalLog log;
	private Rifffish r;
	private Calendar CurrentCal;
	private Calendar cal;
	private LocalDateTime today;
	private LocalDateTime nextBatch;
	private String type;
	private LogDate date;

	public SetTimeLogger(Rifffish r, LocalLog log, LogDate date) {
		this.log = log;
		this.r = r;
		this.cal = Calendar.getInstance();
		this.CurrentCal= Calendar.getInstance();
		this.type = date.getType();
		this.date = date;
		
		//cal.set(CurrentCal.YEAR, CurrentCal.MONTH, date.getDay(), date.getHour(), date.getMinute());
		
		today = LocalDateTime.now();
		nextBatch = LocalDateTime.of(today.getYear(), today.getMonthValue(), date.getDay(), date.getHour(), date.getMinute());
		
		if(nextBatch.isBefore(today)){
			getNextBatchTime(nextBatch, date);
		}
	}
	
	public void run(){
		while(true){	
			if(nextBatch.isBefore(today)|| nextBatch.isEqual(today)){
				log.pushLocalLog(r);
				getNextBatchTime(nextBatch, date);	
			}
			else{
				long sleeptime = today.until(nextBatch, ChronoUnit.MILLIS);
				try {
					currentThread();
					Thread.sleep(sleeptime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}		
		}
	}
	
	private LocalDateTime getNextBatchTime(LocalDateTime currentBatchTime, LogDate date){
		LocalDateTime nextBatch;
		
		if (type.equals("DAILY"))
			nextBatch = currentBatchTime.plusDays(1);
		else if (type.equals("WEEKLY"))
			nextBatch = currentBatchTime.plusDays(7);
		else
			nextBatch = currentBatchTime.plusMonths(1);
		
		return nextBatch;
	}
}
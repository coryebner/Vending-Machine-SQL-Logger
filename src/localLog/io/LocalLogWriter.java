package localLog.io;

import rifffish.Problem;
import rifffish.Transaction;

public class LocalLogWriter extends Thread{
	private Object toLog;
	private LocalLog log;

	public LocalLogWriter(LocalLog log, Transaction t) {
		this.toLog = t;
		this.log = log;
	}
	
	public LocalLogWriter(LocalLog log, Problem p){
		this.toLog = p;
		this.log = log;
	}
	
	public void run(){
		if(toLog instanceof Transaction)
			log.printToLocalLog((Transaction)toLog);
		else if(toLog instanceof Problem)
			log.printToLocalLog((Transaction)toLog);
	}
}
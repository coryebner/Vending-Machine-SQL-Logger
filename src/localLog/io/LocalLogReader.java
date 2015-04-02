package localLog.io;

import rifffish.Problem;
import rifffish.Rifffish;
import rifffish.Transaction;

public class LocalLogReader extends Thread{
	private LocalLog log;
	private Rifffish r;

	public LocalLogReader(Rifffish r, LocalLog log) {
		this.log = log;
		this.r = r;
	}
	
	public void run(){
		log.pushLocalLog(r);
		System.out.println("thread " + Thread.currentThread().getName() + "finished pushLocalLog");
	}
}
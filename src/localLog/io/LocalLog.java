package localLog.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

import com.sun.swing.internal.plaf.synth.resources.synth;

import rifffish.Problem;
import rifffish.Rifffish;
import rifffish.Transaction;

public class LocalLog{
	private boolean available = false;
	
	/**
	 * Prints a transaction to a local log file
	 * 
	 * @param t a transaction object
	 */
	public synchronized void printToLocalLog(Transaction t){
		 while (available == true) {
	         try {
	            wait();
	         }
	         catch (InterruptedException e) { 
	         } 
	      }
		
		  try(    FileWriter fw = new FileWriter("log.txt", true);
		          BufferedWriter bw = new BufferedWriter(fw);
		          PrintWriter out = new PrintWriter(bw)){

			  out.println("Transaction," + t.getId() + "," + t.getProduct_id() + "," + t.getQuantity() + "," + t.getPayment_method() + "," + t.getStatus() + "," + t.getTimestamp() + "," + t.getError());

		  }  
		  catch( IOException e ){
		      System.out.println("Failed to print to file");// File writing/opening failed at some stage.
		  }
		  
		  available = true;
		  notifyAll();
	}
	
	/**
	 * Prints a Problem to a local log file
	 * 
	 * @param t a Problem object
	 */
	 public synchronized void printToLocalLog(Problem t){
		 while (available == true) {
	         try {
	            wait();
	         }
	         catch (InterruptedException e) { 
	         } 
	      }
		 
		  try(    FileWriter fw = new FileWriter("log.txt", true);
		          BufferedWriter bw = new BufferedWriter(fw);
		          PrintWriter out = new PrintWriter(bw)){

			  out.println("Problem," + t.getId() + "," + t.getDescription() + "," + t.getTimestamp());

		  }  
		  catch( IOException e ){
		      // File writing/opening failed at some stage.
		  }
		  
		  available = true;
		  notifyAll();
	}
	 
	/**
	 *  Sends any local logs to the server
	 *  
	 * @param r Riffish object
	 */
	public synchronized void pushLocalLog(Rifffish r){
		Error error = null;
		
		while (available == false) {
	         try {
	            wait();
	         }
	         catch (InterruptedException e) { 
	         } 
	      }
		
		try {
			RandomAccessFile file =  new RandomAccessFile("log.txt", "rws");
			    String line;
			    String[] split;
			    while ((line = file.readLine()) != null) {
			       // process the line.
			    	split = line.split(",");
			    	if(split[0].equals("Transaction")){
			    		Transaction t = new Transaction(Integer.parseInt(split[2]), r.valueOf(split[3]), Boolean.valueOf(split[4]));
			    		t.id = Integer.parseInt(split[1]);
			    		t.timestamp = split[6];
			    		r.log(t);
			    	}else if(split[0].equals("Problem")){
			    		Problem p = new Problem(split[2]);
			    		p.timestamp = split[3];
			    		p.machine_id = Integer.parseInt(split[1]);
			    		r.log(p);
			    	}
			    	
			    	//if the message was sent to the server successfully remove the line from the file
			    	if(error == null){
			    		// Shift remaining lines upwards.
			    	    long writePos = file.getFilePointer();
			    	    file.readLine();
			    	    long readPos = file.getFilePointer();

			    	    byte[] buf = new byte[1024];
			    	    int n;
			    	    while (-1 != (n = file.read(buf))) {
			    	        file.seek(writePos);
			    	        file.write(buf, 0, n);
			    	        readPos += n;
			    	        writePos += n;
			    	        file.seek(readPos);
			    	    }

			    	    file.setLength(writePos);

			    	}
			    	file.close();
			    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		available = false;
		notifyAll();
	}
}
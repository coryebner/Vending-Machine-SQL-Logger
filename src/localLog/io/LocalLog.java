package localLog.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

import rifffish.Problem;
import rifffish.Rifffish;
import rifffish.Stockout;
import rifffish.Transaction;

public class LocalLog{
	private boolean available = false;
	private static final String LOG_FILENAME = "LoggerLog.txt";
	
	/**
	 * Prints a transaction to a local log file
	 * 
	 * @param t a transaction object
	 */
	public synchronized int printToLocalLog(Transaction t){
		int result = 0;
		
		  try(    FileWriter fw = new FileWriter(LOG_FILENAME, true);
		          BufferedWriter bw = new BufferedWriter(fw);
		          PrintWriter out = new PrintWriter(bw)){

			  out.println("Transaction," + t.getId() + "," + t.getProductId() + "," + t.getQuantity() + "," + t.getPaymentMethod() + "," + t.getStatus() + "," + t.getTimestamp() + "," + t.getError());

		  }  
		  catch( IOException e ){
		      System.out.println("Failed to print to file");// File writing/opening failed at some stage.
		      result = -1;
		  }
		  
		  available = true;
		  notifyAll();
		  
		return result;
	}
	
	/**
	 * Prints a Stockout to a local log file
	 * 
	 * @param t a Stockout object
	 */
	 public synchronized int printToLocalLog(Stockout t){
		int result = 0;

		 
		  try(    FileWriter fw = new FileWriter(LOG_FILENAME, true);
		          BufferedWriter bw = new BufferedWriter(fw);
		          PrintWriter out = new PrintWriter(bw)){

			  out.println("Stockout," + t.getId() + "," + t.getDescription() + "," + t.getTimestamp());

		  }  
		  catch( IOException e ){
		      // File writing/opening failed at some stage.
				result = -1;
		  }
		  
		  available = true;
		  notifyAll();
		return result;
	}
	 
		/**
		 * Prints a Problem to a local log file
		 * 
		 * @param t a Problem object
		 */
		 public synchronized int printToLocalLog(Problem t){
			int result = 0;
			 
			  try(    FileWriter fw = new FileWriter(LOG_FILENAME, true);
			          BufferedWriter bw = new BufferedWriter(fw);
			          PrintWriter out = new PrintWriter(bw)){

				  out.println("Problem," + t.getId() + "," + t.getDescription() + "," + t.getTimestamp());

			  }  
			  catch( IOException e ){
			      // File writing/opening failed at some stage.
				  result = -1;
			  }
			  
			  available = true;
			  notifyAll();
			return result;
		}
	 
	/**
	 *  Sends any local logs to the server. NOT SAFE.
	 *  
	 * @param r Riffish object
	 */
	public synchronized int pushLocalLog(Rifffish r){
		int result = 0;
		Error error = null;
		
		try {
			File theFile = new File(LOG_FILENAME);
			RandomAccessFile file =  new RandomAccessFile(theFile, "rw");
			    String line;
			    String[] split;	            
			    while ((line = file.readLine()) != null && error == null) {
			    	
			       // process the line.
			    	split = line.split(",");
			    	if(split[0].equals("Transaction")){
			    		Transaction t = new Transaction(Integer.parseInt(split[3]), r.valueOfPayment(split[4]), Boolean.valueOf(split[5]));
			    		t.id = Integer.parseInt(split[2]);
			    		t.timestamp = split[6];
			    		r.log(t);
			    	}else if(split[0].equals("Problem")){
			    		Problem p = new Problem(r.valueOfProblem(split[2]));
			    		p.timestamp = split[3];
			    		p.machine_id = Integer.parseInt(split[1]);
			    		r.log(p);
			    	}else if(split[0].equals("Stockout")){
			    		Stockout p = new Stockout(Integer.parseInt(split[1]), r.valueOfStockout(split[2]));
			    		p.timestamp = split[3];
			    		r.log(p);
			    	}			    	
			    	
			    }
			    file.close();
			    
			    System.out.println("Sent logs to server" + error);
			    
			    if(error == null){
			    	File temporaryFileName = new File("temporaryLog.txt");
			    	RandomAccessFile temporaryFile= new RandomAccessFile(temporaryFileName , "rw");

			    	temporaryFile.close();
			    	               
			    	theFile.delete();
			    	temporaryFileName.renameTo(theFile);
			    }
			    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = -1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = -1;
		}

		available = false;
		notifyAll();
		return result;
	}
	
	/**
	 * Returns the number of lines in the log file
	 * 
	 * @return the number of lines in the file
	 */
	public synchronized int getNumLines(){
		int result = 0;
		
		try {
			LineNumberReader  lnr = new LineNumberReader(new FileReader(new File(LOG_FILENAME)));
			lnr.skip(Long.MAX_VALUE);
			result = lnr.getLineNumber();
			lnr.close();
		} catch (FileNotFoundException e) {
			result=0;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}
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
	 
	public synchronized void pushLocalLog(){
		try {
			RandomAccessFile file =  new RandomAccessFile("log.txt", "rws");
			    String line;
			    while ((line = file.readLine()) != null) {
			       // process the line.
			    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
}
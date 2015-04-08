/**
 * 
 */
package logger.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.RandomAccessFile;

import logger.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rifffish.Problem;
import rifffish.Rifffish.PaymentMethod;
import rifffish.Rifffish.ProblemTypes;
import rifffish.Rifffish.StockoutTypes;
import rifffish.Stockout;
import rifffish.Transaction;

/**
 * @author Cory Ebner
 *
 */
public class SetTimeLoggerTest {
	final String RIFFFISH_API_KEY = "rsh_rDWPv1x18utNfeDOqmeQrgtt";
	private Logger logger = null;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Local dev testing, API Key will need to be regenerated
		//logger = new Logger(true, 0);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		logger = null;
		File theFile = new File("LoggerLog.txt");
		File temporaryFileName = new File("temporaryLog.txt");
    	RandomAccessFile temporaryFile= new RandomAccessFile(temporaryFileName , "rw");

    	temporaryFile.close();
    	               
    	if(theFile.exists())
    		theFile.delete();
    	
    	temporaryFileName.renameTo(theFile);
	}

	
}

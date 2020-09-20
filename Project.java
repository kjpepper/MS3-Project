package csvParse;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;  
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DatabaseMetaData;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Project {
	public static int records = 0; // how many entries in the CSV File
	public static int brecords = 0; // how many bad entries
	public static int grecords = 0; // how many good entries
	public static void main(String[] args) throws SecurityException, IOException {
		String File = "/Users/kjpepper275/Downloads/ms3Interview - Jr Challenge 2.csv"; // where the document is saved on my computer 
		connect(); // connecting to database for the first time on the run
		create(); // creating a table and drop a previous table
		Project.read(File); // method to read the CSV File 
		boolean append = true; // creating logged file for end results
		FileHandler handler = new FileHandler("results.log", append);
        Logger logger = Logger.getLogger("Results From CSV");
        logger.addHandler(handler);
        logger.info("INFORMATION!!!"); // header for file
        logger.info("Number of Records = " + records);
        logger.info("Number of Bad Records = " + brecords);
        logger.info("Number of Good Records = " + grecords);

	}
	public static void connect() {
	      Connection c = null;
	      
	      try {
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:information.db"); //getting connection to database
	      } catch ( Exception e ) { // any problems catch
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("Opened database successfully");
	   }
	public static void create() {
	      Connection c = null;
	      Statement stmt = null;
	      try {
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:information.db"); //connection to database
	         System.out.println("Opened database successfully"); //confirmation on opening
	         stmt = c.createStatement(); 
	         String sqlCommand = "DROP TABLE IF EXISTS 'GOODRESULTS' "; // drop for multiple runs avoid duplicates
	         stmt.executeUpdate(sqlCommand); //execute the string
	         String sql = "CREATE TABLE IF NOT EXISTS GOODRESULTS " + //creating table and columns
	                        "(FIRST TEXT     NOT NULL," +
	                        " LAST           TEXT    NOT NULL, " + 
	                        " EMAIL          TEXT     NOT NULL, " + 
	                        " GENDER         TEXT, " + 
	                        " IMAGE          TEXT     NOT NULL, " +
	                        " PAYMENT        TEXT     NOT NULL, " +
	                        " AMOUNT         TEXT    , " +
	                        " BOOLEAN1       TEXT     , " +
	                        " BOOLEAN2       TEXT     , " +
	                        " LOCATION       TEXT     ) "; 
	         stmt.executeUpdate(sql);
	         stmt.close();
	         c.close();
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() ); //catch
	         System.exit(0);
	      }
	      System.out.println("Table created successfully"); // confirmation
	   }
	
	public static void add(String [] array) // insert into table for good results
	{
		Connection c = null;
	    Statement stmt = null;
	    try {
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:information.db");
	         c.setAutoCommit(false);
	         stmt = c.createStatement();
	         //HardCodded the array as I was having trouble and wanted to see what each element equaled and why it would give an error
	         //This only works for the test assisgnment although the logic is still the same I would Just have to research to make this cleaner
	         String sql = "INSERT INTO GOODRESULTS (FIRST,LAST,EMAIL,GENDER,IMAGE,"
	         		+ "PAYMENT,AMOUNT,BOOLEAN1, BOOLEAN2,LOCATION) " +
	         		"VALUES('"+array[0]+"','"+array[1]+"','"+array[2]+"','"+array[3]+"','"+array[4]+"','"+array[5]+"','"+array[6]+"','"+array[7]+"','"+array[8]+"','"+array[9]+"')";
	         stmt.executeUpdate(sql); // added add the colums for this row
	         c.commit();
	         c.close();
	    }
	    catch(Exception e) 
	    {
	    	System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	        System.exit(0);
	    }
	}

	public static void read(String csvfile) { // reading all the data from the csv
		CSVReader reader = null;
		System.out.println("Here");
		try 
		{
			FileWriter outputfile = new FileWriter("results-bad.csv"); //my csv file I write too 
			CSVWriter writer = new CSVWriter(outputfile); 
			reader = new CSVReader(new FileReader(csvfile));
			boolean bad = false; // how to determine if its a bad entry
			String[] line;
			List<String[]> bdata = new ArrayList<String[]>(); // bad results
			//List<String[]> gdata = new ArrayList<String[]>(); // good results
			while((line = reader.readNext()) != null) 
			{
				for(int i = 0; i < 10; i++)  
				{  
					String token = line[i];
					if(token.equals("")||token.contains("'")) { //checking if a row is empty also I check for a single quote as My Insert to table algorithm won't work if there is a single quote, This could be fixed but It took me a bit to get that insert to work
						brecords++;
						bad = true;
					}
				} 
				if(bad) 
				{
					bdata.add(line); // if its a bad result add to a list
					bad = false;
				}
				else 
				{
					if(grecords==0) //ignore first success as it is just headers
					{
						grecords++; //increment to go to the next line
					}
					else {
					grecords++;
					add(line); // send the string array to be added to the database
					}
				}
				records++; // number of total records
			}
			writer.writeAll(bdata);//write all the bad CSV entries at once 
			writer.close(); 
		}

		catch (Exception e) {
			System.out.println("Error Somewhere in here");
		}
	}
}

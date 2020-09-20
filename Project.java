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
		//"/Users/kjpepper275/Documents/book1.csv"
		//"/Users/kjpepper275/Downloads/ms3Interview - Jr Challenge 2.csv"
		connect(); // connecting to database
		create(); // creating a table if needed
		Project.read(File); // method to read the CSV File 
		boolean append = true;
		FileHandler handler = new FileHandler("results.log", append);
        Logger logger = Logger.getLogger("Results From CSV");
        logger.addHandler(handler);
        logger.info("INFORMATION!!!");
        logger.info("Number of Records = " + records);
        logger.info("Number of Bad Records = " + brecords);
        logger.info("Number of Good Records = " + grecords);

	}
	public static void connect() {
	      Connection c = null;
	      
	      try {
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:information.db");
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("Opened database successfully");
	   }
	public static void create() {
	      Connection c = null;
	      Statement stmt = null;
	    /*  try {
		         Class.forName("org.sqlite.JDBC");
		         c = DriverManager.getConnection("jdbc:sqlite:information.db");
		         System.out.println("Opened database successfully");
		         String sql1 = "DROP TABLE GOODRESULTS ";
		         stmt.executeUpdate(sql1);
	      }catch ( Exception e ) {
		         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		         System.exit(0);
		      } */
	      try {
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:information.db");
	         System.out.println("Opened database successfully");
	         stmt = c.createStatement();
	         String sqlCommand = "DROP TABLE IF EXISTS 'GOODRESULTS' ";
	         stmt.executeUpdate(sqlCommand);
	         String sql = "CREATE TABLE IF NOT EXISTS GOODRESULTS " +
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
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("Table created successfully");
	   }
	
	public static void add(String [] array) // insert into table
	{
		Connection c = null;
	    Statement stmt = null;
	    try {
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:information.db");
	         c.setAutoCommit(false);
	         stmt = c.createStatement();
	         //System.out.println(array[0]);
	         //System.out.println(array[9]);
	         String sql = "INSERT INTO GOODRESULTS (FIRST,LAST,EMAIL,GENDER,IMAGE,"
	         		+ "PAYMENT,AMOUNT,BOOLEAN1, BOOLEAN2,LOCATION) " +
	         		"VALUES('"+array[0]+"','"+array[1]+"','"+array[2]+"','"+array[3]+"','"+array[4]+"','"+array[5]+"','"+array[6]+"','"+array[7]+"','"+array[8]+"','"+array[9]+"')";
	         stmt.executeUpdate(sql);
	         c.commit();
	         c.close();
	    }
	    catch(Exception e) 
	    {
	    	System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	        System.exit(0);
	    }
	}

	public static void read(String csvfile) {
		CSVReader reader = null;
		System.out.println("Here");
		try 
		{
			FileWriter outputfile = new FileWriter("results-bad.csv"); 
			CSVWriter writer = new CSVWriter(outputfile); 
			reader = new CSVReader(new FileReader(csvfile));
			boolean bad = false;
			String[] line;
			List<String[]> bdata = new ArrayList<String[]>(); // bad results
			//List<String[]> gdata = new ArrayList<String[]>(); // good results
			while((line = reader.readNext()) != null) 
			{
				for(int i = 0; i < 10; i++)  
				{  
					String token = line[i];
					if(token.equals("")||token.contains("'")) {
						brecords++;
						bad = true;
					}
				} 
				if(bad) 
				{
					bdata.add(line);
					bad = false;
				}
				else 
				{
					if(grecords==0) 
					{
						grecords++;
					}
					else {
					grecords++;
					//gdata.add(line);
					add(line);
					}
				}
				records++;
			}
			writer.writeAll(bdata);
			writer.close(); 
		}

		catch (Exception e) {
			System.out.println("Error Somewhere in here");
		}
	}
}

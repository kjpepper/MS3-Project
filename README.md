# MS3-Project
//Kristopher Pepper

Summary:

The Java Project collects a CSV document as user input, then reads the document, inputs all valid entries into a database while taking bad entries into a separate CSV file, and counts all the records.
To get the script to run, download and make sure you have a valid file path for the CSV File that you have. You will also need to use the external libraries of OPENCSV-3.8.jar and also sqljdbc.jar.

Overview

I began by creating a method to read in the CSV document.  The sample document given to me is quite large with 10 columns so I made my own testing CSV first and started to use OPENCSV which is an external library used as a CSV parser, I used that to read the document and then I use CSVWriter to create my CSV document which I store the bad entries on. Once I got that working I switched to the CSV document that was provided, I only had one issue and that’s that my method kept thinking the document had 15 columns (there was only 10) so I hard coded in a line so that it stops at 10 (so this might not work for other CSV document inputs). 

The way my method detects if there is a valid entry or not, is it just checks for a white space (“”) although later on I ended up adding a statement to also check for a single quote (‘) as my insert database method would throw an error, so I just added that as bad data. The bad data gets flagged and inputted into a list of arrays and my program writes the array list to a csv at the end of the method

If the data doesn’t get flagged as ‘bad’ my code sends the line of the csv to an insert method as a string array.  The method connects to the database and adds the array to a table of “GOODRESULTS”.  My program counts all the records as well as the bad and good results separately and prints out a log file and the end of processing.

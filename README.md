# MS3-Project
//Kristopher Pepper

Summary:

The Java Project collects a CSV document as user input, then reads the document, inputs all valid entries into a database while taking bad entries into a separate CSV file, and counts all the records.
To get the script to run, download and make sure you have a valid file path for the CSV File that you have. You will also need to use the external libraries of OPENCSV-3.8.jar and also sqljdbc.jar.

Overview

I began by creating a method to read the CSV document.  Due to the sample document being large, I decided to make my own testing CSV first. Then started to use OPENCSV, an external library used as a CSV parser. I utilized OPENCSV to read the document, and implemented CSVWriter to create my CSV document and store bad entries.Once I got it working, I switched to the CSV document provided. I had one issue which is my method kept thinking there were 15 columns and there were only 10 columns. I hard coded in a line so it stops at 10, so this may not work for other CSV document inputs. 

My method detects both valid and entries and non-valid entries by checking for white space (“”). I also added a statement to check for a single quote (‘) as my insert database method would give an error if it was detected. The bad data then gets flagged and inputted into a list of arrays, and my program writes the array list to a CSV at the end of the method. 

If the data doesn’t get flagged as ‘bad’, my code sends the line of the CSV to an insert method as a string array.  The method connects to the database and adds the array to a table of “GOODRESULTS”.  My program counts all the records as well as the bad and good results separately and prints out a log file at the end of processing.

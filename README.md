# MS3-Project
//Kristopher Pepper

Summary::

With the Java Project that I have made, It will collect a CSV document as user input, it will read that document, Input all Valid Entries into a Database and all the other bad entries into another CSV file, it then counts all the records


To get this to run just download and make sure you have a valid file path for the CSV File that you have/ You will also need to use the external libraries of OPENCSV-3.8.jar and also sqljdbc.jar

Overview

When Doing this Project The first thing I did was start reading out of the CSV file, I created a smaller testing CSV Doc to start an algorithm where I orignally read(using the external library of OPENCSV) and decide if the line in the CSV file is a valid entry or not, Once I had read through, I then start switched to the CSV was provided, I had to change some things around in my method as, I check if the cell is a white space but when using the CSV provided it would read 15 colums (its only the first 10 with data), So I put in a hard coded line to make it only check 10 columns(So this isn't going to work with other CSV files) I then Just started With the Database

I drop the previosly created table each time it is ran just so I dont make the table excessively large, also, My line of Code to input a line of of information into the Table is hard codded in with A string[], I could've made it more clean but I kept getting errors with the insert statement so when I finally got it to work I didn't want to keep working on it at the risk of breaking it again, so once again my Code will only work with theCSV file Provided



I also Included my compressed java project folder Which includes the database it made and the CSV file that it outputs 

//REQUIRED CLASS - KEEP THIS FILE IN THE 'project5' DIRECTORY WITH 'package project5'
//IMPLEMENT THE 'body' METHOD. MAKE AN INSTANCE OF 'Database' and return an ouput string
//DO NOT ADD METHODS

package project5;

import java.io.IOException;

import project2.InvalidInputException;
import project4.Database;

public class Project5
{
	public static String body(String[] args)
	{
		String output = "";
		try {
			Database db = new Database(args[0], true);
			output = db.toString();
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			output = "Syntax Error";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			output = "Invalid file";
		}
		return output;
	}
}

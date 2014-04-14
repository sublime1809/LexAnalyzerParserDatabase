//REQUIRED CLASS - KEEP THIS FILE IN THE 'project4' DIRECTORY WITH 'package project4'
//IMPLEMENT THE 'body' METHOD. MAKE AN INSTANCE OF 'Database' and return an ouput string
//DO NOT ADD METHODS

package project4;

import java.io.IOException;

import project2.InvalidInputException;

public class Project4
{
	public static String body(String[] args)
	{
		String output = "";
		try {
			Database db = new Database(args[0]);
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

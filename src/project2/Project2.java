//REQUIRED CLASS - KEEP THIS FILE IN THE 'project2' DIRECTORY WITH 'package project2'
//IMPLEMENT THE 'body' METHOD. MAKE AN INSTANCE OF 'DatalogProgram' and return an ouput string
//DO NOT ADD METHODS

package project2;

import java.io.IOException;
import java.util.*;
import project1.LexicalAnalyzer;

public class Project2
{
	public static LexicalAnalyzer analyze;
	
	public static String body(String[] args)
	{	
		try {
			StringBuilder output = new StringBuilder();
			try {
				output.append("Success!\n");
				output.append(new DatalogProgram(args[0]).toString());
				return output.toString();
			} catch(InvalidInputException e){
				output.setLength(0);
				output.append("Failure!\n");
				output.append("\t" + e.getInvalid().toString());
				
				return output.toString();
			}
		} catch(IOException e){
			return "Invalid file";
		}
	}
}

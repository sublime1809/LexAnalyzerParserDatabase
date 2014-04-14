//REQUIRED CLASS - KEEP THIS FILE IN THE 'project1' DIRECTORY WITH 'package project1'
//IMPLEMENT THE 'body' METHOD. MAKE AN INSTANCE OF 'LexicalAnalyzer' and return an ouput string
//DO NOT ADD METHODS

package project1;

public class Project1
{
	public static String body(String[] args)
	{
		String fileName = args[0];
		try {
			LexicalAnalyzer analyze = new LexicalAnalyzer(fileName);

			String output = analyze.toString();
			//System.out.println("Begin Output: \n" + output + " \n End Output");
			return output;
		} catch (LexicalException e) {
			return e.getMessage();
		}
		
	}
}

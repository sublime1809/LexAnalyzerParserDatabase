package project1;

public class LexicalException extends Exception{

	public LexicalException(String string) {
		super(string);
	}
	public LexicalException(char ch) {
		super("Invalid Character: " + ch);
	}
	
}

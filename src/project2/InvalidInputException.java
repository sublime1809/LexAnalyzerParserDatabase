package project2;

import project1.*;

public class InvalidInputException extends Exception{
	private Token bad;
	
	public InvalidInputException(Token Bad){
		super(Bad.toString());
		this.bad = Bad;
	}
	
	public Token getInvalid(){
		return bad;
	}
	
}

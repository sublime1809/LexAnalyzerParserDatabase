package project2;

import project1.*;
import project1.TokenType;

public class Identifier extends ParseTree{

	public Identifier(Token value) throws InvalidInputException {
		super(value);
		if(value.getType() != TokenType.ID){
			throw new InvalidInputException(value);
		}
		
	}
}

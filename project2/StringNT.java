package project2;

import project1.*;
import project1.TokenType;

public class StringNT extends ParseTree{

	public StringNT(Token value) throws InvalidInputException {
		super(value);
		if(value.getType() == TokenType.STRING){
			Domain.addDomain(value.getValue());
		} else {
			throw new InvalidInputException(value);
		}
	}
	
}

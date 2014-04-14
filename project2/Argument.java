package project2;

import project1.*;
import project1.TokenType;

public class Argument extends ParseTree {
	
	public Argument() throws InvalidInputException {
		super("Argument");
		Token cur = MyTokenGetter.peek();
		if(cur.getType() == TokenType.ID){
			ParseTree id = new Identifier(cur);
			this.setChild(id);
			MyTokenGetter.nextToken();
		} else if(cur.getType() == TokenType.STRING){
			ParseTree string = new StringNT(cur);
			this.setChild(string);
			MyTokenGetter.nextToken();
		} else {
			//System.out.println("Invalid: " + cur + " Expecting: ID | String");
			throw new InvalidInputException(cur);
		}
	}

}

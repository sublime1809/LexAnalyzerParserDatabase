package project2;

import project1.*;
import project1.Token.*;

public class Operator extends ParseTree {

	public Operator() throws InvalidInputException {
		super("Operator");
		Token cur = MyTokenGetter.peek();
		if(cur.getType() == TokenType.ADD || cur.getType() == TokenType.MULTIPLY){
			this.setChild(new ParseTree(cur));
			MyTokenGetter.nextToken();
		} else {
			//System.out.println("Invalid: " + cur.toString() + " Expecting: + | *");
			throw new InvalidInputException(cur);
		}
	}
}

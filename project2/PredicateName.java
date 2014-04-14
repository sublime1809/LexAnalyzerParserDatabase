package project2;

import project1.*;
import project1.Token.*;

public class PredicateName extends ParseTree {
	
	public PredicateName() throws InvalidInputException {
		super("PredicateName");
		Token cur = MyTokenGetter.peek();
		if(cur.getType() == TokenType.ID){
			ParseTree id = new Identifier(cur);
			this.setChild(id);
			MyTokenGetter.nextToken();
		} else {
			//System.out.println("Invalid: " + cur.toString() + " Expecting: ID");
			throw new InvalidInputException(cur);
		}
	}
}

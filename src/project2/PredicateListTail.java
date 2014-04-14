package project2;

import project1.*;
import project1.TokenType;

public class PredicateListTail extends ParseTree {
	
	public PredicateListTail() throws InvalidInputException {
		super("PredicateListTail");
		Token cur = MyTokenGetter.peek();
		if(cur.getType() == TokenType.COMMA){
			ParseTree comma = new ParseTree(cur);
			MyTokenGetter.nextToken();
			ParseTree predList = new PredicateList();
			comma.setSibling(predList);
			this.setChild(comma);
		} else {
			this.setRoot(null);
		}
	}

}

package project2;

import project1.*;
import project1.Token.*;

public class ConstantListTail extends ParseTree{

	public ConstantListTail() throws InvalidInputException{
		super("ConstantListTail");
		Token cur = MyTokenGetter.peek();
		if(cur.getType() == TokenType.COMMA){
			ParseTree comma = new ParseTree(cur);
			this.setChild(comma);
			MyTokenGetter.nextToken();
			ParseTree constantList = new ConstantList();
			comma.setSibling(constantList);
		} else {
			this.setRoot(null);
		}
	}
}

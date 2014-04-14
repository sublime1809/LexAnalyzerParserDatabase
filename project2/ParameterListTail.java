package project2;

import project1.*;
import project1.TokenType;

public class ParameterListTail extends ParseTree {

	public ParameterListTail() throws InvalidInputException {
		super("ParameterListTail");
		Token cur = MyTokenGetter.peek();
		if(cur.getType() == TokenType.COMMA){
			ParseTree comma = new ParseTree(cur);
			MyTokenGetter.nextToken();
			ParseTree parameterList = new ParameterList();
			comma.setSibling(parameterList);
			this.setChild(comma);
			//MyTokenGetter.nextToken();			/// ?????/
		} else {
			this.setRoot(null);
		}
	}
}

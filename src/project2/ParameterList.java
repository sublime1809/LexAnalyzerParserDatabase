package project2;

import project1.*;
import project1.TokenType;

public class ParameterList extends ParseTree {
	
	public ParameterList() throws InvalidInputException {
		super("ParameterList");
		Token cur = MyTokenGetter.peek();
		Enum<TokenType> type = cur.getType();
		//System.out.println(cur.toString());
		if(type == TokenType.STRING || type == TokenType.ID || type == TokenType.GT || type == TokenType.GE || type == TokenType.EQ 
				|| type == TokenType.NE || type == TokenType.LE || type == TokenType.LT || type == TokenType.LEFT_PAREN){
			ParseTree parameter = new Parameter();
			this.setChild(parameter);
			ParseTree parameterTail = new ParameterListTail();
			parameter.setSibling(parameterTail);
		} else {
			//System.out.println("Invalid: " + cur.toString() + " Expecting for List: String | ID | < | <= | = | != | >= | > | )");
			throw new InvalidInputException(cur);
		}
	}
}

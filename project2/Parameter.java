//REQUIRED CLASS
package project2;

import project1.*;

public class Parameter extends ParseTree {
	
	public Parameter() throws InvalidInputException{
		super("Parameter");
		Token cur = MyTokenGetter.peek();
		
		Enum<TokenType> type = cur.getType();
		if(type == TokenType.STRING || type == TokenType.ID){
			this.setChild(new Argument());
		} else if(type == TokenType.GT || type == TokenType.GE || type == TokenType.EQ || type == TokenType.NE ||
				type == TokenType.LE || type == TokenType.LT){
			this.setChild(new Comparator());
		} else if(type == TokenType.LEFT_PAREN){
			this.setChild(new Expression());
		} else {
			//System.out.println("Invalid: " + cur.toString() + " Expecting: String | ID | < | <= | = | != | >= | > | )");
			throw new InvalidInputException(cur);
		}
	}
}

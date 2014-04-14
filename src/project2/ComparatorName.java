package project2;

import project1.*;
import project1.Token.*;

public class ComparatorName extends ParseTree{

	public ComparatorName() throws InvalidInputException{
		super("ComparatorName");
		Token cur = MyTokenGetter.peek();
		Enum<TokenType> type = cur.getType();
		if(type == TokenType.GT || type == TokenType.GE || type == TokenType.EQ || type == TokenType.NE ||
				type == TokenType.LE || type == TokenType.LT){
			this.setChild(new ParseTree(cur));
		} else {
			//System.out.println("Invalid: " + cur.toString() + " Expecting: < | <= | = | != | >= | >");
			throw new InvalidInputException(cur);
		}
	}
}


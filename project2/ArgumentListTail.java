package project2;

import project1.*;
import project1.TokenType;

public class ArgumentListTail extends ParseTree{

	public ArgumentListTail() throws InvalidInputException {
		super("ArgumentListTail");
		Token cur = MyTokenGetter.peek();
		if(cur.getType() == TokenType.COMMA){
			ParseTree child = new ParseTree(cur);
			this.setChild(child);
			cur = MyTokenGetter.nextToken();
			if(cur.getType() == TokenType.ID || cur.getType() == TokenType.STRING){
				ParseTree argumentList = new ArgumentList();
				child.setSibling(argumentList);
			} else {
				//System.out.println("Invalid: " + cur.toString() + " Expecting: ID | String");
				throw new InvalidInputException(cur);
			}
		} else {
			this.setRoot(null);
		}
	}
}

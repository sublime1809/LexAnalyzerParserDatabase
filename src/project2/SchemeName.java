package project2;

import project1.*;
import project1.TokenType;

public class SchemeName extends ParseTree {
	
	public SchemeName() throws InvalidInputException {
		super("SchemeName");
		Token cur = MyTokenGetter.peek();
		if(cur.getType() == TokenType.ID){
			this.setChild(new Identifier(cur));
			cur = MyTokenGetter.nextToken();
			if(cur.getType() == TokenType.LEFT_PAREN){
				this.setSibling(new ParseTree(cur));
				cur = MyTokenGetter.nextToken();
				this.getSibling().setSibling(new AttributeList());
				cur = MyTokenGetter.peek();
				if(cur.getType() == TokenType.RIGHT_PAREN)
					this.getSibling().getSibling().setSibling(new ParseTree(cur));
				else {
					//System.out.println("Thrown SchemeName1");
					throw new InvalidInputException(cur);
				}
			} else {
				//System.out.println("Thrown SchemeName2");
				throw new InvalidInputException(cur);
			}
		} else {
			//System.out.println("Thrown SchemeName3");
			throw new InvalidInputException(cur);
		}
	}
}

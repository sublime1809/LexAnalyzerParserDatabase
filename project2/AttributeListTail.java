package project2;

import project1.*;
import project1.TokenType;

public class AttributeListTail extends ParseTree{
	
	public AttributeListTail() throws InvalidInputException{
		super("AttributeListTail");
		Token cur = MyTokenGetter.peek();
		if(cur.getType() == TokenType.COMMA){
			ParseTree leftChild = new ParseTree(cur);
			cur = MyTokenGetter.nextToken();
			if(cur.getType() == TokenType.ID){
				ParseTree idSibling = new AttributeList();
				leftChild.setSibling(idSibling);
				this.setChild(leftChild);
			} else {
				//System.out.println("Thrown AttributeListTail2");
				throw new InvalidInputException(cur);
			}
		} else {
			this.setRoot(null);
		}
	}
}
 
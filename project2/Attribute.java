package project2;

import project1.*;
import project1.TokenType;

public class Attribute extends ParseTree{
	
	private String value = "";

	public Attribute() throws InvalidInputException {
		super("Attribute");
		Token cur = MyTokenGetter.peek();
		if(cur.getType() == TokenType.ID){
			this.value = cur.getValue();
			this.setChild(new Identifier(cur));
			MyTokenGetter.nextToken();
			ParseTree rightSibling = new AttributeListTail();
			this.setSibling(rightSibling);
		} else {
			//System.out.println("Thrown Attribute");
			throw new InvalidInputException(cur);
		}
	}
	
	public String getValue() {
		return value;
	}
}

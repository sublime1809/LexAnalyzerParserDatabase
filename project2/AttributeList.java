package project2;

import java.util.ArrayList;

import project1.*;

public class AttributeList extends ParseTree {
	
	public AttributeList() throws InvalidInputException{
		super("AttributeList");
		Token cur = MyTokenGetter.peek();
		if(cur.getType() == TokenType.ID){
			ParseTree leftChild = new Attribute();
			this.setChild(leftChild);
		} else {
			//System.out.println("Thrown AttributeList2");
			throw new InvalidInputException(cur);
		}
	}
}

//REQUIRED CLASS
package project2;

import project1.*;

public class Scheme extends ParseTree
{

	public Scheme() throws InvalidInputException{
		super("Scheme");
		Token cur = MyTokenGetter.peek();
		if(cur.getType() == TokenType.ID){
			ParseTree leftChild = new SchemeName();
			MyTokenGetter.nextToken();
			ParseTree rightSibling = new SchemeListTail();
			this.setChild(leftChild);
			this.setSibling(rightSibling);
			SchemeList.addScheme(this);
		} else {
			//System.out.println("Thrown Scheme");
			throw new InvalidInputException(cur);
		}
	}
	
	public String toString(){
		return "\n  " + super.toString();
	}
}

	
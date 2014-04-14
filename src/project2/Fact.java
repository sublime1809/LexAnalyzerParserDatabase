//REQUIRED CLASS
package project2;

import project1.*;

public class Fact extends ParseTree {
	
	public Fact() throws InvalidInputException{
		super("Fact");
		Token cur = MyTokenGetter.peek();
		if(cur.getType() == TokenType.ID){
			ParseTree factName = new FactName();
			this.setChild(factName);
			MyTokenGetter.nextToken();
			ParseTree factList = new FactList();
			this.setSibling(factList);
			FactList.addFact(this);
		} else {
			throw new InvalidInputException(cur);
		}
	}
	
	public String toString(){
		return "\n  " + super.toString();
	}
}

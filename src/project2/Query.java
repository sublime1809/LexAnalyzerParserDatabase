//REQUIRED CLASS
package project2;

import project1.*;
import project1.Token.*;

public class Query extends ParseTree {
	
	public Query() throws InvalidInputException{
		super("Query");
		Token cur = MyTokenGetter.peek();
		if(cur.getType() == TokenType.ID){
			ParseTree simpPred = new SimplePredicate();			// now holding next token after )
			cur = MyTokenGetter.peek();
			if(cur.getType() == TokenType.Q_MARK){
				ParseTree ques = new ParseTree(cur);
				simpPred.setSibling(ques);
				this.setChild(simpPred);
				QueryList.addQuery(this);
				MyTokenGetter.nextToken();
			} else {
				//System.out.println("Invalid: " + cur.toString() + " Expecting: '?'");
				throw new InvalidInputException(cur);
			}	
		} else {
			//System.out.println("Invalid: " + cur.toString() + " Expecting: ID");
			throw new InvalidInputException(cur);
		}
	}
	
	public String toString(){
		return "\n  " + super.toString();
	}
}

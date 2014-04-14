//REQUIRED CLASS
package project2;

import project1.*;
import project1.TokenType;

public class Predicate extends ParseTree
{
	public Predicate() throws InvalidInputException {
		super("Predicate");
		ParseTree predName = new PredicateName();
		this.setChild(predName);
		Token cur = MyTokenGetter.peek();
		if(cur.getType() == TokenType.LEFT_PAREN){
			ParseTree lp = new ParseTree(cur);
			cur = MyTokenGetter.nextToken();
			ParseTree parameterList = new ParameterList();
			cur = MyTokenGetter.peek();
			if(cur.getType() == TokenType.RIGHT_PAREN){
				ParseTree rp = new ParseTree(cur);
				predName.setSibling(lp);
				lp.setSibling(parameterList);
				parameterList.setSibling(rp);
				MyTokenGetter.nextToken();
			} else {
				//System.out.println("Invalid: " + cur.toString() + " Expecting: ')'");
				throw new InvalidInputException(cur);
			}
		} else {
			//System.out.println("Invalid: " + cur.toString() + " Expecting: '('");
			throw new InvalidInputException(cur);
		}
	}
}

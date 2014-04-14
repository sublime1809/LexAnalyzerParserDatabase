package project2;

import project1.*;
import project1.TokenType;

public class SimplePredicate extends ParseTree {
	
	public SimplePredicate() throws InvalidInputException {
		super("Simple Predicate");
		Token cur = MyTokenGetter.peek();
		if(cur.getType() == TokenType.ID){
			ParseTree leftChild = new PredicateName();
			cur = MyTokenGetter.peek();			// incremented in the Predicate Name
			if(cur.getType() == TokenType.LEFT_PAREN){
				ParseTree lp = new ParseTree(cur);
				leftChild.setSibling(lp);
				cur = MyTokenGetter.nextToken();
				if(cur.getType() == TokenType.ID || cur.getType() == TokenType.STRING){
					ParseTree argumentList = new ArgumentList();
					lp.setSibling(argumentList);
					cur = MyTokenGetter.peek();
					if(cur.getType() == TokenType.RIGHT_PAREN){
						ParseTree rp = new ParseTree(cur);
						argumentList.setSibling(rp);
						MyTokenGetter.nextToken();
						this.setChild(leftChild);
					} else {
						//System.out.println("Invalid: " + cur + " Expecting: ')'");
						throw new InvalidInputException(cur);
					}
				} else {
					//System.out.println("Invalid: " + cur + " Expecting: ID | String");
					throw new InvalidInputException(cur);
				}
			} else {
				//System.out.println("Invalid: " + cur + " Expecting: '('");
				throw new InvalidInputException(cur);
			}
		} else {
			//System.out.println("Invalid: " + cur + " Expecting: ID");
			throw new InvalidInputException(cur);
		}
	}
}

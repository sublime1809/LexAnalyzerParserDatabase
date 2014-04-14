//REQUIRED CLASS
package project2;

import project1.*;

public class Rule extends ParseTree {
	
	public Rule() throws InvalidInputException{
		super("Rule");		// increment ruleList count
		Token cur = MyTokenGetter.peek();
		if (cur.getType() == TokenType.ID){ 	// Child == simplePred -> :- -> <Predicate List> -> .
			ParseTree simplePred = new SimplePredicate();
			cur = MyTokenGetter.peek();
			if(cur.getType() == TokenType.COLON_DASH){
				ParseTree cd = new ParseTree(cur);
				simplePred.setSibling(cd);
				cur = MyTokenGetter.nextToken();
				ParseTree predicateList = new PredicateList();
				cd.setSibling(predicateList);
				cur = MyTokenGetter.peek();
				if(cur.getType() == TokenType.PERIOD){
					ParseTree period = new ParseTree(cur);
					predicateList.setSibling(period);
					MyTokenGetter.nextToken();
					this.setChild(simplePred);
					RuleList.addRule(this);
				} else {
					//System.out.println("Invalid: " + cur.toString() + " Expecting: .");
					throw new InvalidInputException(cur);
				}
			} else {
				//System.out.println("Invalid: " + cur.toString() + " Expecting: :-");
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

package project2;

import project1.*;
import project1.Token.*;

public class Comparator extends ParseTree{

	public Comparator() throws InvalidInputException {
		super("Comparator");
		Token cur = MyTokenGetter.peek();
		ParseTree compName = new ComparatorName();
		cur = MyTokenGetter.nextToken();
		if(cur.getType() == TokenType.LEFT_PAREN){
			ParseTree lp = new ParseTree(cur);
			MyTokenGetter.nextToken();
			ParseTree argument1 = new Argument();
			cur = MyTokenGetter.peek();
			if(cur.getType() == TokenType.COMMA){
				ParseTree comma = new ParseTree(cur);
				MyTokenGetter.nextToken();
				ParseTree argument2 = new Argument();
				cur = MyTokenGetter.peek();
				if(cur.getType() == TokenType.RIGHT_PAREN){
					ParseTree rp = new ParseTree(cur);
					this.setChild(compName);
					compName.setSibling(lp);
					lp.setSibling(argument1);
					argument1.setSibling(comma);
					comma.setSibling(argument2);
					argument2.setSibling(rp);
					MyTokenGetter.nextToken();
				}  else {
					//System.out.println("Invalid: " + cur.toString() + " Expecting: ')'");
					throw new InvalidInputException(cur);
				}
			} else {
				//System.out.println("Invalid: " + cur.toString() + " Expecting: ','");
				throw new InvalidInputException(cur);
			}
		} else {
			//System.out.println("Invalid: " + cur.toString() + " Expecting: '('");
			throw new InvalidInputException(cur);
		}
	}
}

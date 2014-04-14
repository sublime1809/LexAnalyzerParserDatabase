package project2;

import project1.*;

public class Expression extends ParseTree{

	public Expression() throws InvalidInputException {
		super("Expression");
		Token cur = MyTokenGetter.peek();
		if(cur.getType() == TokenType.LEFT_PAREN){
			ParseTree lp = new ParseTree(cur);
			MyTokenGetter.nextToken();
			ParseTree para1 = new Parameter();
			ParseTree op = new Operator();
			ParseTree para2 = new Parameter();
			cur = MyTokenGetter.peek();
			if(cur.getType() == TokenType.RIGHT_PAREN){
				ParseTree rp = new ParseTree(cur);
				this.setChild(lp);
				lp.setSibling(para1);
				para1.setSibling(op);
				op.setSibling(para2);
				para2.setSibling(rp);
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

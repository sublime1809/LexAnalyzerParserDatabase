package project2;

import project1.*;
import project1.Token.*;

public class FactName extends ParseTree{
	
	public FactName() throws InvalidInputException {
		super("FactName");
		Token cur = MyTokenGetter.peek();
		if(cur.getType() == TokenType.ID){
			ParseTree id = new Identifier(cur);
			this.setChild(id);
			cur = MyTokenGetter.nextToken();
			if(cur.getType() == TokenType.LEFT_PAREN){
				ParseTree lp = new ParseTree(cur);
				this.setSibling(lp);
				cur = MyTokenGetter.nextToken();
				if(cur.getType() == TokenType.STRING){
					ParseTree constant = new ConstantList();
					lp.setSibling(constant);
					cur = MyTokenGetter.peek();
					if(cur.getType() == TokenType.RIGHT_PAREN){
						ParseTree rl = new ParseTree(cur);
						constant.setSibling(rl);
						cur = MyTokenGetter.nextToken();
						if(cur.getType() == TokenType.PERIOD){
							ParseTree p = new ParseTree(cur);
							rl.setSibling(p);
						} else {
							//System.out.println("Thrown from FactName: expecting .");
							throw new InvalidInputException(cur);
						}
					} else {
						//System.out.println("Thrown from FactName: expecting )");
						throw new InvalidInputException(cur);
					}
				} else {
					//System.out.println("Thrown from FactName: expecting string");
					throw new InvalidInputException(cur);
				}
			} else {
				//System.out.println("Thrown from FactName: expecting (");
				throw new InvalidInputException(cur);
			}
		}
	}
}

package project2;

import project1.*;

public class QueryListTail extends ParseTree {
	
	public QueryListTail() throws InvalidInputException{
		super("QueryListTail");
		Token cur = MyTokenGetter.peek();
		if(cur.getType() == TokenType.ID){
			ParseTree queryList = new QueryList();
			this.setChild(queryList);
		} else {
			this.setRoot(null);
		}
	}
}

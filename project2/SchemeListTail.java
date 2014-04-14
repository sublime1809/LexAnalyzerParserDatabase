package project2;

import project1.Token;
import project1.TokenType;

public class SchemeListTail extends ParseTree {

	public SchemeListTail() throws InvalidInputException {
		super("SchemeListTail");
		Token cur = MyTokenGetter.peek();
		if(cur.getType() == TokenType.ID){
			ParseTree leftChild = new SchemeList();
			this.setChild(leftChild);
		} else {
			this.setRoot(null);
		}
			
	}
}

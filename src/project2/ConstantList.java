package project2;

import project1.*;

public class ConstantList extends ParseTree{

	public ConstantList() throws InvalidInputException {
		super("ConstantList");
		Token cur = MyTokenGetter.peek();
		ParseTree leftChild = new StringNT(cur);
		MyTokenGetter.nextToken();
		ParseTree strSibling = new ConstantListTail();
		this.setChild(leftChild);
		leftChild.setSibling(strSibling);
	}
}

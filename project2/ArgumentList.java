package project2;

import project1.*;
import project1.TokenType;

import java.util.*;

public class ArgumentList extends ParseTree{
	
	public ArgumentList() throws InvalidInputException {
		super("ArgumentList");
		ParseTree child = new Argument();			// child
					// increment here??
		ParseTree listTail = new ArgumentListTail();		// child.sibling
		this.setChild(child);
		child.setSibling(listTail);
	}
}

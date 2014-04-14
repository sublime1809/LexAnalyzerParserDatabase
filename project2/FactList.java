//REQUIRED CLASS
package project2;

import java.util.ArrayList;

import project1.*;

public class FactList extends ParseTree {
	
	private static ArrayList<Fact> factList = new ArrayList<Fact>();
	
	public FactList() throws InvalidInputException{
		super("FactList");
		Token cur = MyTokenGetter.peek();
		if(cur.getType() == TokenType.ID){
			ParseTree fact = new Fact();
			this.setChild(fact);
		} else {
			this.setRoot(null);
		}
	}
	
	public static ArrayList<Fact> getFacts() {
		return factList;
	}
	public static void addFact(Fact toAdd){
		factList.add(toAdd);
	}
	public static int getSize(){
		return factList.size();
	}
}

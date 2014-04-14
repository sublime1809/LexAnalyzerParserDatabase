//REQUIRED CLASS
package project2;

import project1.*;
import java.util.*;

public class RuleList extends ParseTree {
	
	private static ArrayList<Rule> ruleList = new ArrayList<Rule>();

	public RuleList() throws InvalidInputException{
		super("RuleList");
		Token cur = MyTokenGetter.peek();
		if(cur.getType() == TokenType.ID){
			ParseTree ruleChild = new Rule();
			ParseTree ruleList = new RuleList();
			this.setChild(ruleChild);
			ruleChild.setSibling(ruleList);
		} else {
			this.setRoot(null);
		}
	}
	
	public static ArrayList<Rule> getRules() {
		return ruleList;
	}
	
	public static void addRule(Rule toAdd){
		ruleList.add(toAdd);
	}
	
	public static int getSize(){
		return ruleList.size();
	}
}

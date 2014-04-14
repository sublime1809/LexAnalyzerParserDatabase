//REQUIRED CLASS
package project2;

import java.util.ArrayList;

import project1.*;

public class SchemeList extends ParseTree {
	
	private static ArrayList<Scheme> schemeList = new ArrayList<Scheme>();
	
	public SchemeList() throws InvalidInputException {
		super("SchemeList");
		Token cur = MyTokenGetter.peek();
		if(cur.getType() == TokenType.ID){
			ParseTree leftChild = new Scheme();
			this.setChild(leftChild);
		} else {
			//System.out.println("Thrown SchemeList");
			throw new InvalidInputException(cur);
		}
	}
	public static ArrayList<Scheme> getSchemes() {
		return schemeList;
	}
	public static void addScheme(Scheme toAdd){
		schemeList.add(toAdd);
	}
	public static int getSize(){
		return schemeList.size();
	}
}

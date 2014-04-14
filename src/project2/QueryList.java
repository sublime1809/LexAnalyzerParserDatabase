//REQUIRED CLASS
package project2;

import java.util.ArrayList;

import project1.*;

public class QueryList extends ParseTree {
	
	private static ArrayList<Query> queryList = new ArrayList<Query>();
	
	public QueryList() throws InvalidInputException {
		super("QueryList");
		ParseTree queryChild = new Query();
		ParseTree queryTail = new QueryListTail();
		this.setChild(queryChild);
		queryChild.setSibling(queryTail);
	}
	
	public static ArrayList<Query> getQueries() {
		return queryList;
	}
	
	public static void addQuery(Query toAdd){
		queryList.add(toAdd);
	}
	
	public static int getSize(){
		return queryList.size();
	}
}

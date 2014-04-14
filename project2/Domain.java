//REQUIRED CLASS
package project2;

import java.util.Collection;
import java.util.TreeSet;

import project1.*;

public class Domain extends ParseTree {
	private static Collection<String> Domain = new TreeSet<String>();
	
	public Domain(){
		super("Domain");
	}
	
	public static String String(){
		StringBuilder domain = new StringBuilder();
		for(String dom : Domain){
			domain.append("\n  " + dom);
		}
		return domain.toString();
	}
	
	public static void addDomain(String toAdd){
		Domain.add(toAdd);
	}
	
	public static int getSize(){
		return Domain.size();
	}
}

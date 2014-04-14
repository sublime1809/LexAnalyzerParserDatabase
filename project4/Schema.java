//REQUIRED CLASS
package project4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import project1.*;
import project2.*;

public class Schema
{
	private ArrayList<String> attributes = new ArrayList<String>();
	
	public Schema (AttributeList attrList) {
		
		boolean more = true;
		AttributeList curList = attrList;
		while(more) {
			if(curList.getChild() instanceof Attribute) {
				Attribute temp = (Attribute) curList.getChild();
				attributes.add(temp.getValue());
				if(temp.getSibling().getRoot() != null)
					curList = (AttributeList)temp.getSibling().getChild().getSibling();
				else
					more = false;
			} else {
				more = false;
			}
		}
	}
	
	public Schema() {
	}
	
	public void project(ArrayList<String> heads) {
		ArrayList<String> tempAttr = new ArrayList<String>();
		for(String attr : attributes) {
			if(heads.contains(attr)) {
				tempAttr.add(attr);
			}
		}
		attributes = tempAttr;
	}

	public boolean contains(String attr) {
		return attributes.contains(attr);
	}
	
	public void addAttribute(String attr) {
		this.attributes.add(attr);
	}
	
	public void rename(int index, String newValue) {
		attributes.set(index, newValue);
	}
	
	public ArrayList<String> getAttrs() {
		return this.attributes;
	}
	
	@Override
	public boolean equals(Object toCompare) {
		Schema toComp = (Schema) toCompare;
		if(toComp.getAttrs().size() != attributes.size())
			return false;
		for(int i = 0; i < attributes.size(); i++) {
			if(!toComp.getAttrs().get(i).equals(attributes.get(i)))
				return false;
		}
		return true;
	}
	
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("(");
		Iterator<String> attrs = attributes.iterator();
		for(int i = 0; i < attributes.size(); i++) {
			string.append(attrs.next());
			if(i+1 < attributes.size())
				string.append(",");
		}
		string.append(")");
		return string.toString();
	}
}

//REQUIRED CLASS
package project4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

import project1.*;
import project2.*;

public class Tuple implements Comparable
{
	private ArrayList<AVPairs> avpairs = new ArrayList<AVPairs>();
	private Schema schema;
	
	public Tuple(Schema schema, ConstantList constantList) {
		ArrayList<String> constants = new ArrayList<String>();
		this.schema = schema;
		
		boolean more = true;
		ConstantList curList = constantList;
		while(more) {
			if(curList.getChild() instanceof StringNT) {
				String temp = ((Token) curList.getChild().getRoot()).getValue();
				constants.add(temp);
				if(curList.getChild().getSibling().getRoot() != null) {
					curList = (ConstantList) curList.getChild().getSibling().getChild().getSibling();
				} else
					more = false;
			} else {
				more = false;
			}
		}
		
		Iterator<String> attributes = schema.getAttrs().iterator();
		for(int i = 0; i < constants.size(); i++) {
			AVPairs temp = new AVPairs(attributes.next(), constants.get(i), i);
			avpairs.add(temp);
		}
	}
	public Tuple(Tuple tuple) {
		for(AVPairs pair : tuple.getPairs()) {
			this.avpairs.add(pair);
		}
	}
	public Tuple(Schema schema) {
		this.schema = schema;
	}
	
	
	/** Relational Operators **/
	public void rename(String original, String newValue) {
		for(AVPairs av : avpairs) {
			if(av.getAttr().equals(original))
				av.setAttr(newValue);
		}
	}
	public Tuple rename(int index, String newValue) {
		Tuple tempTuple = new Tuple(this.schema);
		int i = 0;
		for(; i < avpairs.size(); i++) {
			AVPairs av = avpairs.get(i);
			if(i == index) {
				tempTuple.addPair(new AVPairs(newValue, av.getValue(), i));
			} else {
				tempTuple.addPair(new AVPairs(av.getAttr(),av.getValue(), i));
			}
		}
		return tempTuple;
	}
	public boolean select(int index, String value) {
		for(AVPairs av : avpairs) {
			if(av.getIndex() == index) {
				if(av.getValue().equals(value))
					return true;
				return false;
			}
		}
		return false;
	}
	public Tuple project(ArrayList<Integer> indexes) {
		Tuple tempTuple = new Tuple(this.schema);
		int i = 0;
		for(AVPairs pair : this.avpairs) {
			if(indexes.contains(pair.getIndex())) {
				pair.setIndex(i++);
				tempTuple.addPair(pair);
			}
		}
		return tempTuple;
	}
	public Tuple project(ArrayList<String> heads, boolean another) {
		Tuple tempTuple = new Tuple(this.schema);
		int i = 0;
		for(AVPairs pair : this.avpairs) {
			if(heads.contains(pair.getAttr())) {
				pair.setIndex(i++);
				tempTuple.addPair(pair);
			}
		}
		return tempTuple;
	}
	
	
	/** Check if two index's values are the same **/
	public boolean indexesEqual(int index1, int index2) {
		if(index1 >= index2) {
			String value1 = "";
			for(AVPairs av : avpairs) {
				if (av.getIndex() == index1) {
					value1 = av.getValue();
					break;
				}
			}
			for(AVPairs av : avpairs) {
				if(av.getIndex() == index2) {
					if(av.getValue().equals(value1))
						return true;
					else
						return false;
				}
			}
		} else {
			String value1 = "";
			for(AVPairs av : avpairs) {
				if (av.getIndex() == index2) {
					value1 = av.getValue();
					break;
				}
			}
			for(AVPairs av : avpairs) {
				if(av.getIndex() == index1) {
					if(av.getValue().equals(value1))
						return true;
					else
						return false;
				}
			}
		}
		return false;
	}
	
	public Tuple combineWithTuple(Tuple toCombo) {
		Tuple toReturn = new Tuple(this);
		int lastIndex = avpairs.size();
		for(AVPairs pair : toCombo.getPairs()) {
			toReturn.addPair(new AVPairs(pair.getAttr(), pair.getValue(), lastIndex++));
		}
		return toReturn;
	}
	
	
	@Override
	public boolean equals(Object toCompare) {
		Tuple compare = (Tuple) toCompare;
		
		if(compare.getPairs().size() != avpairs.size()) {
			return false;
		} else {
			Iterator<AVPairs> pairs1 = avpairs.iterator();
			Iterator<AVPairs> pairs2 = compare.getPairs().iterator();
			for(int i=0; i < avpairs.size(); i++) {
				AVPairs temp1 = pairs1.next();
				AVPairs temp2 = pairs2.next();
				if(!temp1.equals(temp2)) {
					return false;
				}
			}
			return true;
		}
	}
	
	
	@Override
	public int hashCode() {
		int temp = 0;
		for(AVPairs pair : avpairs)
			temp += pair.hashCode();
		return temp;
	}
	
	public ArrayList<AVPairs> getPairs() {
		return this.avpairs;
	}
	public void removePair(AVPairs pair) {
		this.avpairs.remove(pair);
	}
	public void addPair(AVPairs pair) {
		if(!avpairs.contains(pair))
			this.avpairs.add(pair);
	}
	public void addPair(String value) {
		AVPairs pair = new AVPairs(this.schema.getAttrs().get(avpairs.size()), value, avpairs.size());
		if(!avpairs.contains(pair))
			this.avpairs.add(pair);
	}
	public void addValue(String attr, String value) {
		int index = this.schema.getAttrs().indexOf(attr);
		AVPairs pair = new AVPairs(attr, value, index);
		if(!avpairs.contains(pair))
			this.avpairs.add(pair);
	}
	
	public String toString() {
		StringBuilder string = new StringBuilder();
		Iterator<AVPairs> pairs = avpairs.iterator();
		for(int i =0 ; i <avpairs.size(); i++) {
			string.append(pairs.next().toString());
			if(i+1 < avpairs.size())
				string.append(", ");
		}
		return string.toString();
	}
	@Override
	public int compareTo(Object arg0) {
		Tuple compare = (Tuple) arg0;
		String value1 = "";
		for(AVPairs pair : avpairs) {
			if(pair.getIndex() == 0) {
				value1 = pair.getValue();
			}
		}
		String value2 = "";
		for(AVPairs pair : compare.getPairs()) {
			if(pair.getIndex() == 0) {
				value2 = pair.getValue();
			}
		}
		return value1.compareTo(value2);
	}
}

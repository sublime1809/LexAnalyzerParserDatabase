//REQUIRED CLASS
package project4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

import project1.*;
import project2.*;

public class Relation
{
	
	private String name = "";
	private Schema schema;
	private HashSet<Tuple> tuples = new HashSet<Tuple>();
	
	/** CONSTRUCTORS **/
	/*
	 *  This is how we will initially create a Relation (from the Database class)
	 * relationName - the name of the relation that we are creating
	 * attrList - the AttributeList object (Project 2) that we are going to parse to obtain the schema
	 */
	public Relation(String relationName, AttributeList attrList) {
		this.name = relationName;
		this.schema = new Schema(attrList);
	}
	/*
	 * This is our copying constructor, to help us copy one relation to another.
	 */
	public Relation(Relation relation, boolean copyTuples) {
		this.name = relation.name;
		this.schema = new Schema();
		for(String attr : relation.getSchema().getAttrs())
			this.schema.addAttribute(attr);
		if(copyTuples) {
			for(Tuple tuple : relation.getTuples()) {
				this.tuples.add(tuple);
			}
		}
	}
	/*
	 * This is a constructor used to join schemas and then add them here
	 */
	public Relation(String relationName, Schema schema) {
		this.name = relationName;
		this.schema = schema;
	}
	/*
	 * 
	 */
	public Relation() {
		this.schema = new Schema();
	}
	
	
	/** Relational Algebra operations **/
	public Relation rename(int index, String newValue) {
		Relation newRel = new Relation(this, false);
		newRel.schema.rename(index, newValue);
		for(Tuple tuple : this.getTuples()) {
			newRel.addTuple(tuple.rename(index, newValue));
		}
		return newRel;
	}
	
	public Relation select(int index, String value) {
		Relation newRel = new Relation(this, false);
		for(Tuple tuple : tuples) {
			if(tuple.select(index, value))
				newRel.addTuple(tuple);
		}
		return newRel;
	}
	
	public Relation project(ArrayList<Integer> indexes) {
		Relation newRel = new Relation(this, false);
		for(Tuple tuple : tuples) {
			newRel.addTuple(tuple.project(indexes));
		}
		return newRel;
	}
	
	public Relation project(ArrayList<String> heads, boolean another) {
		Relation newRel = new Relation(this, false);
		newRel.getSchema().project(heads);
		for(Tuple tuple : tuples) {
			newRel.addTuple(tuple.project(heads, another));
		}
		return newRel;
	}
	
	public Relation join(Relation toJoin) {
		// Precondition: create copies as to not modify the original Relations
		Relation temp = new Relation(this, true);
		Relation temp2 = new Relation(toJoin, true);
		Relation joined = new Relation();
		// 1) Cross Product.
			// a) find all attributes that are the same and rename the one in "toJoin"
			//    (while saving the like column names for the Select (2))
			// 	  (and keeping all the unique ones/ original for the projection)
			ArrayList<HeadPair> likeAttrs = new ArrayList<HeadPair>();
			ArrayList<Integer> indexes = new ArrayList<Integer>();
			for(String attr : temp.getSchema().getAttrs()) {
				if(toJoin.getSchema().getAttrs().contains(attr)) {
					String newAttr = createPrime(attr);
					while(toJoin.getSchema().getAttrs().contains(newAttr)) {
						newAttr = createPrime(newAttr);
					}
					HeadPair likeColumn = new HeadPair(attr, newAttr);
					int indexOfLike = temp2.getSchema().getAttrs().indexOf(attr);
					likeColumn.setRenamedIndex(indexOfLike);
					
					indexes.add(indexOfLike);
					likeAttrs.add(likeColumn);
				}
			}
			for(HeadPair head : likeAttrs) {
				temp2 = temp2.rename(head.getRenamedIndex(), head.getRenamed());
			}
			// b) combine the schemas
			Schema finalSchema = new Schema();
			for(String attr : temp.getSchema().getAttrs()) {
				finalSchema.addAttribute(attr);
			}
			for(String attr : temp2.getSchema().getAttrs()) {
				finalSchema.addAttribute(attr);
			}
			// c) create all combinations of tuples
			HashSet<Tuple> finalTuples = new HashSet<Tuple>();
			for(Tuple tuple : temp.getTuples()) {
				for(Tuple tuple2 : temp2.getTuples()) {
					finalTuples.add(tuple.combineWithTuple(tuple2));
				}
			}
			// ** joined relation will have the combined schemas and the combined tuples
			//    but will have the same name as the original
			joined.setSchema(finalSchema);
			joined.setTuples(finalTuples);
			
		
		// 2) Select where the like attributes are the same
		for(HeadPair likeAttr : likeAttrs) {
			int index1 = joined.getSchema().getAttrs().indexOf(likeAttr.getOriginal());	// getting the index of the original attribute (i.e. A)
			int index2 = joined.getSchema().getAttrs().indexOf(likeAttr.getRenamed());	// getting the index of the renamed (primed) attribute (i.e. A`)
			
			HashSet<Tuple> newJoinedTuples = new HashSet<Tuple>();
			// a) for each tuple, check to see if they are equal
			for(Tuple tuple : joined.getTuples()) {			
				if(tuple.indexesEqual(index1, index2)) {
			// b) if they are, add them to the final set for the joined relation
					newJoinedTuples.add(tuple);				
				}
			}
			// c) take all the tuples that have the correct corresponding indexes equal, and setTuples
			joined.setTuples(newJoinedTuples);				
		}
		
		// 3) Project on all the attributes in "this" and all the unique attributes in "toJoin"
		//	  * to find them, we need to iterate through all the possible ones (the schema) and
		//	  	if it is in the LikeAttrs as the renamed, don't project
		//    * also need to iterate through the tuples and make sure those indexes are correct
		//			* the public Tuple project(ArrayList<String>, boolean) should take care 
		//		 	  of that.
		ArrayList<String> toProject = new ArrayList<String>();
		for(String attr : joined.getSchema().getAttrs()) {
			boolean contain = false;
			for(HeadPair likeAttr : likeAttrs) {
				if(likeAttr.getRenamed().equals(attr)) {
					contain = true;
					break;
				}
			}
			if(!contain)
				toProject.add(attr);
		}
		joined = joined.project(toProject, false);
		return joined;
	}
	
	/**
	 * 
	 * @param toJoin
	 * @return null if it is not union compatible or the unioned relation if otherwise.
	 */
	public Relation union(Relation toJoin, ArrayList<HeadPair> heads) {
		// Precondition: make a copy of the relations so we don't alter the originals
		Relation temp = new Relation(this, true);	// we are going to alter this one directly and return it.
		Relation temp2 = new Relation(toJoin, true);
		// 1) Find the like schema attributes - this is to compare them. 
		//		a) if there is a attribute in temp2, and not in temp, discard it.
		//		b) at this point it should be the same size and union compatible.
		/*for(String attr : temp2.getSchema().getAttrs()) {	// this might not be necessary since we are simply adding them to temp
			if(!temp.getSchema().getAttrs().contains(attr)) {
				temp2.getSchema().getAttrs().remove(attr);
			}
		}*/
		// 2) Organize the tuples to be compatible
		//		a) For all the tuples in temp2 - create an empty tuple and add the avpairs in the order
		//			that the schema (of temp) needs and add them to temp
		for(Tuple tuple : temp2.getTuples()) {
			Tuple tempTuple = new Tuple(temp.getSchema());
			for(HeadPair head : heads) {
				String attr = head.getRenamed();	// renamed contains what the rule states that it is
				for(AVPairs pair : tuple.getPairs()) {
					if(pair.getAttr().equals(attr)) {
						tempTuple.addPair(pair.getValue());
						break;
					}
				}
			}
			if(tempTuple.getPairs().size() == temp.getSchema().getAttrs().size())
				temp.addTuple(tempTuple);
		}
		return temp;
	}
	
	
	/** Adding tuples **/
	public void addTuple(ConstantList constants) {
		Tuple tuple = new Tuple(this.schema, constants);
		tuples.add(tuple);
	}
	public void addTuple(Tuple tuple) {
		if(!tuples.contains(tuple))
			tuples.add(tuple);
	}
	
	
	/** These are simple logic methods to help compare/print etc. **/
	public boolean isRelation(String RNtoTest) {
		return RNtoTest.equals(name);
	}
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append(name + schema.toString() + "\n");
		
		for(Tuple tuple : tuples) {
			string.append(tuple.toString() + "\n");
		}
		return string.toString();
	}
	public String printTuples() {
		StringBuilder string = new StringBuilder();	
		Iterator<Tuple> tuple = tuples.iterator();
		for(int i = 0; i < tuples.size(); i ++) {
			string.append("  "+tuple.next().toString());
			if(i+1 < tuples.size())
				string.append("\n");
		}
		return string.toString();
	}
	public String createPrime(String orig) {
		return orig + "`";
	}
	
	
	/** Simple getters/setters **/
	public String getName(){
		return this.name;
	}
	public Schema getSchema() {
		return this.schema;
	}
	public HashSet<Tuple> getTuples() {
		return this.tuples;
	}
	
	public void setName(String name){
		this.name = name;
	}
	public void setSchema(Schema schema) {
		this.schema = schema;
	}
	public void setTuples(HashSet<Tuple> tuples) {
		this.tuples = tuples;
	}
	
	
	/** Equals to tell when it has been modified
	 * Must have:
	 *     * same schema
	 *     * same tuples
	 *     * same name
	 */
	@Override
	public boolean equals(Object toCompare) {
		Relation toComp = (Relation) toCompare;
		return this.schema.equals(toComp.getSchema()) && checkTuples(toComp.getTuples(), this.getTuples()) && this.name.equals(toComp.getName());
	}
	
	private boolean checkTuples(HashSet<Tuple> tuples1, HashSet<Tuple> tuples2) {
		for(Tuple tuple : tuples1) {
			if(!tuples2.contains(tuple))
				return false;
		}
		for(Tuple tuple : tuples2) {
			if(!tuples1.contains(tuple))
				return false;
		}
		return true;
	}
}

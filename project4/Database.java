//REQUIRED CLASS
package project4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import project1.*;
import project2.*;

public class Database
{
	
	private ArrayList<Relation> relations = new ArrayList<Relation>();
	private ArrayList<QueryAnswer> answers = new ArrayList<QueryAnswer>();
	private int iterations = 0;
	private boolean needToIterate = false;
	
	public Database(String fileName) throws InvalidInputException, IOException {
		DatalogProgram datalog = new DatalogProgram(fileName);
		
		/* Build relations/schemas */
		processSchemes(datalog.getSchemes());
		
		/* Populate the relations w/ values */
		processFacts(datalog.getFacts());
		
		/* Answer queries and build string */
		processQueries(datalog.getQueries());
	}
	
	public Database(String fileName, boolean projectFive) throws InvalidInputException, IOException {
		DatalogProgram datalog = new DatalogProgram(fileName);
		
		/* Build relations/schemas */
		processSchemes(datalog.getSchemes());
		
		/* Populate the relations w/ values */
		processFacts(datalog.getFacts());
		
		if(projectFive) {
			/* Populate the relations w/ the values from the rules */
			do {
				processRules(datalog.getRules());
			} while(needToIterate);
		}
			
		/* Answer queries and build string */
		processQueries(datalog.getQueries());
	}
	
	public String toString() {
		StringBuilder string = new StringBuilder();
		for(Relation relation : relations) {
			string.append(relation.toString() + "\n");
		}
		//return string.toString();
		return "";
	}
	
	private void processSchemes(ArrayList<Scheme> schemes) {
		for(Scheme scheme : schemes) {
			SchemeName schemeChild = (SchemeName) scheme.getChild();
			
			Identifier schemeChildID = (Identifier) schemeChild.getChild(); 	// to get the name of the Scheme
			String relationName = ((Token) schemeChildID.getRoot()).getValue();
			
			AttributeList attrList = (AttributeList) schemeChild.getSibling().getSibling();		// to create the Schema for the Relation
			
			relations.add(0, new Relation(relationName, attrList));		// The Schemes are processed and added backwards, 
																		// so we have to reverse them... not necessary, but I want to
		}
	}
	
	private void processFacts(ArrayList<Fact> facts) {
		for(int i = (facts.size()-1); i >= 0; i--) {
			Fact fact = facts.get(i);
		//for(Fact fact : facts) {
			if(fact.getChild() instanceof FactName) {
				FactName factChild = (FactName) fact.getChild();
				Identifier factChildID = (Identifier) factChild.getChild();
				
				String relationName = ((Token) factChildID.getRoot()).getValue();
				ConstantList constants = (ConstantList) factChild.getSibling().getSibling();
				
				Relation oneWeWant = null;
				for(Relation relation : relations) {
					if(relation.getName().equals(relationName)) {
						oneWeWant = relation;
						break;
					}
				}
				
				oneWeWant.addTuple(constants);
			}
		}
	}
	
	private void processRules(ArrayList<Rule> rules) {
		needToIterate = false;
		for(Rule rule : rules) {
			
			SimplePredicate pred = (SimplePredicate) rule.getChild();
			PredicateName predName = (PredicateName) pred.getChild();
			String relationName = ((Token) predName.getChild().getRoot()).getValue();
			Relation answer = null;			
			
			for(Relation relation : relations) {
				if(relation.getName().equals(relationName)) {
					answer = new Relation(relation, true);
					break;
				}
			}
			ArgumentList args = (ArgumentList) predName.getSibling().getSibling();
			// for project 5, the rules should all have identifiers as the arguments
			ArrayList<String> toProject = new ArrayList<String>();
			ArrayList<HeadPair> actualMap = new ArrayList<HeadPair>();
			ArgumentList curList = args;
			boolean more = true;
			int j = 0;
			while(more) {
				Argument temp = (Argument)curList.getChild();
				String value = ((Token)temp.getChild().getRoot()).getValue();
				actualMap.add(new HeadPair(answer.getSchema().getAttrs().get(j), value));
				toProject.add(value);
				ArgumentListTail tail = (ArgumentListTail)temp.getSibling();
				if(tail.getRoot() != null) {
					curList = (ArgumentList)tail.getChild().getSibling();
				} else {
					more = false;
				}
			}
			
			/** 
			 * get the relations to be joined by the rules and add them to preds
			 */
			PredicateList predList = (PredicateList) pred.getSibling().getSibling();
			ArrayList<PredicateName> preds = new ArrayList<PredicateName>();
			PredicateList curList2 = predList;
			boolean more2 = true;
			while(more2) {
				Predicate temp = (Predicate)curList2.getChild();
				PredicateName predicate = (PredicateName) temp.getChild();
				preds.add(predicate);
				PredicateListTail tail = (PredicateListTail)temp.getSibling();
				if(tail.getRoot() != null) {
					curList2 = (PredicateList)tail.getChild().getSibling();
				} else {
					more2 = false;
				}
			}
			
			/**
			 * create the relations from each of the preds
			 */
			ArrayList<Relation> predRelations = new ArrayList<Relation>();
			for(PredicateName name : preds) {
				Relation temp = null;
				String interName = ((Token)name.getChild().getRoot()).getValue();
				for(Relation relation : relations) {
					if(relation.getName().equals(interName)) {
						temp = new Relation(relation, true);
						break;
					}
				}
				if(temp != null) {
					ParameterList list = (ParameterList) name.getSibling().getSibling();
					ArrayList<Argument> args0 = new ArrayList<Argument>();
					ParameterList curList3 = list;
					boolean more3 = true;
					while(more3) {
						Parameter temp2 = (Parameter)curList3.getChild();
						Argument argument = (Argument) temp2.getChild();
						args0.add(argument);
						ParameterListTail tail = (ParameterListTail)temp2.getSibling();
						if(tail.getRoot() != null) {
							curList3 = (ParameterList)tail.getChild().getSibling();
						} else {
							more3 = false;
						}
					}
					for(int i = 0; i < args0.size(); i++) {
						String type = args0.get(i).getChild().getClass().getSimpleName();
						
						String value = ((Token)args0.get(i).getChild().getRoot()).getValue();
						
						if(type.equals("StringNT")) {
							temp = temp.select(i, value);
						} else {
							temp = temp.rename(i, value);
						}
					}
					
					predRelations.add(temp);
				}
				
			}
			Relation toUnion = predRelations.get(0);
			if(predRelations.size() > 0) {
				for(int i = 1; i <= predRelations.size()-1; i++) {
					toUnion = toUnion.join(predRelations.get(i));
				}
			} 
			toUnion = toUnion.project(toProject, true);
			
			Relation toCompare = new Relation(answer, true);
			answer = answer.union(toUnion, actualMap);
			
			ArrayList<Relation> tempRelations = new ArrayList<Relation>();
			for(Relation relation : relations) {
				tempRelations.add(relation);
			}
			for(Relation relation : tempRelations) {
				if(relationName.equals(relation.getName())) {
					relations.remove(relation);
					relations.add(answer);
					break;
				}
			}
			
			if(!toCompare.equals(answer)) {
				needToIterate = true;
			}
		}
		iterations++;
	}
	
	private void processQueries(ArrayList<Query> queries) {
		System.out.println("Schemes populated after "+iterations+" passes through the Rules.");
		for(Query query : queries) {
			SimplePredicate pred = (SimplePredicate) query.getChild();
			PredicateName predName = (PredicateName) pred.getChild();
			String relationName = ((Token) predName.getChild().getRoot()).getValue();
			Relation answer = null;
			
			
			for(Relation relation : relations) {
				if(relation.getName().equals(relationName)) {
					answer = relation;
					break;
				}
			}
			
			ArgumentList args = (ArgumentList) predName.getSibling().getSibling();
			ArrayList<Argument> arguments = new ArrayList<Argument>();
			ArgumentList curList = args;
			boolean more = true;
			while(more) {
				Argument temp = (Argument)curList.getChild();
				arguments.add(temp);
				ArgumentListTail tail = (ArgumentListTail)temp.getSibling();
				if(tail.getRoot() != null) {
					curList = (ArgumentList)tail.getChild().getSibling();
				} else {
					more = false;
				}
			}
			
			
			ArrayList<Integer> modified = new ArrayList<Integer>();
			for(int i = 0; i < arguments.size(); i++) {
				String type = arguments.get(i).getChild().getClass().getSimpleName();
				
				String value = ((Token)arguments.get(i).getChild().getRoot()).getValue();
				
				if(type.equals("Identifier")) {
					
					if(answer.getSchema().getAttrs().contains(value)) {
						int index = answer.getSchema().getAttrs().indexOf(value);
							if(modified.contains(index)) {
							LinkedHashSet<Tuple> temp = new LinkedHashSet<Tuple>();
							for(Tuple tuple : answer.getTuples()) {
								if(tuple.indexesEqual(i, index))
									temp.add(tuple);
							}
							answer.setTuples(temp);
						}
					}
					answer = answer.rename(i, value);
					modified.add(i);
				} else {
					answer = answer.select(i, value);
				}
			}
			
			answer = answer.project(modified);
			
			QueryAnswer queryAnswer = null;
			if(modified.size() == 0)
				queryAnswer = new QueryAnswer(pred.toString(), answer, false);
			else
				queryAnswer = new QueryAnswer(pred.toString(), answer, true);
			
			System.out.println(queryAnswer);
			
		}
		System.out.println("Done!");
	}
}

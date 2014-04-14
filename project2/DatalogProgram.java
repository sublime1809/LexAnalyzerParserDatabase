//REQUIRED CLASS
package project2;

import java.io.IOException;
import java.util.ArrayList;

import project1.*;
import project1.Token.*;

public class DatalogProgram extends ParseTree{
	
	private ArrayList<Fact> factList = new ArrayList<Fact>();
	private ArrayList<Scheme> schemeList = new ArrayList<Scheme>();
	private ArrayList<Query> queryList = new ArrayList<Query>();
	private ArrayList<Rule> ruleList = new ArrayList<Rule>();

	public DatalogProgram(String fileName) throws InvalidInputException, IOException{
		
		super("DatalogProgram");
		MyTokenGetter tokenGetter = new MyTokenGetter(fileName);
		Token cur = MyTokenGetter.peek();			// new MyTokenGenerator
		ParseTree current = null;
		
		if(cur.getType() == TokenType.SCHEMES){		// if TokenType == Schemes :	// titleS -> (number) -> colon
			ParseTree titleS = new ParseTree(cur);
			this.setChild(titleS);
			cur = MyTokenGetter.nextToken();		// if TokenType == Colon :
			if (cur.getType() == TokenType.COLON){	// parseTree rightChild = SchemeList
				ParseTree colon = new ParseTree(cur);
				MyTokenGetter.nextToken();
				ParseTree schemes = new SchemeList();
				int numberSL = SchemeList.getSize();
				titleS.setSibling(new ParseTree("("));
				ParseTree numberS = new ParseTree(numberSL);
				titleS.getSibling().setSibling(numberS);
				numberS.setSibling(new ParseTree(")"));
				numberS.getSibling().setSibling(colon);
				colon.setSibling(schemes);
				current = schemes;
				schemeList = SchemeList.getSchemes();
			} else {
				throw new InvalidInputException(cur);
			}
		} else {
			throw new InvalidInputException(cur);
		}
		
		cur = MyTokenGetter.peek();
		
		if(cur.getType() == TokenType.FACTS){
			ParseTree enter = new ParseTree("\n");
			ParseTree titleF = new ParseTree(cur);
			current.setSibling(enter);
			enter.setSibling(titleF);
			cur = MyTokenGetter.nextToken();
			if(cur.getType() == TokenType.COLON){
				ParseTree colonF = new ParseTree(cur);
				MyTokenGetter.nextToken();
				ParseTree facts = new FactList();
				int numberFL = FactList.getSize();
				titleF.setSibling(new ParseTree("("));
				ParseTree numberF = new ParseTree(numberFL);
				titleF.getSibling().setSibling(numberF);
				numberF.setSibling(new ParseTree(")"));
				numberF.getSibling().setSibling(colonF);
				colonF.setSibling(facts);
				current = facts;
				factList = FactList.getFacts();
			}  else {
				throw new InvalidInputException(cur);
			}
		} else {
			throw new InvalidInputException(cur);
		}
		
		cur = MyTokenGetter.peek();
		
		if(cur.getType() == TokenType.RULES){
			ParseTree enter = new ParseTree("\n");
			ParseTree titleR = new ParseTree(cur);
			current.setSibling(enter);
			enter.setSibling(titleR);
			cur = MyTokenGetter.nextToken();
			if(cur.getType() == TokenType.COLON){
				ParseTree colonR = new ParseTree(cur);
				MyTokenGetter.nextToken();
				ParseTree rules = new RuleList();
				int numberRL = RuleList.getSize();
				titleR.setSibling(new ParseTree("("));
				ParseTree numberR = new ParseTree(numberRL);
				titleR.getSibling().setSibling(numberR);
				numberR.setSibling(new ParseTree(")"));
				numberR.getSibling().setSibling(colonR);
				colonR.setSibling(rules);
				current = rules;
				ruleList = RuleList.getRules();
			} else {
				throw new InvalidInputException(cur);
			}
		} else {
			throw new InvalidInputException(cur);
		} 
		
		cur = MyTokenGetter.peek();
		
		if(cur.getType() == TokenType.QUERIES){
			ParseTree enter = new ParseTree("\n");
			ParseTree titleQ = new ParseTree(cur);
			current.setSibling(enter);
			enter.setSibling(titleQ);
			cur = MyTokenGetter.nextToken();
			if(cur.getType() == TokenType.COLON){
				ParseTree colonQ = new ParseTree(cur);
				MyTokenGetter.nextToken();
				ParseTree queries = new QueryList();
				int numberQL = QueryList.getSize();
				titleQ.setSibling(new ParseTree("("));
				ParseTree numberQ = new ParseTree(numberQL);
				titleQ.getSibling().setSibling(numberQ);
				numberQ.setSibling(new ParseTree(")"));
				numberQ.getSibling().setSibling(colonQ);
				colonQ.setSibling(queries);
				current = queries;
				queryList = QueryList.getQueries();
			}
		} else {
			throw new InvalidInputException(cur);
		}
		if(Domain.getSize() > 0){
			ParseTree enter = new ParseTree("\n");
			ParseTree titleD = new ParseTree("Domain(");
			current.setSibling(enter);
			enter.setSibling(titleD);
			int numberDL = Domain.getSize();
			ParseTree numberD = new ParseTree(numberDL);
			titleD.setSibling(numberD);
			numberD.setSibling(new ParseTree("):"));
			ParseTree domainList = new ParseTree(Domain.String());
			numberD.getSibling().setSibling(domainList);
		}
		cur = MyTokenGetter.peek();
		if(cur.getType() != TokenType.EOF){
			throw new InvalidInputException(cur);
		}
	}
	
	public ArrayList<Scheme> getSchemes() {
		return schemeList;
	}
	public ArrayList<Fact> getFacts() {
		return factList;
	}
	public ArrayList<Query> getQueries() {
		return queryList;
	}
	public ArrayList<Rule> getRules() {
		return ruleList;
	}
}

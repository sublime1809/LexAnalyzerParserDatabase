package project2;

import java.io.*;
import java.util.*;

import project1.*;
 
public final class MyTokenGetter {
	private static LexicalAnalyzer analyzer;
	private static ListIterator<Token> iterator;
	private static Token next;
	private static Token cur;
	
	public MyTokenGetter(String filename) throws IOException{
		try {
			analyzer = new LexicalAnalyzer(filename);
			iterator = analyzer.getListIterator();
			next = iterator.next();
		} catch (LexicalException e) {
			throw new IOException("Cannot find file.");
		}
	}
	
	public static boolean hasMoreTokens(){
		return iterator.hasNext();
	}
	
	public static Token peek(){
		while (next.getType() == TokenType.COMMENT){
			next = iterator.next();
		}
		return next;
	}
	
	public static Token nextToken(){
		cur = next;
		next = iterator.next();
		while (next.getType() == TokenType.COMMENT){
			next = iterator.next();
		}
		return next;
	}
}

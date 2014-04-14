//REQUIRED CLASS
package project1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class LexicalAnalyzer
{
	InputReader input;
	private ArrayList<Token> tokens = new ArrayList<Token>();
	
	public LexicalAnalyzer(String fileName) throws LexicalException {
		State curState = State.START;
		try {
			input = new InputReader(new File(fileName));
			if(curState != State.EOF) {
				do {
					Transition trans = curState.nextTransition(InputReader.getCurrent());
					curState = trans.getNextState();
					if(!InputReader.hasNext()) {
						//System.out.println("Has no more: " + (int)InputReader.getCurrent());
						break;
					} 
					InputReader.getNext();
				} while(curState != State.EOF);
				if(curState == State.EOF) {
					Transition trans = curState.nextTransition(InputReader.getCurrent());
					curState = trans.getNextState();
				}
			}
			tokens = State.getTokens();
			
		} catch (IOException e) {
			throw new LexicalException("Cannot Find File");
		}
	}
	
	public String toString() {
		StringBuilder string = new StringBuilder();
		for(Token token : tokens) {
			string.append(token.toString() + "\n");
		}
		string.append("Total Tokens = " + tokens.size());
		return string.toString();
	}
	
	public ListIterator<Token> getListIterator() {
		ListIterator<Token> iterator = tokens.listIterator();
		return iterator;
	}
}

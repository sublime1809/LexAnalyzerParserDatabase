package project1;

import java.util.ArrayList;

public enum State {
	START {
		public Transition nextTransition(int c)  {
			State nextState = whatWouldStartDo(InputReader.getCurrent());
			return new Transition(nextState, null);
		}
	},
	EOF {
		public Transition nextTransition(int c)  {
			tokens.add(new Token(TokenType.EOF, "", InputReader.getLine()));
			return new Transition(null, TokenType.EOF);
		}
	},
	STRING (true){
		public Transition nextTransition(int c)  {
			if(c == (int) '\'') {
				TokenBuilder.append((char)c);
				return new Transition(State.STRING_CHECK, TokenType.STRING);
			} else if(c == (char) -1) {
				addToken(TokenType.UNDEFINED);
				State nextState = whatWouldStartDo(c);
				return new Transition(nextState, TokenType.EOF);
			} else {
				TokenBuilder.append((char)c);
				return new Transition(State.STRING, TokenType.STRING);
			} 
		}
	},
	STRING_CHECK {
		public Transition nextTransition(int c)  {
			if(c == (int) '\'') {
				TokenBuilder.append((char)c);
				return new Transition(State.STRING, TokenType.STRING);
			} else {
				addToken(TokenType.STRING);
				State nextState = whatWouldStartDo(c);
				//tokens.add(new Token(TokenType.STRING, TokenBuilder.getTokenValue(), TokenBuilder.getLine()));
				return new Transition(nextState, TokenType.STRING);
			}
		}
	},
	IDENTIFIER {
		public Transition nextTransition(int c)  {
			if(Character.isWhitespace(c)) {
				//tokens.add(new Token(TokenType.ID, TokenBuilder.getTokenValue(), TokenBuilder.getLine()));
				int line = TokenBuilder.getLine();
				String value = TokenBuilder.getTokenValue();
				if(value.equals("Schemes")) {
					tokens.add(new Token(TokenType.SCHEMES, value, line));
				} else if( value.equals("Facts")) {
					tokens.add(new Token(TokenType.FACTS, value, line));
				} else if(value.equals("Rules")) {
					tokens.add(new Token(TokenType.RULES, value, line));
				} else if(value.equals("Queries")) {
					tokens.add(new Token(TokenType.QUERIES, value, line));
				} else {
					tokens.add(new Token(TokenType.ID, value, line));
				}
				State nextState = whatWouldStartDo(c);
				return new Transition(nextState, TokenType.ID);
			} else if(Character.isDigit(c) || Character.isLetter(c)) {
				TokenBuilder.append((char)c);
				return new Transition(State.IDENTIFIER, TokenType.ID);
			} else {
				int line = TokenBuilder.getLine();
				String value = TokenBuilder.getTokenValue();
				if(value.equals("Schemes")) {
					tokens.add(new Token(TokenType.SCHEMES, value, line));
				} else if( value.equals("Facts")) {
					tokens.add(new Token(TokenType.FACTS, value, line));
				} else if(value.equals("Rules")) {
					tokens.add(new Token(TokenType.RULES, value, line));
				} else if(value.equals("Queries")) {
					tokens.add(new Token(TokenType.QUERIES, value, line));
				} else {
					tokens.add(new Token(TokenType.ID, value, line));
				}
				State nextState = whatWouldStartDo(c);
				//tokens.add(new Token(TokenType.STRING, TokenBuilder.getTokenValue(), TokenBuilder.getLine()));
				return new Transition(nextState, TokenType.ID);
			}
		}
	},
	COLON {
		public Transition nextTransition(int c)  {
			if(c == (int) '-') {
				TokenBuilder.append((char)c);
				return new Transition(State.COLON_DASH, TokenType.COLON);
			} else {
				addToken(TokenType.COLON);
				State nextState = whatWouldStartDo(c);
				return new Transition(nextState, TokenType.COLON);
			}
		}
	},
	COLON_DASH {
		public Transition nextTransition(int c)  {
			addToken(TokenType.COLON_DASH);
			State nextState = whatWouldStartDo(c);
			return new Transition(nextState, TokenType.COLON_DASH);
		}
	},
	LT {
		public Transition nextTransition(int c)  {
			if(c == '='){
				TokenBuilder.append((char)c);
				return new Transition(State.LE, TokenType.LT);
			} else {
				addToken(TokenType.LT);
				State nextState = whatWouldStartDo(c);
				return new Transition(nextState, TokenType.LT);
			}
		}
	},
	LE {
		public Transition nextTransition(int c)  {
			addToken(TokenType.LE);
			State nextState = whatWouldStartDo(c);
			return new Transition(nextState, TokenType.LE);
		}
	},
	GT {
		public Transition nextTransition(int c)  {
			if(c == '=') {
				TokenBuilder.append((char)c);
				return new Transition(State.GE, TokenType.GT);
			} else {
				addToken(TokenType.GT);
				State nextState = whatWouldStartDo(c);
				return new Transition(nextState, TokenType.GT);
			}
		}
	},
	GE {
		public Transition nextTransition(int c)  {
			addToken(TokenType.GE);
			State nextState = whatWouldStartDo(c);
			return new Transition(nextState, TokenType.GE);
		}
	},
	PERIOD {
		public Transition nextTransition(int c)  {
			addToken(TokenType.PERIOD);
			State nextState = whatWouldStartDo(c);
			return new Transition(nextState, TokenType.PERIOD);
		}
	},
	ADD {
		public Transition nextTransition(int c)  {
			addToken(TokenType.ADD);
			State nextState = whatWouldStartDo(c);
			return new Transition(nextState, TokenType.ADD);
		}
	},
	MULTIPLY {
		public Transition nextTransition(int c)  {
			addToken(TokenType.MULTIPLY);
			State nextState = whatWouldStartDo(c);
			return new Transition(nextState, TokenType.MULTIPLY);
		}
	},
	EX { 	// "!"
		public Transition nextTransition(int c)  {
			if(c != '=') {
				addToken(TokenType.UNDEFINED);
				State nextState = whatWouldStartDo(c);
				return new Transition(nextState, TokenType.UNDEFINED);
			} else {
				TokenBuilder.append((char)c);
				return new Transition(State.NE, TokenType.UNDEFINED);
			}
		}
	},
	NE {	// "!="
		public Transition nextTransition(int c)  {
			addToken(TokenType.NE);
			State nextState = whatWouldStartDo(c);
			return new Transition(nextState, TokenType.NE);
		}
	},
	EQ {	// "="
		public Transition nextTransition(int c)  {
			addToken(TokenType.EQ);
			State nextState = whatWouldStartDo(c);
			return new Transition(nextState, TokenType.EQ);
		}
	},
	Q_MARK {	// "?"
		public Transition nextTransition(int c)  {
			addToken(TokenType.Q_MARK);
			State nextState = whatWouldStartDo(c);
			return new Transition(nextState, TokenType.Q_MARK);
		}
	},
	RIGHT_PAREN {	// ")"
		public Transition nextTransition(int c)  {
			addToken(TokenType.RIGHT_PAREN);
			State nextState = whatWouldStartDo(c);
			return new Transition(nextState, TokenType.RIGHT_PAREN);
		}
	},
	LEFT_PAREN {	// "("
		public Transition nextTransition(int c)  {
			addToken(TokenType.LEFT_PAREN);
			State nextState = whatWouldStartDo(c);
			return new Transition(nextState, TokenType.LEFT_PAREN);
		}
	},
	COMMA {		// ","State nextState = whatWouldStartDo(c);
		public Transition nextTransition(int c)   {
			addToken(TokenType.COMMA);
			State nextState = whatWouldStartDo(c);
			return new Transition(nextState, TokenType.COMMA);
		}
	},
	COMMENT (true){	// "#"
		public Transition nextTransition(int c)  {
			if(c == '|') {
				TokenBuilder.append((char)c);
				return new Transition(State.MULTILINE_COMMENT, TokenType.COMMENT);
			} else if(c == '\n' || c == (char)-1) {
				addToken(TokenType.COMMENT);
				return new Transition(State.START, TokenType.COMMENT);
			} else {
				TokenBuilder.append((char)c);
				return new Transition(State.SINGLELINE_COMMENT, TokenType.COMMENT);
			}
		}
	},
	MULTILINE_COMMENT (true) {		// "#|"
		public Transition nextTransition(int c)  {	
			if(c == '|') {
				TokenBuilder.append((char)c);
				return new Transition(State.MULTILINE_CHECK, TokenType.COMMENT);
			} else if(c == (char)-1) {
				addToken(TokenType.UNDEFINED);
				State nextState = whatWouldStartDo(c);
				return new Transition(nextState, TokenType.UNDEFINED);
			} else {
				TokenBuilder.append((char)c);
				return new Transition(State.MULTILINE_COMMENT, TokenType.COMMENT);
			}
		}	
	},
	MULTILINE_CHECK (true){		// "#||"
		public Transition nextTransition(int c)  {
			TokenBuilder.append((char)c);
			if(c == '#') {
				addToken(TokenType.COMMENT);
				return new Transition(State.START, TokenType.COMMENT);
			} else if(c == (char) -1) {
				addToken(TokenType.UNDEFINED);
				State nextState = whatWouldStartDo(c);
				return new Transition(nextState, null);
			} else {
				TokenBuilder.append((char)c);
				return new Transition(State.MULTILINE_COMMENT, TokenType.COMMENT);
			}
		}
	},
	SINGLELINE_COMMENT (true){	// "#_"
		public Transition nextTransition(int c)  {
			if(c == '\n' || c == (char)-1) {
				addToken(TokenType.COMMENT);
				State nextState = whatWouldStartDo(c);
				return new Transition(nextState, TokenType.COMMENT);
			} else {
				TokenBuilder.append((char)c);
				return new Transition(State.SINGLELINE_COMMENT, TokenType.COMMENT);
			}
		}
	},
	UNDEFINED {
		public Transition nextTransition(int c)  {
			addToken(TokenType.UNDEFINED);
			State nextState = whatWouldStartDo(c);
			return new Transition(nextState, TokenType.UNDEFINED);
		}
	};
	
	public static ArrayList<Token> tokens = new ArrayList<Token>();
	
	public abstract Transition nextTransition(int c) ;
	private boolean retainWhiteSpace;
	
	private State(boolean retainWhiteSpace) {
		this.retainWhiteSpace = retainWhiteSpace;
	}
	private State() {
		this.retainWhiteSpace = false;
	}
	
	public boolean retainWS() {
		return this.retainWhiteSpace;
	}
	
	public static void addToken(TokenType type) {
		int line = TokenBuilder.getLine();
		tokens.add(new Token(type, TokenBuilder.getTokenValue(), line));
	}
	
	public static ArrayList<Token> getTokens() {
		return tokens;
	}
	
	public static State whatWouldStartDo(int c) {
		char ch = (char) c;
		if(c != -1) {
			while((Character.isWhitespace(ch) || ch == ' ') && ch != (char) -1) {
				if(InputReader.hasNext())
					ch = InputReader.getNext();
			} 
		}
		TokenBuilder.append(ch);
		switch(ch) {
		case '\'' :
			return State.STRING;
		case '#' :
			return State.COMMENT;
		case ',' :
			return State.COMMA;
		case ':' :
			return State.COLON;
		case '<' :
			return State.LT;
		case '>':
			return State.GT;
		case '.' :
			return State.PERIOD;
		case '+':
			return State.ADD;
		case '*' :
			return State.MULTIPLY;
		case '!' :
			return State.EX;
		case '=' :
			return State.EQ;
		case '?' :
			return State.Q_MARK;
		case '(' : 
			return State.LEFT_PAREN;
		case ')' :
			return State.RIGHT_PAREN;
		default:
			if(Character.isWhitespace(ch)) {
				return whatWouldStartDo(InputReader.getNext());
			} else if(Character.isLetter(ch)) {
				return State.IDENTIFIER;
			} else if(ch == (char)-1) {
				return State.EOF;
			} else {
				return State.UNDEFINED;
			}
		}
	}
}


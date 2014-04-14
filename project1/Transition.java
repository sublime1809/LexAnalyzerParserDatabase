package project1;

public class Transition {
	
	private State nextState;
	private TokenType tokenType;
	
	public Transition(State nextState, TokenType tokenType) {
		this.nextState = nextState;
		this.tokenType = tokenType;
	}
	
	public State getNextState() {
		return this.nextState;
	}
	public TokenType getTokenType() {
		return this.tokenType;
	}
	
}

//REQUIRED CLASS
package project1;

public class Token
{
	TokenType tokenType;
	String tokenValue;
	int lineNumber;
	
	public Token(TokenType type, String value, int line) {
		this.tokenType = type;
		this.tokenValue = value;
		this.lineNumber = line;
	}
	
	public TokenType getType() {
		return this.tokenType;
	}
	public String getValue() {
		return this.tokenValue;
	}
	public int getLineNumber() {
		return this.lineNumber;
	}
	
	public void setType(TokenType type) {
		this.tokenType = type;
	}
	public void setValue(String value) {
		this.tokenValue = value;
	}
	public void setLine(int line) {
		this.lineNumber = line;
	}
	
	public String toString() {
		return "(" + this.tokenType + ",\"" + this.tokenValue + "\"," + this.lineNumber + ")";
	}
}
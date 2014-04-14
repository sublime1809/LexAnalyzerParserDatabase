package project2;

import project1.*;
import project1.TokenType;

public class ParseTree {
	private Object value;
	private ParseTree leftChild;
	private ParseTree rightSibling;
	
	public ParseTree(Object value, ParseTree leftChild, ParseTree rightSibling){
		this.value = value;
		this.leftChild = leftChild;
		this.rightSibling = rightSibling;
	}
	public ParseTree(Object value , ParseTree leftChild){
		this.value = value;
		this.leftChild = leftChild;
		this.rightSibling = null;
	}
	public ParseTree(Object value){
		this.value = value;
		this.leftChild = null;
		this.rightSibling = null;
	}
	
	public Object getRoot(){
		return value;
	}
	public ParseTree getChild(){
		return leftChild;
	}
	public ParseTree getSibling(){
		return rightSibling;
	}
	
	public void setRoot(Object value){
		this.value = value;
	}
	public void setChild (ParseTree child){
		this.leftChild = child;
	}
	public void setSibling (ParseTree sibling){
		this.rightSibling = sibling;
	}
	
	public String toString(){
		if(leftChild == null && rightSibling == null){		// TERMINAL	
			if(this.value == null)
				return "";
			else if(this.value instanceof Token){
				Token value = (Token) this.value;
				/*if(value.getType() == TokenType.STRING) {
					return "'" + value.getValue() + "'";
				}*/
				return value.getValue();
			}
			return this.value.toString();
		} else if (leftChild != null && rightSibling == null){
			return leftChild.toString();
		} else if (leftChild == null && rightSibling != null){
			if(this.value instanceof Token){
				Token value = (Token) this.value;
				/*if(value.getType() == TokenType.STRING) {
					return "'" + value.getValue() + "'" + rightSibling.toString();
				} else */ 
				if (value.getType() == TokenType.COLON_DASH){
					return " " + value.getValue() + " " + rightSibling.toString();
				}
				return value.getValue() + rightSibling.toString();
			}
			if(this.value == null)
				return rightSibling.toString();
			return this.value.toString() + rightSibling.toString();
		} else
			return leftChild.toString() + rightSibling.toString();
	}
}

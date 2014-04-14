package project4;

public class AVPairs {
	
	private String attr;
	private String value;
	private int index;
	
	public AVPairs(String attr, String value, int index) {
		this.attr = attr;
		this.value = value;
		this.index = index;
	}
	
	
	/** Simple test method to see if we want this pair **/
	public boolean isValueEqualTo(String test) {
		return this.value.equals(test);
	}

	
	/** To make sure that we are only adding unique avpairs to a tuple **/
	@Override
	public boolean equals(Object toCompare) {
		AVPairs compare = (AVPairs) toCompare;
		if(compare.getAttr().equals(this.attr) && compare.getValue().equals(this.value)) {
			return true;
		} else {
			return false;
		}
	}
	@Override
	public int hashCode() {
		return this.attr.hashCode() + this.value.hashCode();
	}
	
	
	/** Getter/Setters **/
	public void setAttr(String attr) {
		this.attr = attr;
	}
	public void setValue(String value){
		this.value = value;
	}
	public void setIndex(int i) {
		this.index = i;
	}
	
	public String getAttr() {
		return this.attr;
	}
	public String getValue() {
		return this.value;
	}
	public int getIndex() {	
		return this.index;
	}
	
	
	/** Do I really need an explanation? **/
	public String toString() {
		return attr + "=" + value;
	}
}

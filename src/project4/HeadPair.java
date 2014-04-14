package project4;

public class HeadPair {
	
	private String original;
	private String renamed;
	private int origIndex;
	private int renamedIndex;
	
	public HeadPair(String original, String renamed, int origIndex, int renamedIndex) {
		this.original = original;
		this.renamed = renamed;
		this.origIndex = origIndex;
		this.renamedIndex = renamedIndex;
	}
	
	public HeadPair(String original, String renamed) {
		this.original = original;
		this.renamed = renamed;
	}
	
	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	public String getRenamed() {
		return renamed;
	}

	public void setRenamed(String renamed) {
		this.renamed = renamed;
	}

	public int getOrigIndex() {
		return origIndex;
	}

	public void setOrigIndex(int origIndex) {
		this.origIndex = origIndex;
	}

	public int getRenamedIndex() {
		return renamedIndex;
	}

	public void setRenamedIndex(int renamedIndex) {
		this.renamedIndex = renamedIndex;
	}
	
}

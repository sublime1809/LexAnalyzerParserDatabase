package project4;

public class QueryAnswer {

	private String query;
	private Relation answer;
	private boolean displayTuples;
	
	public QueryAnswer(String query, Relation answer, boolean displayTuples) {
		this.query = query;
		this.answer = answer;
		this.displayTuples = displayTuples;
	}
	
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append(this.query + " ");
		if(answer.getTuples().size() == 0) {
			string.append("No");
		} else {
			string.append("Yes(" + answer.getTuples().size() + ")");
			if(this.displayTuples)
				string.append("\n" + answer.printTuples());
		}
		return string.toString();
	}
}

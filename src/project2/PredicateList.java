package project2;

public class PredicateList extends ParseTree {
	
	public PredicateList() throws InvalidInputException {
		super("PredicateList");
		ParseTree predicate = new Predicate();
		ParseTree predicateListTail = new PredicateListTail();
		this.setChild(predicate);
		predicate.setSibling(predicateListTail);
	}
}

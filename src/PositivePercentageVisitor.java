/**
 * Assignment 2 - Mini Twitter
 * CS 356 - Object Oriented Programming
 * Dr. Sun
 * @author isoldealfaro
 *
 *PositivePercentageVisitor implements Vistable interface. Increments every time it is 
 *visited when a positive message is found.
 */
public class PositivePercentageVisitor implements Visitable {
	private static int total;
	
	@Override
	public void accept() {
		// TODO Auto-generated method stub
		total++;
	}
	
	public int get() {
		return total;
	}

}
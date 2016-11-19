/**
 * Assignment 2 - Mini Twitter
 * CS 356 - Object Oriented Programming
 * Dr. Sun
 * @author isoldealfaro
 *
 *TweetTotalVisitor implements Vistable interface. Increments tweet total every time it is
 *visited when a tweet is posted.
 */
public class TweetTotalVisitor implements Visitable {
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
/**
 * Assignment 2 - Mini Twitter
 * CS 356 - Object Oriented Programming
 * Dr. Sun
 * @author isoldealfaro
 *
 *UserTotalVisitor implements Visitable interface and increments every time it is visited
 *when a new user is created.
 */
//Every time a user is created, it visits this class and the user total is incremented
public class UserTotalVisitor implements Visitable {
	private static int userTotal;
	
	@Override
	public void accept() {
		// TODO Auto-generated method stub
		userTotal++;
	}
	
	public int get() {
		return userTotal;
	}

}

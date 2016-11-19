/**
 * Assignment 2 - Mini Twitter
 * CS 356 - Object Oriented Programming
 * Dr. Sun
 * @author isoldealfaro
 *
 *GroupTotalVisitor that implements the Visitable interface, increments groupTotal 
 *everytime it is visited
 */
public class GroupTotalVisitor implements Visitable {
	private static int groupTotal;
	
	@Override
	public void accept() {
		// TODO Auto-generated method stub
		groupTotal++;
	}
	
	public int get() {
		return groupTotal;
	}

}
/**
 * Assignment 2 - Mini Twitter
 * CS 356 - Object Oriented Programming
 * Dr. Sun
 * @author isoldealfaro
 *
 *Visitor interface that helps implement Visitor pattern.
 */
public interface Visitor {
	
	public void visit (UserTotalVisitor utv);
	public void visit (GroupTotalVisitor gtv);
	public void visit (TweetTotalVisitor ttv);
	public void visit (PositivePercentageVisitor ppv);
	
}
/**
 * Assignment 2 - Mini Twitter
 * CS 356 - Object Oriented Programming
 * Dr. Sun
 * @author isoldealfaro
 *
 *Observerable interface that works with Observer interface.  Implemented by UserView.
 */
public interface Observable {
	
	public void attach(String id);
    public void notifyObservers(String tweet);

}
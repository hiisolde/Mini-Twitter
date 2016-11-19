/**
 * Assignment 2 - Mini Twitter
 * CS 356 - Object Oriented Programming
 * Dr. Sun
 * @author isoldealfaro
 *
 *User Object that implements Component for the Composite pattern and the Observer pattern.
 */
import java.util.ArrayList;

public class User implements Component, Observer {

	private String userID = "";
	private ArrayList<String> followers;
	private ArrayList<String> followings;
	private ArrayList<String> newsfeed;

	public User(String id) {
		userID = id;
		followings = new ArrayList<String>();
		followers = new ArrayList<String>();
		newsfeed = new ArrayList<String>();
	}
	
	public User getUser() {
		return User.this;
	}

	public ArrayList<String> getFollowings() {
		return followings;
	}

	public ArrayList<String> getFollowers() {
		return followers;
	}

	public ArrayList<String> getNewsfeed() {

		return (ArrayList<String>) newsfeed;
	}

	public void addToFollowings(String string) {
		followings.add(string);
	}

	public void addToFollowers(String string) {
		followers.add(string);
	}

	@Override
	public void update(String string) {
		newsfeed.add(string);
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return userID;
	}
	
}
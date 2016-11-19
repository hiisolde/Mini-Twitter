/**
 * Assignment 2 - Mini Twitter
 * CS 356 - Object Oriented Programming
 * Dr. Sun
 * @author isoldealfaro
 *
 *Group object that implements the Compenent interface.
 */
public class Group implements Component {

	private String groupId = "";

	public Group(String name) {
		groupId = name;
	}
	
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return groupId;
	}
}
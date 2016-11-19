/**
 * Assignment 2 - Mini Twitter
 * CS 356 - Object Oriented Programming
 * Dr. Sun
 * @author isoldealfaro
 *
 *UserView implements Observerable interface and implements GUI to for a certain user
 *to follow other users, display their followings, post tweets, and display their
 *newsfeed.
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import javax.swing.*;
//import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class UserView extends JFrame implements Observable{

	private JPanel contentPane;
	private User currentUser;
	private JTextField followUserTextField;
	private JButton followUserButton;
	private JLabel currentlyFollowingLabel;
	private ArrayList<String> followingIdList;
	private DefaultListModel followingListModel;
	private JList followingList;
	private JLabel newsfeedLabel;
	private List<String> newsfeed;
	private DefaultListModel newsfeedListModel;
	private JList newsfeedList;
	private String tweet;
	private JTextField tweetField;	
	private JButton postTweetButton;
	private JScrollPane followingScrollPane;
	private JScrollPane newsfeedScrollPane;
	private String idToFollow;
	private HashMap<String, User> userMap = AdminControlPanel.getUserMap();

	@SuppressWarnings("unchecked")
	public UserView(User u) {		

		//Generate GUI components
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 450);
		
		currentUser = u.getUser();
		
		contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		newsfeedListModel = new DefaultListModel();

		followUserTextField = new JTextField();
		followUserTextField.setBounds(10, 10, 270, 20);
		//followUserTextField.setColumns(20);
		contentPane.add(followUserTextField);
		
		followUserButton = new JButton("Follow User");
		followUserButton.setBounds(290, 10, 115, 25);
		followUserButton.addActionListener(new FollowUserButtonListener());
		contentPane.add(followUserButton);
		
		currentlyFollowingLabel = new JLabel("Currently Following:");
		//20 to 10
		currentlyFollowingLabel.setBounds(10, 45, 175, 15);
		contentPane.add(currentlyFollowingLabel);
		
		followingListModel = new DefaultListModel();
		followingList = new JList(followingListModel);
		followingList.setBounds(10, 65, 415, 145);
		contentPane.add(followingList);
		followingIdList = (ArrayList<String>) currentUser.getFollowings().clone();
	
		//Add followings to list
		for (int i = 0; i < followingIdList.size(); i++){
			followingListModel.addElement(followingIdList.get(i));
		}

		tweetField = new JTextField();
		tweetField.setBounds(10, 230, 270, 20);
		contentPane.add(tweetField);
		
		postTweetButton = new JButton("Post tweet");
		postTweetButton.setBounds(290, 230, 115, 25);
		postTweetButton.addActionListener(new PostTweetButtonListener());
		contentPane.add(postTweetButton);

		newsfeedLabel = new JLabel("News Feed:");
		newsfeedLabel.setBounds(10, 265, 210, 15);
		contentPane.add(newsfeedLabel);

		newsfeedList = new JList(newsfeedListModel);
		newsfeedList.setBounds(10, 290, 415, 115);
		newsfeed = (ArrayList<String>) currentUser.getNewsfeed().clone();
		contentPane.add(newsfeedList);
		
		//Add tweets to newsfeed
		for (int i = 0; i < newsfeed.size(); i++){
			newsfeedListModel.addElement(newsfeed.get(i));
		}

		followingScrollPane = new JScrollPane(followingList);
		followingScrollPane.setBounds(10, 65, 415, 145);
		contentPane.add(followingScrollPane);

		newsfeedScrollPane = new JScrollPane(newsfeedList);
		newsfeedScrollPane.setBounds(10, 290, 415, 115);
		contentPane.add(newsfeedScrollPane);
	}
	
	//Notifies observers when a new tweet is posted
	public void notifyObservers(String tweet) {
		for (int i = 0; i < currentUser.getFollowers().size(); i++) {
			String follower = currentUser.getFollowers().get(i);
			userMap.get(follower).update(tweet);
		}
	}
	
	//Attaches inputed user to current user's followings, and updates that user's followers
	public void attach(String id) {
		currentUser.addToFollowings(id);
		userMap.get(id).addToFollowers(currentUser.getId());
	}
	
	//Follows a user
	private class FollowUserButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){	
			//Set the user ID of the user to follow
			idToFollow = followUserTextField.getText();
			
			if(!userMap.containsKey(idToFollow)) {
				JOptionPane.showMessageDialog(null, "User doesn't exist!");
			} else if (idToFollow.equals(currentUser.getId())) {
				JOptionPane.showMessageDialog(null, "You can't follow yourself!");
			} else if(currentUser.getFollowings().contains(idToFollow)){
				JOptionPane.showMessageDialog(null, "You already follow this user.");
			} else {
				attach(idToFollow);
				JOptionPane.showMessageDialog(null, "You are now following this user.");
				followingListModel.addElement(idToFollow);
			}
		}
	}
	
	//Posts a tweet to user's newfeed and anyone following them
	private class PostTweetButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){				
			tweet = "-   " + currentUser.getId() + ": " + tweetField.getText();
			
			//Current user sends tweet to all followers
			notifyObservers(tweet);
			currentUser.update(tweet);
			newsfeedListModel.addElement(tweet);;
			
			//Visit TweetTotalVisitor
			TweetTotalVisitor tweetTotal = new TweetTotalVisitor();
			tweetTotal.accept();
				
			//Check for positive words and visit PositivePercentageVisito
			String[] words = {"happy", "nice", "like", "love", "yay", "great", "amazing", "good"};
			
			PositivePercentageVisitor positiveTotal = new PositivePercentageVisitor();
			
			for (int i = 0; i < words.length; i++) {
				if (tweet.contains(words[i])) {
					positiveTotal.accept();
				}
			}
		};
	}	
}

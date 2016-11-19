/**
 * Assignment 2 - Mini Twitter
 * CS 356 - Object Oriented Programming
 * Dr. Sun
 * @author isoldealfaro
 *
 *Contains the GUI for AdminControlPanel and contains a JTree of users and groups,
 *and allows the user to add users or groups. Also contains buttons to launch UserView
 *GUI, and displays total users, groups, messages, and percent positive. Implements the
 *Visitor interface.
 */
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;

@SuppressWarnings("serial")
public class AdminControlPanel extends JFrame implements Visitor{
	private static AdminControlPanel instance;
	private JPanel panel;
	private String userID;
	private String groupID;
	private JTextField userTextBox;
	private JTextField groupTextBox;
	private JButton addUserButton;		
	private JButton addGroupButton;		
	private JButton userViewButton;		
	private JButton userTotalButton;		
	private JButton groupTotalButton;		
	private JButton tweetTotalButton;		
	private JButton postivePrecentageButton;
	private JTree tree;
	private TreeModel model;
	private DefaultMutableTreeNode root;
	private DefaultMutableTreeNode selectedNode;	
	private JScrollPane treeScrollPane;
	private static HashMap<String, User> userMap = new HashMap<String, User>();
	private static HashMap<String, Group> groupMap= new HashMap<String, Group>();
	
	//Implements Singleton pattern, creating one instance of the panel.
	public static AdminControlPanel getInstance(){
		if (instance == null)
			instance = new AdminControlPanel();
		return instance;
	}

	//Sets default node of the tree to the Root and calls the GUI implementation
	private AdminControlPanel() {	
		root = new DefaultMutableTreeNode("Root");
		initComponents();		
	}
	
	//Initializes all GUI components
	public void initComponents() {
		setBounds(100, 100, 660, 350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		setContentPane(panel);
		panel.setLayout(null);
		
		userTextBox = new JTextField(20);
		userTextBox.setBounds(185, 5, 280, 20);
		panel.add(userTextBox);
		
		addUserButton = new JButton("Add User");
		addUserButton.setBounds(470, 5, 160, 25);
		addUserButton.addActionListener(new AddUserListener());
		panel.add(addUserButton);
				
		groupTextBox = new JTextField(20);
		groupTextBox.setBounds(185, 40, 280, 20);
		panel.add(groupTextBox);
		
		addGroupButton = new JButton("Add Group");
		addGroupButton.setBounds(470, 40, 160, 25);		
		addGroupButton.addActionListener(new AddGroupListener());
		panel.add(addGroupButton);
		
		userViewButton = new JButton("Open User View");
		userViewButton.setBounds(185, 70, 450, 25);
		userViewButton.addActionListener(new userViewButtonListener());
		panel.add(userViewButton);
		
		userTotalButton = new JButton("Show User Total");
		userTotalButton.setBounds(185, 250, 220, 25);
		userTotalButton.addActionListener(new userTotalButtonListener());
		panel.add(userTotalButton);
		
		groupTotalButton = new JButton("Show Group Total");
		groupTotalButton.setBounds(415, 250, 220, 25);
		groupTotalButton.addActionListener(new groupTotalButtonListener());
		panel.add(groupTotalButton);
		
		tweetTotalButton = new JButton("Show Tweet Total");
		tweetTotalButton.setBounds(185, 280, 220, 25);
		tweetTotalButton.addActionListener(new tweetTotalButtonListener());
		panel.add(tweetTotalButton);
		
        postivePrecentageButton = new JButton("Show Positive Percentage");
        postivePrecentageButton.setBounds(415, 280, 220, 25);
        postivePrecentageButton.addActionListener(new postivePrecentageButtonListener());
        panel.add(postivePrecentageButton);

        tree = new JTree(root);
        tree.setBounds(10, 5, 150, 295);
        panel.add(tree);
        
        model = tree.getModel();

        treeScrollPane = new JScrollPane(tree);
        treeScrollPane.setBounds(10, 6, 150, 295);
        panel.add(treeScrollPane);
	}
	
	//Adds only valid users to tree under Root or groups
	private class AddUserListener implements ActionListener{
		public void actionPerformed(ActionEvent e){	
			
			userID = userTextBox.getText();
			selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

			if (userID.isEmpty() == true) {
				JOptionPane.showMessageDialog(null, "Please enter a user!");
			} else if (userMap.containsKey(userID)) {
				JOptionPane.showMessageDialog(null, "User already exists, please enter a new user.");
			} else if (selectedNode == null) {
				addUser(userID, root);
			} else if (userMap.containsKey(selectedNode.toString())) {
				JOptionPane.showMessageDialog(null, "You can only create users in the Root or groups");
			} else {
				addUser(userID, selectedNode);
			}
		}
	}
	
	//Adds user to tree and reloads tree
	public void addUser(String id, DefaultMutableTreeNode node) {
		User u = new User(id);	
		userMap.put(id, u);
		//System.out.print(userMap.keySet());
		UserTotalVisitor utv = new UserTotalVisitor();
		visit(utv);
		node.add(new DefaultMutableTreeNode(id));
		JOptionPane.showMessageDialog(null, "User has been added.");
		
		((DefaultTreeModel) model).reload();
	}

	//Adds only valid groups to tree under Root or other groups
	private class AddGroupListener implements ActionListener{
		public void actionPerformed(ActionEvent e){	
			
			groupID = groupTextBox.getText();
			selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			
			if (groupID.isEmpty() == true){
				JOptionPane.showMessageDialog(null, "Please enter a group name!");
			} else if (groupMap.containsKey(groupID)){
				JOptionPane.showMessageDialog(null, "Group name already exists, please enter another.");
			} else if (selectedNode == null){
				addGroup(groupID, root);
			} else if (userMap.containsKey(selectedNode.toString())){
				JOptionPane.showMessageDialog(null, "You can only create groups in the Root or other groups");
			} else{
				addGroup(groupID, selectedNode);
			}
		}
	}
	
	//Adds group to tree and reloads tree
	public void addGroup(String id, DefaultMutableTreeNode node){
		Group g = new Group(id);
		groupMap.put(id, g);
		GroupTotalVisitor gtv = new GroupTotalVisitor();
		visit(gtv);
		node.add(new DefaultMutableTreeNode(id));		
		JOptionPane.showMessageDialog(null, "Group has been added");
		((DefaultTreeModel) model).reload();
	}
	
	//Displays UserView GUI
	private class userViewButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){	
			
			selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (selectedNode == null || groupMap.containsKey(selectedNode.toString())){
            	JOptionPane.showMessageDialog(null, "Please select a user");
            } else {
            	User u = userMap.get(selectedNode.toString());
    			UserView userView = new UserView(u);
    			userView.setVisible(true);
            }
		}
	}
	
	//Displays total amount of users
	private class userTotalButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){	
			UserTotalVisitor userTotal = new UserTotalVisitor();
			JOptionPane.showMessageDialog(null, "Total number of users: " + userTotal.get());
		}
	}
	
	//Displays total amount of groups
	private class groupTotalButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){		
			GroupTotalVisitor groupTotal = new GroupTotalVisitor();
			JOptionPane.showMessageDialog(null, "Total number of groups: " + groupTotal.get());
		}
	}
	
	//Displays total amount of tweets
	private class tweetTotalButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){		
			TweetTotalVisitor tweetTotal = new TweetTotalVisitor();
			JOptionPane.showMessageDialog(null, "Total number of tweets: " + tweetTotal.get());
		}
	}
	
	//Displays percentage of tweets with select positive words
	private class postivePrecentageButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){	
			TweetTotalVisitor tweetTotal = new TweetTotalVisitor();
			double total = tweetTotal.get();
			
			PositivePercentageVisitor posTotal = new PositivePercentageVisitor();
			double pTotal = posTotal.get();
			
			double posPercent = (pTotal / total) * 100;
			JOptionPane.showMessageDialog(null, "Percent positive tweets: " + posPercent + "%");
	
		}
	}
	
	//Returns the userMap
	public static HashMap<String, User> getUserMap () {
		return userMap;
	}
	
	//Implements Visitor Interface
	public void visit (UserTotalVisitor utv) {
		utv.accept();
	}
	
	public void visit (GroupTotalVisitor gtv) {
		gtv.accept();
	}
	
	public void visit (TweetTotalVisitor ttv) {
	}
	
	public void visit (PositivePercentageVisitor ppv) {
	}

}
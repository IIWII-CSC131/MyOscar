package com.iiwii.myoscar.gui;

import com.iiwii.myoscar.UAC.UserAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * <p>The login panel to initialize the user account, or continue as guest</p>
 *
 * @author Eric Rodriguez
 * @version 1.0
 */
public class LoginPanel extends JPanel
{
	
	private final MainWindow frame;
	
	private final Font FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
	
	private JTextField     usernameField;
	private JPasswordField passwordField;
	private JLabel         statusLabel;
	
	/**
	 * <p>Initializes the JPanel</p>
	 * @param frame The main window frame. Allows the program to access its methods
	 */
	public LoginPanel(MainWindow frame)
	{
		this.frame = frame;
		initPanel();
	}
	
	/**
	 * <p>Initializes the JPanel and adds all components to it. Uses SpringLayout.</p>
	 */
	private void initPanel()
	{
		setSize(frame.getPreferredSize());
		SpringLayout lay = new SpringLayout();
		setLayout(lay);
		
		JLabel title = new JLabel("MyOscars GUI Window");
		title.setFont(new Font(FONT.getName(), FONT.getStyle(), 25));
		lay.putConstraint(SpringLayout.NORTH, title, 10, SpringLayout.NORTH, this);
		lay.putConstraint(SpringLayout.HORIZONTAL_CENTER, title, 0, SpringLayout.HORIZONTAL_CENTER, this);
		add(title);
		
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(FONT);
		lay.putConstraint(SpringLayout.WEST, usernameLabel, -180, SpringLayout.HORIZONTAL_CENTER, this);
		lay.putConstraint(SpringLayout.SOUTH, usernameLabel, -5, SpringLayout.VERTICAL_CENTER, this);
		add(usernameLabel);
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(FONT);
		lay.putConstraint(SpringLayout.WEST, passwordLabel, -180, SpringLayout.HORIZONTAL_CENTER, this);
		lay.putConstraint(SpringLayout.NORTH, passwordLabel, 5, SpringLayout.VERTICAL_CENTER, this);
		add(passwordLabel);
		
		usernameField = new JTextField();
		usernameField.setFont(new Font(FONT.getName(), FONT.getStyle(), 14));
		lay.putConstraint(SpringLayout.WEST, usernameField, 5, SpringLayout.EAST, usernameLabel);
		lay.putConstraint(SpringLayout.EAST, usernameField, 180, SpringLayout.HORIZONTAL_CENTER, this);
		lay.putConstraint(SpringLayout.VERTICAL_CENTER, usernameField, 0, SpringLayout.VERTICAL_CENTER, usernameLabel);
		add(usernameField);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font(FONT.getName(), FONT.getStyle(), 14));
		lay.putConstraint(SpringLayout.WEST, passwordField, 0, SpringLayout.WEST, usernameField);
		lay.putConstraint(SpringLayout.EAST, passwordField, 180, SpringLayout.HORIZONTAL_CENTER, this);
		lay.putConstraint(SpringLayout.VERTICAL_CENTER, passwordField, 0, SpringLayout.VERTICAL_CENTER, passwordLabel);
		add(passwordField);
		
		JButton loginButton = new JButton("Login");
		loginButton.setFont(FONT);
		loginButton.addActionListener(this::loginAction);
		lay.putConstraint(SpringLayout.WEST, loginButton, 10, SpringLayout.WEST, passwordLabel);
		lay.putConstraint(SpringLayout.EAST, loginButton, -5, SpringLayout.HORIZONTAL_CENTER, this);
		lay.putConstraint(SpringLayout.NORTH, loginButton, 10, SpringLayout.SOUTH, passwordLabel);
		add(loginButton);
		
		JButton createAccountButton = new JButton("Create Account");
		createAccountButton.setFont(FONT);
		createAccountButton.addActionListener(this::createAccountAction);
		lay.putConstraint(SpringLayout.WEST, createAccountButton, 5, SpringLayout.HORIZONTAL_CENTER, this);
		lay.putConstraint(SpringLayout.EAST, createAccountButton, -10, SpringLayout.EAST, passwordField);
		lay.putConstraint(SpringLayout.NORTH, createAccountButton, 0, SpringLayout.NORTH, loginButton);
		add(createAccountButton);
		
		JButton guestButton = new JButton("Continue as Guest");
		guestButton.setFont(FONT);
		guestButton.addActionListener(this::continueAsGuestAction);
		lay.putConstraint(SpringLayout.SOUTH, guestButton, -80, SpringLayout.SOUTH, this);
		lay.putConstraint(SpringLayout.HORIZONTAL_CENTER, guestButton, 0, SpringLayout.HORIZONTAL_CENTER, this);
		add(guestButton);
		
		//TODO: Edit the status label to allow showing errors
		statusLabel = new JLabel();
		statusLabel.setFont(FONT);
		lay.putConstraint(SpringLayout.SOUTH, statusLabel, -30, SpringLayout.NORTH, usernameLabel);
		lay.putConstraint(SpringLayout.HORIZONTAL_CENTER, statusLabel, 0, SpringLayout.HORIZONTAL_CENTER, this);
		add(statusLabel);
	}
	
	/**
	 * <p>Ignores the user account and allows the user to use the program without one.</p>
	 * @param evt   Ignored
	 */
	private void continueAsGuestAction(ActionEvent evt)
	{
		frame.changePanels(MainWindow.Panels.LOGIN, MainWindow.Panels.MAIN);
	}
	
	/**
	 * <p>Attempts to login the user.</p>
	 * @param evt   Ignored
	 */
	private void loginAction(ActionEvent evt)
	{
		UserAccount uac = new UserAccount();
		
		char[] pass = passwordField.getPassword();
		StringBuilder sb = new StringBuilder();
		for (char c : pass)
		{
			sb.append(c);
		}
		
		UserAccount.Status status = uac.createNewSession(usernameField.getText(), sb.toString());
		
		if (status != UserAccount.Status.OK)
		{
			displayError(status);
			return;
		}
		
		frame.setSentinel(uac.getSentinel());
		frame.changePanels(MainWindow.Panels.LOGIN, MainWindow.Panels.MAIN);
	}
	
	/**
	 * <p>Attempts to create a new user and login.</p>
	 * @param evt   Ignored
	 */
	private void createAccountAction(ActionEvent evt)
	{
		UserAccount uac = new UserAccount();
		
		char[] pass = passwordField.getPassword();
		StringBuilder sb = new StringBuilder();
		for (char c : pass)
		{
			sb.append(c);
		}
		
		UserAccount.Status status = uac.createNewUser(usernameField.getText(), sb.toString());
		
		if (status != UserAccount.Status.OK)
		{
			displayError(status);
			return;
		}
		
		frame.setSentinel(uac.getSentinel());
		frame.changePanels(MainWindow.Panels.LOGIN, MainWindow.Panels.MAIN);
	}
	
	/**
	 * <p>Displays the appropriate error if flagged.</p>
	 * @param status    The status of the user account
	 */
	private void displayError(UserAccount.Status status)
	{
		String text = "";
		switch (status)
		{
			case OK:
				break;
			case WRITE_ERROR:
				text = "Can't write user to the file";
				break;
			case USER_SPACE_ERROR:
				text = "Username can't contain spaces";
				break;
			case PASS_SPACE_ERROR:
				text = "Password can't contain spaces";
				break;
			case USER_LENGTH_ERROR:
				text = "Username must be at least 6 characters";
				break;
			case PASS_LENGTH_ERROR:
				text = "Password must be at least 8 characters";
				break;
			case USER_EXIST_ERROR:
				text = "User already exists";
				break;
			case USER_NOT_EXIST_ERROR:
				text = "User does not exist";
				break;
			case BAD_PASS_ERROR:
				text = "Password is incorrect";
				break;
		}
		
		statusLabel.setText(text);
		repaint();
	}
}

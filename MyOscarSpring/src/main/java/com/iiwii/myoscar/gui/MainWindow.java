package com.iiwii.myoscar.gui;

import com.iiwii.myoscar.uac.UserAccount;

import javax.swing.*;
import java.awt.*;

/**
 * <p>The main GUI window for the app.</p>
 *
 * @author Eric Rodriguez
 * @version 1.1
 */
public class MainWindow extends JFrame
{
	private final Dimension preferredFrameSize = new Dimension(1000, 600);
	private final double    SIDEBAR_RATIO      = 0.16;
	
	public Dimension preferredPanelSize = new Dimension(990, 590);
	
	private UserAccount  uac;
	private LoginPanel   loginPanel;
	private MainPanel    mainPanel;
	private SidebarPanel sidebarPanel;
	private JPanel       containerPanel;
	private Panels       previousPanel      = Panels.NIL;
	private Panels       currentPanel       = Panels.NIL;
	private boolean      isSidebarPresent   = false;
	private boolean      isContainerPresent = false;
	
	public MainWindow()
	{
		init();
	}
	
	/**
	 * <p>Initializes the main window by setting the content to a JPanel.</p>
	 */
	private void init()
	{
		// Ends the program when the window is closed. We can change this later if it suits us to run the Spring Boot 
		// API without the GUI also present
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("MyOscar - Spring Boot API");
		getContentPane().setPreferredSize(preferredFrameSize);
		pack();
		setLocationRelativeTo(null); // Puts the window centered on screen. It isn't perfectly centered but it's close
		setResizable(false);
		
		showLoginPanel();
	}
	
	/**
	 * <p>Display the login panel</p>
	 */
	private void showLoginPanel()
	{
		getContentPane().removeAll();
		loginPanel = new LoginPanel(this);
		add(loginPanel, BorderLayout.CENTER);
		repaint();
	}
	
	private void showMainPanel()
	{
		pack();
		if (!isSidebarPresent)
		{
			initSidebar();
			isSidebarPresent = true;
		}
		
		mainPanel = new MainPanel(this);
//		mainPanel.setBackground(Color.BLACK); // Tester
		
		int containerWidth = containerPanel.getPreferredSize().width;
		int sidebarWidth = sidebarPanel.getPreferredSize().width;
		mainPanel.setPreferredSize(new Dimension(
				containerWidth - sidebarWidth, containerPanel.getPreferredSize().height));
		
		containerPanel.add(mainPanel, BorderLayout.LINE_END);
		EventQueue.invokeLater(containerPanel::updateUI);
	}
	
	private void initSidebar()
	{
		sidebarPanel = new SidebarPanel(this);
//		sidebarPanel.setBackground(Color.BLUE); // Tester
		sidebarPanel.setPreferredSize(new Dimension((int) (containerPanel.getPreferredSize().width *
		                                                   SIDEBAR_RATIO), containerPanel.getPreferredSize().height));
		containerPanel.add(sidebarPanel, BorderLayout.LINE_START);
	}
	
	public void changePanels(Panels previousPanel, Panels nextPanel)
	{
		this.previousPanel = previousPanel;
		removeOldPanel();
		
		switch (nextPanel)
		{
			case NIL:
				dispose();
				break;
			case LOGIN:
				isSidebarPresent = false;
				showLoginPanel();
			case MAIN:
				showMainPanel();
				break;
			case SETTINGS:
				break;
			case MY_ACCOUNT:
				break;
			case MY_MOVIES:
				break;
			case MY_NOMINATIONS:
				break;
			case VOTES:
				break;
			case API:
				break;
		}
		
		currentPanel = nextPanel;
		
		if (!isContainerPresent)
		{
			add(containerPanel);
			repaint();
			isContainerPresent = true;
		}
	}
	
	private void removeOldPanel()
	{
		switch (previousPanel)
		{
			case NIL:
				break;
			case LOGIN:
				remove(loginPanel);
				initContainerPanel();
				break;
			case MAIN:
				containerPanel.remove(mainPanel);
				break;
			case SETTINGS:
				break;
			case MY_ACCOUNT:
				break;
			case MY_MOVIES:
				break;
			case MY_NOMINATIONS:
				break;
			case VOTES:
				break;
			case API:
				break;
		}
	}
	
	public void goBack()
	{
		changePanels(currentPanel, previousPanel);
	}
	
	private void initContainerPanel()
	{
		containerPanel = new JPanel();
		containerPanel.setPreferredSize(preferredPanelSize);
	}
	
	public Panels getCurrentPanel()
	{
		return currentPanel;
	}
	
	public void setUserAccount(UserAccount uac)
	{
		this.uac = uac;
	}
	
	enum Panels
	{
		NIL, LOGIN, MAIN, SETTINGS, MY_ACCOUNT, MY_MOVIES, MY_NOMINATIONS, VOTES, API
	}
}
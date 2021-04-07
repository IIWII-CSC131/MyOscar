package com.iiwii.myoscar.gui;

import com.iiwii.myoscar.UAC.Sentinel;
import com.iiwii.myoscar.UAC.UserAccount;

import javax.swing.*;
import java.awt.*;

/**
 * <p>The main GUI window for the app.</p>
 *
 * @author Eric Rodriguez
 * @version 1.0
 */
public class MainWindow extends JFrame
{
	private Sentinel sentinel;
	private UserAccount uac;
	
	SpringLayout lay;
	
	private LoginPanel   loginPanel;
	private MainPanel    mainPanel;
	private SidebarPanel sidebarPanel;
	
	private Panels previousPanel = Panels.NIL;
	
	private boolean sidebarPresent = false;
	private double  sidebarRatio   = 0.2;
	
	public MainWindow()
	{
		init();
	}
	
	/**
	 * <p>Initializes the main window by setting the content to a JPanel.</p>
	 */
	private void init()
	{
		// Ends the program when the window is closed. We can change this later if it suits us to run the Spring-Boot 
		// API without the GUI also present
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("MyOscar - Spring-Boot API");
		setPreferredSize(new Dimension(1000, 600));
		setSize(getPreferredSize());
		setLocationRelativeTo(null); // Puts the window centered on screen. It isn't perfectly centered but it's close
		setResizable(false);
		lay = new SpringLayout();
		setLayout(lay);
		
		//TODO: Create necessary components for JFrame and JPanel 
		
		showLoginPanel();
	}
	
	/**
	 * <p>Display the login panel</p>
	 */
	public void showLoginPanel()
	{
		getContentPane().removeAll();
		loginPanel = new LoginPanel(this);
		setContentPane(loginPanel);
		repaint();
	}
	
	public void showMainPanel()
	{
		getContentPane().removeAll();
		mainPanel = new MainPanel(this);
		System.out.println(mainPanel.getWidth());
		if (!sidebarPresent)
		{
			initSidebar();
		}
		
		//TODO: Fix this, doesn't seem to be working. Maybe use a different layout manager? 
		lay.putConstraint(SpringLayout.WEST, mainPanel, getSidebarPanelSize().width, SpringLayout.WEST, this);
		lay.putConstraint(SpringLayout.EAST, mainPanel, 0, SpringLayout.EAST, this);
		lay.putConstraint(SpringLayout.SOUTH, mainPanel, 0, SpringLayout.SOUTH, this);
		lay.putConstraint(SpringLayout.NORTH, mainPanel, 0, SpringLayout.NORTH, this);
		mainPanel.setBackground(Color.BLACK);
		add(mainPanel);
		repaint();
		
		System.out.println(mainPanel.getWidth());
	}
	
	public void initSidebar()
	{
		sidebarPanel = new SidebarPanel(this);
		sidebarPanel.setSize(getSidebarPanelSize());
		lay.putConstraint(SpringLayout.WEST, sidebarPanel, 0, SpringLayout.WEST, this);
		lay.putConstraint(SpringLayout.EAST, sidebarPanel, getSidebarPanelSize().width, SpringLayout.WEST, sidebarPanel);
		lay.putConstraint(SpringLayout.SOUTH, sidebarPanel, 0, SpringLayout.SOUTH, this);
		lay.putConstraint(SpringLayout.NORTH, sidebarPanel, 0, SpringLayout.NORTH, this);
//		sidebarPanel.setBackground(Color.BLACK);
//		add(sidebarPanel);
		sidebarPresent = true;
	}
	
	public void changePanels(Panels previousPanel, Panels nextPanel)
	{
		switch (nextPanel)
		{
			case NIL:
				dispose();
				break;
			case LOGIN:
				sidebarPresent = false;
				showLoginPanel();
			case MAIN:
				showMainPanel();
				break;
			case SETTINGS:
				break;
			default:
				return;
		}
		
		this.previousPanel = previousPanel;
	}
	
	public void goBack()
	{
		
	}
	
	public Dimension getMainPanelSize()
	{
		int width = getWidth() - getSidebarPanelSize().width;
		return new Dimension(width, getHeight());
	}
	
	public Dimension getSidebarPanelSize(){
		int width = (int) (getWidth() * sidebarRatio);
		return new Dimension(width, getHeight());
	}
	
	public void setSentinel(Sentinel sentinel)
	{
		this.sentinel = sentinel;
	}
	
	enum Panels
	{
		NIL, LOGIN, MAIN, SETTINGS
	}
}
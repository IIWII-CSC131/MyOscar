package com.iiwii.myoscar.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SidebarPanel extends JPanel
{
	
	private final MainWindow frame;
	
	private final Font DEFAULT_FONT  = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
	private final Font SELECTED_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 16);
	
	private JLabel mainPanelLabel;
	private JLabel myAccountLabel;
	private JLabel settingsLabel;
	private JLabel myMoviesLabel;
	private JLabel myNominationsLabel;
	private JLabel myVotesLabel;
	private JLabel apiLabel;
	
	public SidebarPanel(MainWindow frame)
	{
		this.frame = frame;
		init();
	}
	
	private void init()
	{
		SpringLayout lay = new SpringLayout();
		setLayout(lay);
		
		// Initialize the labels
		mainPanelLabel = new JLabel("Main Menu");
		myAccountLabel = new JLabel("My Account");
		settingsLabel = new JLabel("Settings");
		myMoviesLabel = new JLabel("My Movies");
		myNominationsLabel = new JLabel("My Nominations");
		myVotesLabel = new JLabel("My Votes");
		apiLabel = new JLabel("API");
		
		// Add the properties to each label
		mainPanelLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lay.putConstraint(SpringLayout.NORTH, mainPanelLabel, 10, SpringLayout.NORTH, this);
		lay.putConstraint(SpringLayout.WEST, mainPanelLabel, 20, SpringLayout.WEST, this);
		mainPanelLabel.addMouseListener(new MouseAdapter()
		{
			@Override public void mouseClicked(MouseEvent e)
			{
				super.mouseClicked(e);
				mainPanelLabelAction();
			}
		});
		
		myAccountLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lay.putConstraint(SpringLayout.NORTH, myAccountLabel, 10, SpringLayout.SOUTH, mainPanelLabel);
		lay.putConstraint(SpringLayout.WEST, myAccountLabel, 0, SpringLayout.WEST, mainPanelLabel);
		myAccountLabel.addMouseListener(new MouseAdapter()
		{
			@Override public void mouseClicked(MouseEvent e)
			{
				super.mouseClicked(e);
				myAccountLabelAction();
			}
		});
		
		settingsLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lay.putConstraint(SpringLayout.NORTH, settingsLabel, 10, SpringLayout.SOUTH, myAccountLabel);
		lay.putConstraint(SpringLayout.WEST, settingsLabel, 0, SpringLayout.WEST, mainPanelLabel);
		settingsLabel.addMouseListener(new MouseAdapter()
		{
			@Override public void mouseClicked(MouseEvent e)
			{
				super.mouseClicked(e);
				settingsLabelAction();
			}
		});
		
		myMoviesLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lay.putConstraint(SpringLayout.NORTH, myMoviesLabel, 10, SpringLayout.SOUTH, settingsLabel);
		lay.putConstraint(SpringLayout.WEST, myMoviesLabel, 0, SpringLayout.WEST, mainPanelLabel);
		myMoviesLabel.addMouseListener(new MouseAdapter()
		{
			@Override public void mouseClicked(MouseEvent e)
			{
				super.mouseClicked(e);
				myMoviesLabelAction();
			}
		});
		
		myNominationsLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lay.putConstraint(SpringLayout.NORTH, myNominationsLabel, 10, SpringLayout.SOUTH, myMoviesLabel);
		lay.putConstraint(SpringLayout.WEST, myNominationsLabel, 0, SpringLayout.WEST, mainPanelLabel);
		myNominationsLabel.addMouseListener(new MouseAdapter()
		{
			@Override public void mouseClicked(MouseEvent e)
			{
				super.mouseClicked(e);
				myNominationsLabelAction();
			}
		});
		
		myVotesLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lay.putConstraint(SpringLayout.NORTH, myVotesLabel, 10, SpringLayout.SOUTH, myNominationsLabel);
		lay.putConstraint(SpringLayout.WEST, myVotesLabel, 0, SpringLayout.WEST, mainPanelLabel);
		myVotesLabel.addMouseListener(new MouseAdapter()
		{
			@Override public void mouseClicked(MouseEvent e)
			{
				super.mouseClicked(e);
				voteLabelAction();
			}
		});
		
		apiLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lay.putConstraint(SpringLayout.NORTH, apiLabel, 10, SpringLayout.SOUTH, myVotesLabel);
		lay.putConstraint(SpringLayout.WEST, apiLabel, 0, SpringLayout.WEST, mainPanelLabel);
		apiLabel.addMouseListener(new MouseAdapter()
		{
			@Override public void mouseClicked(MouseEvent e)
			{
				super.mouseClicked(e);
				apiLabelAction();
			}
		});
		
		resetLabels();
		mainPanelLabel.setFont(SELECTED_FONT);
		
		add(mainPanelLabel);
		add(myAccountLabel);
		add(settingsLabel);
		add(myMoviesLabel);
		add(myNominationsLabel);
		add(myVotesLabel);
		add(apiLabel);
	}
	
	private void resetLabels()
	{
		mainPanelLabel.setFont(DEFAULT_FONT);
		myAccountLabel.setFont(DEFAULT_FONT);
		settingsLabel.setFont(DEFAULT_FONT);
		myMoviesLabel.setFont(DEFAULT_FONT);
		myNominationsLabel.setFont(DEFAULT_FONT);
		myVotesLabel.setFont(DEFAULT_FONT);
		apiLabel.setFont(DEFAULT_FONT);
	}
	
	private void mainPanelLabelAction()
	{
		resetLabels();
		mainPanelLabel.setFont(SELECTED_FONT);
//		frame.changePanels(frame.getCurrentPanel(), MainWindow.Panels.MAIN);
	}
	
	private void myAccountLabelAction()
	{
		resetLabels();
		myAccountLabel.setFont(SELECTED_FONT);
//		frame.changePanels(frame.getCurrentPanel(), MainWindow.Panels.MY_ACCOUNT);
	}
	
	private void settingsLabelAction()
	{
		resetLabels();
		settingsLabel.setFont(SELECTED_FONT);
//		frame.changePanels(frame.getCurrentPanel(), MainWindow.Panels.SETTINGS);
	}
	
	private void myMoviesLabelAction()
	{
		resetLabels();
		myMoviesLabel.setFont(SELECTED_FONT);
	}
	
	private void myNominationsLabelAction()
	{
		resetLabels();
		myNominationsLabel.setFont(SELECTED_FONT);
	}
	
	private void voteLabelAction()
	{
		resetLabels();
		myVotesLabel.setFont(SELECTED_FONT);
	}
	
	private void apiLabelAction()
	{
		resetLabels();
		apiLabel.setFont(SELECTED_FONT);
	}
}

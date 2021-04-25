package com.iiwii.myoscar.gui;

import com.iiwii.myoscar.movie_data.Movie;
import com.iiwii.myoscar.movie_data.OmdbSearch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class MainPanel extends JPanel
{
	private final Font DEFAULT_FONT   = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
	private final Font SEARCHBAR_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 13);
	
	private MainWindow frame;
	
	private HintTextField moviesSearchBar;
	private HintTextField oscarsSearchBar;
	
	public MainPanel(MainWindow frame)
	{
		this.frame = frame;
		initPanel();
	}
	
	private void initPanel()
	{
		SpringLayout lay = new SpringLayout();
		setLayout(lay);
		
		// Search buttons
		JButton moviesSearchButton = new JButton("Search");
		moviesSearchButton.setFont(DEFAULT_FONT);
		moviesSearchButton.addActionListener(this::moviesSearchButtonAction);
		lay.putConstraint(SpringLayout.EAST, moviesSearchButton, -100, SpringLayout.EAST, this);
		lay.putConstraint(SpringLayout.NORTH, moviesSearchButton, 50, SpringLayout.NORTH, this);
		add(moviesSearchButton);
		
		JButton oscarsSearchButton = new JButton("Search");
		oscarsSearchButton.setFont(DEFAULT_FONT);
		oscarsSearchButton.addActionListener(this::oscarsSearchButtonAction);
		lay.putConstraint(SpringLayout.EAST, oscarsSearchButton, 0, SpringLayout.EAST, moviesSearchButton);
		lay.putConstraint(SpringLayout.NORTH, oscarsSearchButton, 50, SpringLayout.SOUTH, moviesSearchButton);
		add(oscarsSearchButton);
		
		
		// Search bars
		moviesSearchBar = new HintTextField("Search for a movie", SEARCHBAR_FONT);
		moviesSearchBar.setFont(SEARCHBAR_FONT);
		lay.putConstraint(SpringLayout.WEST, moviesSearchBar, 100, SpringLayout.WEST, this);
		lay.putConstraint(SpringLayout.VERTICAL_CENTER, moviesSearchBar, 0, SpringLayout.VERTICAL_CENTER,
		                  moviesSearchButton);
		lay.putConstraint(SpringLayout.EAST, moviesSearchBar, -30, SpringLayout.WEST, moviesSearchButton);
		add(moviesSearchBar);
		
		
		oscarsSearchBar = new HintTextField("Search for Oscar nominations", SEARCHBAR_FONT);
		oscarsSearchBar.setFont(SEARCHBAR_FONT);
		lay.putConstraint(SpringLayout.WEST, oscarsSearchBar, 0, SpringLayout.WEST, moviesSearchBar);
		lay.putConstraint(SpringLayout.VERTICAL_CENTER, oscarsSearchBar, 0, SpringLayout.VERTICAL_CENTER,
		                  oscarsSearchButton);
		lay.putConstraint(SpringLayout.EAST, oscarsSearchBar, 0, SpringLayout.EAST, moviesSearchBar);
		add(oscarsSearchBar);
		
	}
	
	private void moviesSearchButtonAction(ActionEvent actionEvent)
	{
		String text = moviesSearchBar.getText();
		Movie movie = OmdbSearch.getMovie(text);
		ArrayList<Movie> movies;
		
		if (movie == null)
		{
			movies = OmdbSearch.searchMovies(text);
			
			if (movies == null || movies.size() == 0)
			{
				return;
			}
			
			//TODO: Display the results
		}
		else
		{
			//TODO: Display the result
		}
	}
	
	private void oscarsSearchButtonAction(ActionEvent actionEvent)
	{
		String text = oscarsSearchBar.getText();
	}
}

/**
 * <p>Custom class to create a JTextArea that shows a hint.</p>
 */
class HintTextField extends JTextField
{
	private final String HINT;
	private final Font   FONT;
	
	public HintTextField(String hint, Font font)
	{
		HINT = hint;
		FONT = font;
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		if (getText().length() == 0)
		{
			int h = getHeight();
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
			                                  RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			Insets ins = getInsets();
			FontMetrics fm = g.getFontMetrics();
			int c0 = getBackground().getRGB();
			int c1 = getForeground().getRGB();
			int m = 0xfefefefe;
			int c2 = ((c0 & m) >>> 1) + ((c1 & m) >>> 1);
			g.setColor(new Color(c2, true));
			g.setFont(FONT);
			g.drawString(HINT, ins.left, h / 2 + fm.getAscent() / 2 - 2);
		}
	}
}
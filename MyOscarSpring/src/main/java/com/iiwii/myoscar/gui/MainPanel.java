package com.iiwii.myoscar.gui;

import com.iiwii.myoscar.movie_data.Movie;
import com.iiwii.myoscar.movie_data.OmdbSearch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Map;

public class MainPanel extends JPanel
{
	private final Font DEFAULT_FONT   = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
	private final Font SEARCHBAR_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 13);
	
	private MainWindow frame;
	
	private JPanel        view;
	private JScrollPane   resultViewer;
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
		lay.putConstraint(SpringLayout.NORTH, moviesSearchButton, 30, SpringLayout.NORTH, this);
		add(moviesSearchButton);
		
		JButton oscarsSearchButton = new JButton("Search");
		oscarsSearchButton.setFont(DEFAULT_FONT);
		oscarsSearchButton.addActionListener(this::oscarsSearchButtonAction);
		lay.putConstraint(SpringLayout.EAST, oscarsSearchButton, 0, SpringLayout.EAST, moviesSearchButton);
		lay.putConstraint(SpringLayout.NORTH, oscarsSearchButton, 20, SpringLayout.SOUTH, moviesSearchButton);
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
		
		view = new JPanel();
		resultViewer = new JScrollPane(view);
		resultViewer.setBorder(null);
		lay.putConstraint(SpringLayout.WEST, resultViewer, -75, SpringLayout.WEST, oscarsSearchBar);
		lay.putConstraint(SpringLayout.EAST, resultViewer, 75, SpringLayout.EAST, oscarsSearchButton);
		lay.putConstraint(SpringLayout.NORTH, resultViewer, 20, SpringLayout.SOUTH, oscarsSearchBar);
		lay.putConstraint(SpringLayout.SOUTH, resultViewer, -20, SpringLayout.SOUTH, this);
		add(resultViewer);
	}
	
	private void moviesSearchButtonAction(ActionEvent actionEvent)
	{
		String text = moviesSearchBar.getText();
		ArrayList<Movie> movies = OmdbSearch.searchMovies(text);
		
		if (movies == null || movies.size() == 0)
		{
			return;
		}
		
		displayMovieResults(movies);
	}
	
	private void oscarsSearchButtonAction(ActionEvent actionEvent)
	{
		String text = oscarsSearchBar.getText();
	}
	
	private void displayMovieResults(ArrayList<Movie> movies)
	{
		view.removeAll();
		view.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		
		JButton viewMovieButton;
		JLabel movieInfo;
		
		for (int i = 0; i < movies.size(); i++)
		{
			Movie movie = movies.get(i);
			String info = "<html>" + movie.getTitle() + "<br/>" +
			              movie.getYear() + ", " + movie.getType() + ", ID: " + movie.getIMDB_ID();
			movieInfo = new JLabel(info);
			movieInfo.setFont(DEFAULT_FONT);
			viewMovieButton = new JButton("View movie info");
			viewMovieButton.setActionCommand(movie.getIMDB_ID());  // Sets the action command to the IMDB ID to 
			// allow us to know exactly what movie the user wanted
			viewMovieButton.addActionListener(this::viewMovieButtonAction);
			
			gbc.gridx = 0;
			gbc.gridy = i;
			view.add(movieInfo, gbc);
			
			gbc.gridx = 1;
			view.add(viewMovieButton, gbc);
		}
		view.revalidate();
		repaint();
		revalidate();
	}
	
	private void viewMovieButtonAction(ActionEvent actionEvent)
	{
		Movie movie = OmdbSearch.getMovieById(actionEvent.getActionCommand());
		
		if (movie == null)
		{
			return;
		}
		
		displayMovieInfo(movie);
	}
	
	private void displayMovieInfo(Movie movie)
	{
		view.removeAll();
		SpringLayout lay = new SpringLayout();
		view.setLayout(lay);
		
		// Init all variables
		JLabel title = new JLabel(movie.getTitle());
		JLabel genre = new JLabel("Genre:" + movie.getGenre());
		JLabel director = new JLabel("Director:" + movie.getDirector());
		JLabel writer = new JLabel(movie.getWriter());
		JLabel actors = new JLabel(movie.getActors());
		JLabel plot = new JLabel(movie.getPlot());
		JLabel language = new JLabel(movie.getLanguage());
		JLabel country = new JLabel(movie.getCountry());
		JLabel awards = new JLabel(movie.getAwards());
		JLabel year = new JLabel(String.valueOf(movie.getYear()));
		JLabel released = new JLabel(movie.getReleased());
		JLabel imdbVotes = new JLabel(String.valueOf(movie.getIMDB_VOTES()));
		JLabel rated = new JLabel(movie.getRated());
		JLabel runtime = new JLabel(movie.getRuntime());
		JLabel metaScore = new JLabel(String.valueOf(movie.getMetaScore()));
		JLabel imdbRating = new JLabel(String.valueOf(movie.getIMDB_RATING()));
		JLabel imdbId = new JLabel(movie.getIMDB_ID());
		JLabel type = new JLabel(movie.getType());
		String posterLink = movie.getPosterLink();
		Map<String, String> ratings = movie.getRatings();
		
		title.setFont(new Font("Calibri", DEFAULT_FONT.getStyle(), DEFAULT_FONT.getSize() + 8));
		genre.setFont(DEFAULT_FONT);
		director.setFont(DEFAULT_FONT);
		writer.setFont(DEFAULT_FONT);
		actors.setFont(DEFAULT_FONT);
		plot.setFont(DEFAULT_FONT);
		language.setFont(DEFAULT_FONT);
		country.setFont(DEFAULT_FONT);
		awards.setFont(DEFAULT_FONT);
		year.setFont(DEFAULT_FONT);
		released.setFont(DEFAULT_FONT);
		imdbVotes.setFont(DEFAULT_FONT);
		rated.setFont(DEFAULT_FONT);
		runtime.setFont(DEFAULT_FONT);
		metaScore.setFont(DEFAULT_FONT);
		imdbRating.setFont(DEFAULT_FONT);
		imdbId.setFont(DEFAULT_FONT);
		type.setFont(DEFAULT_FONT);
		
		lay.putConstraint(SpringLayout.HORIZONTAL_CENTER, title, 0, SpringLayout.HORIZONTAL_CENTER, view);
		lay.putConstraint(SpringLayout.NORTH, title, 20, SpringLayout.NORTH, view);
		view.add(title);
		
		view.revalidate();
		repaint();
		revalidate();
	}
}

/**
 * <p>Custom class to create a JTextArea that shows a hint.</p>
 * <p>Copied from StackOverflow: </p>
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
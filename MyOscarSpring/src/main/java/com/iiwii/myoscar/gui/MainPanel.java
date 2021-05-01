package com.iiwii.myoscar.gui;

import com.iiwii.myoscar.movie_data.Movie;
import com.iiwii.myoscar.movie_data.OmdbSearch;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;

public class MainPanel extends JPanel
{
	private final Font DEFAULT_FONT   = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
	private final Font SEARCHBAR_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 13);
	private final Font VIEW_FONT      = new Font(Font.SANS_SERIF, Font.PLAIN, 13);
	
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
		lay.putConstraint(SpringLayout.NORTH, moviesSearchButton, 10, SpringLayout.NORTH, this);
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
		
		view = new JPanel();
		resultViewer = new JScrollPane(view);
		resultViewer.setBorder(null);
		lay.putConstraint(SpringLayout.WEST, resultViewer, -75, SpringLayout.WEST, oscarsSearchBar);
		lay.putConstraint(SpringLayout.EAST, resultViewer, 75, SpringLayout.EAST, oscarsSearchButton);
		lay.putConstraint(SpringLayout.NORTH, resultViewer, 20, SpringLayout.SOUTH, oscarsSearchBar);
		lay.putConstraint(SpringLayout.SOUTH, resultViewer, -20, SpringLayout.SOUTH, this);
		add(resultViewer);
	}
	
	private void oscarsSearchButtonAction(ActionEvent actionEvent)
	{
		String text = oscarsSearchBar.getText();
	}
	
	private void displayOscarResults()
	{
		
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
		
		// init vars
		String title = movie.getTitle();
		String genre = "Genres: " + movie.getGenre();
		String director = "Directors: " + movie.getDirector();
		String writer = "Writers: " + movie.getWriter();
		String actors = "Actors: " + movie.getActors();
		String plot = "Plot: " + movie.getPlot();
		String language = "Language: " + movie.getLanguage();
		String country = "Country: " + movie.getCountry();
		String awards = "Awards: " + movie.getAwards();
		String year = "Year: " + String.valueOf(movie.getYear());
		String released = "Released: " + movie.getReleased();
		String imdbVotes = "IMDB Votes: " + String.valueOf(movie.getIMDB_VOTES());
		String rated = "Rated: " + movie.getRated();
		String runtime = "Runtime: " + movie.getRuntime();
		String metaScore = "MetaScore: " + String.valueOf(movie.getMetaScore());
		String imdbRating = "IMDB Rating: " + String.valueOf(movie.getIMDB_RATING());
		String imdbId = "IMDB ID: " + movie.getIMDB_ID();
		String type = "Type: " + movie.getType();
		String posterLink = movie.getPosterLink();
		ArrayList<String[]> ratings = movie.getRatings();
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(VIEW_FONT);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBackground(this.getBackground());
		
		String n = "\n";
		StringBuilder movieInfoText = new StringBuilder(year + n + released + n + type + n + country + n + genre + n +
		                                                actors + n + director + n + writer + n + awards + n + language +
		                                                n + rated + n + imdbVotes + n + imdbRating + n + metaScore + n +
		                                                imdbId + n + runtime + n + plot);
		for (String[] rating : ratings)
		{
			movieInfoText.append(n).append(rating[0]).append(": ").append(rating[1]);
		}
		
		textArea.setText(movieInfoText.toString());
		
		JLabel titleLabel = new JLabel(title);
		titleLabel.setFont(new Font(DEFAULT_FONT.getName(), DEFAULT_FONT.getStyle(), DEFAULT_FONT.getSize() + 6));
		lay.putConstraint(SpringLayout.NORTH, titleLabel, 5, SpringLayout.NORTH, view);
		lay.putConstraint(SpringLayout.HORIZONTAL_CENTER, titleLabel, 0, SpringLayout.HORIZONTAL_CENTER, view);
		view.add(titleLabel);
		
		JScrollPane movieInfoScrollPane = new JScrollPane(textArea);
		movieInfoScrollPane.setBorder(null);
		lay.putConstraint(SpringLayout.WEST, movieInfoScrollPane, 0, SpringLayout.WEST, view);
		lay.putConstraint(SpringLayout.EAST, movieInfoScrollPane, -300, SpringLayout.EAST, view);
		lay.putConstraint(SpringLayout.NORTH, movieInfoScrollPane, 15, SpringLayout.SOUTH, titleLabel);
		lay.putConstraint(SpringLayout.SOUTH, movieInfoScrollPane, 0, SpringLayout.SOUTH, view);
		view.add(movieInfoScrollPane);
		
		// Try to put the poster image in the view
		try
		{
			Image posterImage = ImageIO.read(new URL(posterLink));
			int newX;
			int newY;
			double maxX = 290.0;
			
			// Scale the poster to fit well next to the move info
			double mu = maxX / posterImage.getWidth(null);
			double theta = posterImage.getHeight(null) * mu;
			if (theta > movieInfoScrollPane.getPreferredSize().height)
			{
				mu = (1.0 * movieInfoScrollPane.getPreferredSize().height) / posterImage.getHeight(null);
				newY = movieInfoScrollPane.getPreferredSize().height;
				newX = (int) (posterImage.getWidth(null) * mu);
			}
			else
			{
				newX = (int) maxX;
				newY = (int) theta;
			}
			posterImage = posterImage.getScaledInstance(newX, newY, Image.SCALE_SMOOTH);
			
			JLabel poster = new JLabel(new ImageIcon(posterImage));
			lay.putConstraint(SpringLayout.VERTICAL_CENTER, poster, 0, SpringLayout.VERTICAL_CENTER,
			                  movieInfoScrollPane);
			lay.putConstraint(SpringLayout.EAST, poster, -10, SpringLayout.EAST, view);
			view.add(poster);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		view.revalidate();
		repaint();
		revalidate();
	}
}

/**
 * <p>Custom class to create a JTextArea that shows a hint.</p>
 * <p>Copied from <a href="https://stackoverflow.com/a/24571681">this</a> answer on StackOverflow.</p>
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
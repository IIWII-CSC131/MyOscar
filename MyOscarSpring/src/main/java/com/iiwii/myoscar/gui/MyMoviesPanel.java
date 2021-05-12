package com.iiwii.myoscar.gui;

import com.iiwii.myoscar.movie_data.Movie;
import com.iiwii.myoscar.movie_data.OmdbSearch;
import com.iiwii.myoscar.uac.UserAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * <p></p>
 *
 * @author Eric Rodriguez
 * @version 1.0
 */
public class MyMoviesPanel extends JPanel
{
	private final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
	private final Font ERROR_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 30);
	
	private final MainWindow  frame;
	private final UserAccount uac;
	
	public MyMoviesPanel(MainWindow frame)
	{
		this.frame = frame;
		uac = frame.getUserAccount();
		initPanel();
	}
	
	private void initPanel()
	{
		if (uac == null)
		{
			displayError("No account logged in");
		}
		else 
		{
			showMovies();
		}
	}
	
	private void showMovies()
	{
		ArrayList<String> movieIds = uac.getMovies();
		ArrayList<Movie> movies = new ArrayList<>();
		for (String id : movieIds)
		{
			Movie movie = OmdbSearch.getMovieById(id);
			if (movie != null)
			{
				movies.add(movie);
			}
		}
		
		if (movies.isEmpty())
		{
			displayError("No movies added");
		}
		
		JPanel view = new JPanel();
		JScrollPane scroller = new JScrollPane(view);
		JButton deleteMovieButton;
		JLabel movieInfo;
		
		view.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		
		for (int i = 0; i < movies.size(); i++)
		{
			Movie movie = movies.get(i);
			String info = "<html>" + movie.getTitle() + "<br/>" +
			              movie.getYear() + ", " + movie.getType() + ", ID: " + movie.getIMDB_ID();
			movieInfo = new JLabel(info);
			movieInfo.setFont(DEFAULT_FONT);
			deleteMovieButton = new JButton("Delete movie");
			deleteMovieButton.setActionCommand(movie.getIMDB_ID());  // Sets the action command to the IMDB ID to 
			// allow us to know exactly what movie the user selected
			deleteMovieButton.addActionListener(this::deleteMovieAction);
			
			gbc.gridx = 0;
			gbc.gridy = i;
			view.add(movieInfo, gbc);
			
			gbc.gridx = 1;
			view.add(deleteMovieButton, gbc);
		}
		
		SpringLayout lay = new SpringLayout();
		setLayout(lay);
		lay.putConstraint(SpringLayout.NORTH, scroller, 10, SpringLayout.NORTH, this);
		lay.putConstraint(SpringLayout.EAST, scroller, -10, SpringLayout.EAST, this);
		lay.putConstraint(SpringLayout.SOUTH, scroller, -10, SpringLayout.SOUTH, this);
		lay.putConstraint(SpringLayout.WEST, scroller, 10, SpringLayout.WEST, this);
		add(scroller);
		
		view.revalidate();
		repaint();
		revalidate();
	}
	
	private void deleteMovieAction(ActionEvent actionEvent)
	{
		ArrayList<String> movieIds = uac.getMovies();
		for (int i = 0; i < movieIds.size(); i++)
		{
			if (movieIds.get(i).equals(actionEvent.getActionCommand()))
			{
				movieIds.remove(i);
				
				if (movieIds.isEmpty())
				{
					uac.clearMovies();
				}
				else
				{
					uac.writeMovies(movieIds);
				}
				
				break;
			}
		}
		
		removeAll();
		showMovies();
	}
	
	private void displayError(String msg)
	{
		SpringLayout lay = new SpringLayout();
		setLayout(lay);
		
		JLabel errorLabel = new JLabel(msg);
		errorLabel.setFont(ERROR_FONT);
		lay.putConstraint(SpringLayout.VERTICAL_CENTER, errorLabel, 0, SpringLayout.VERTICAL_CENTER, this);
		lay.putConstraint(SpringLayout.HORIZONTAL_CENTER, errorLabel, 0, SpringLayout.HORIZONTAL_CENTER, this);
		add(errorLabel);
		
		repaint();
	}
	
	
}

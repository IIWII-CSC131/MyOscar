package com.iiwii.myoscar.movie_data;

import java.util.ArrayList;
import java.util.Map;

/*
 * File: movieData.java, Movies.java
 * Author: John Vardanyan
 * Date: March 30, 2021
 *
 * Description: A class to create Movies object.
 * Edited by: Eric Rodriguez
 *      Added getters/setters and tweaked some variables to play well with OMDB
 */
public class Movie
{
	private String title, genre, director, writer, actors, plot, language, country, awards, released, rated, runtime,
			IMDB_ID, type, posterLink;
	private int year, IMDB_VOTES, metaScore;
	private double IMDB_RATING;
	
	private ArrayList<String[]> ratings;
	
	public Movie()
	{
		title = "";
		genre = "";
		director = "";
		writer = "";
		actors = "";
		plot = "";
		language = "";
		country = "";
		awards = "";
		year = 0;
		released = "";
		IMDB_VOTES = 0;
		ratings = null;
		rated = "";
		runtime = "";
		metaScore = 0;
		IMDB_RATING = 0.0;
		IMDB_ID = "";
		type = "";
		posterLink = "";
	}
	
	public Movie(String title, String genre, String director, String writer, String actors,
	             String plot, String language, String country, String awards, int year,
	             String released, int IMDB_VOTES, ArrayList<String[]> ratings, String IMDB_ID, String rated,
	             String runtime, int metaScore, double IMDB_RATING, String type, String posterLink)
	{
		this.title = title;
		this.genre = genre;
		this.director = director;
		this.writer = writer;
		this.actors = actors;
		this.plot = plot;
		this.language = language;
		this.country = country;
		this.awards = awards;
		this.year = year;
		this.released = released;
		this.IMDB_VOTES = IMDB_VOTES;
		this.ratings = ratings;
		this.IMDB_ID = IMDB_ID;
		this.rated = rated;
		this.runtime = runtime;
		this.metaScore = metaScore;
		this.IMDB_RATING = IMDB_RATING;
		this.type = type;
		this.posterLink = posterLink;
	}
	
	public String getRated()
	{
		return rated;
	}
	
	public void setRated(String rated)
	{
		this.rated = rated;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getGenre()
	{
		return genre;
	}
	
	public void setGenre(String genre)
	{
		this.genre = genre;
	}
	
	public String getDirector()
	{
		return director;
	}
	
	public void setDirector(String director)
	{
		this.director = director;
	}
	
	public String getWriter()
	{
		return writer;
	}
	
	public void setWriter(String writer)
	{
		this.writer = writer;
	}
	
	public String getActors()
	{
		return actors;
	}
	
	public void setActors(String actors)
	{
		this.actors = actors;
	}
	
	public String getPlot()
	{
		return plot;
	}
	
	public void setPlot(String plot)
	{
		this.plot = plot;
	}
	
	public String getLanguage()
	{
		return language;
	}
	
	public void setLanguage(String language)
	{
		this.language = language;
	}
	
	public String getCountry()
	{
		return country;
	}
	
	public void setCountry(String country)
	{
		this.country = country;
	}
	
	public String getAwards()
	{
		return awards;
	}
	
	public void setAwards(String awards)
	{
		this.awards = awards;
	}
	
	public int getYear()
	{
		return year;
	}
	
	public void setYear(int year)
	{
		this.year = year;
	}
	
	public String getReleased()
	{
		return released;
	}
	
	public void setReleased(String released)
	{
		this.released = released;
	}
	
	public int getIMDB_VOTES()
	{
		return IMDB_VOTES;
	}
	
	public void setIMDB_VOTES(int IMDB_VOTES)
	{
		this.IMDB_VOTES = IMDB_VOTES;
	}
	
	public ArrayList<String[]> getRatings()
	{
		return ratings;
	}
	
	public void setRatings(ArrayList<String[]> ratings)
	{
		this.ratings = ratings;
	}
	
	public double getIMDB_RATING()
	{
		return IMDB_RATING;
	}
	
	public void setIMDB_RATING(double IMDB_RATING)
	{
		this.IMDB_RATING = IMDB_RATING;
	}
	
	public String getRuntime()
	{
		return runtime;
	}
	
	public void setRuntime(String runtime)
	{
		this.runtime = runtime;
	}
	
	public int getMetaScore()
	{
		return metaScore;
	}
	
	public void setMetaScore(int metaScore)
	{
		this.metaScore = metaScore;
	}
	
	public String getIMDB_ID()
	{
		return IMDB_ID;
	}
	
	public void setIMDB_ID(String IMDB_ID)
	{
		this.IMDB_ID = IMDB_ID;
	}
	
	public String getType()
	{
		return type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public String getPosterLink()
	{
		return posterLink;
	}
	
	public void setPosterLink(String posterLink)
	{
		this.posterLink = posterLink;
	}
}

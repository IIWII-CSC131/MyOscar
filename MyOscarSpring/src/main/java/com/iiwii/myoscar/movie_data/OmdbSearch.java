package com.iiwii.myoscar.movie_data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

/**
 * <p>Gets a movies data using the OMDB API. Revised to follow same coding convention as the OscarData class.</p>
 *
 * @author Eric Rodriguez
 * @version 2.0
 */
public class OmdbSearch
{
	private static String title;
	private static String year;
	private static String type;
	private static String plotLength;
	
	/**
	 * <p>Get a movie from OMDB by using the title of the movie.</p>
	 *
	 * @param title The title of the movie
	 * @return A movie object
	 */
	public static Movie getMovie(String title)
	{
		return getMovie(title, 0, null, null);
	}
	
	/**
	 * <p>Get a movie from OMDB by using the title and year of the movie.</p>
	 *
	 * @param title The title of the movie
	 * @param year  The year of the movie
	 * @return A movie object
	 */
	public static Movie getMovie(String title, int year)
	{
		return getMovie(title, year, null, null);
	}
	
	/**
	 * <p>Get a movie from OMDB by using the title, year, and type of the media. Can also change the plot length.</p>
	 *
	 * @param title      The title of the movie
	 * @param year       The year of the movie
	 * @param type       Type of media
	 * @param plotLength Size of plot length
	 * @return A movie object
	 */
	public static Movie getMovie(String title, int year, String type, String plotLength)
	{
		// Title is surrounded with quotation marks to allow spaces
		String requestURL = "https://www.omdbapi.com/?apikey=e8a0955b&t=\"" + title + "\"";
		String unformattedJsonResponse = "";
		Movie movie = new Movie();
		
		if (type != null && !type.equals(""))
		{
			requestURL += "&type=" + type;
		}
		
		if (year != 0)
		{
			requestURL += "&y=" + year;
		}
		
		if (plotLength != null && !plotLength.equals(""))
		{
			requestURL += "&plot=" + plotLength;
		}
		
		try (Scanner scanner = new Scanner(new URL(requestURL).openStream(), StandardCharsets.UTF_8.toString()))
		{
			scanner.useDelimiter("\\A");
			unformattedJsonResponse = scanner.hasNext() ? scanner.next() : "";
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		JSONObject movieJson = new JSONObject(unformattedJsonResponse);
		
		// Check if the returned value is valid
		if (movieJson.isEmpty() || !movieJson.optString("Response").equals("True"))
		{
			return null;
		}
		
		movie.setTitle(movieJson.optString("Title"));
		movie.setGenre(movieJson.optString("Genre"));
		movie.setDirector(movieJson.optString("Director"));
		movie.setWriter(movieJson.optString("Writer"));
		movie.setActors(movieJson.optString("Actors"));
		movie.setPlot(movieJson.optString("Plot"));
		movie.setLanguage(movieJson.optString("Language"));
		movie.setCountry(movieJson.optString("Country"));
		movie.setAwards(movieJson.optString("Awards"));
		movie.setYear(Integer.parseInt(movieJson.optString("Year", "0")));
		movie.setReleased(movieJson.optString("Released"));
		movie.setRated(movieJson.optString("Rated"));
		movie.setRuntime(movieJson.optString("Runtime"));
		movie.setMetaScore(Integer.parseInt(movieJson.optString("Metascore", "0")));
		movie.setIMDB_RATING(Double.parseDouble(movieJson.optString("imdbRating", "0")));
		movie.setIMDB_ID(movieJson.optString("imdbID"));
		movie.setType(movieJson.optString("Type"));
		movie.setPosterLink(movieJson.optString("Poster"));
		
		// Convert the ratings to a Map
		HashMap<String, String> ratings = new HashMap<>();
		JSONArray jArray = movieJson.getJSONArray("Ratings");
		for (int i = 0; i < jArray.length(); i++)
		{
			ratings.put(jArray.getJSONObject(i).optString("Value"), jArray.getJSONObject(i).optString("Source"));
		}
		movie.setRatings(ratings);
		
		// Convert the votes to an integer
		NumberFormat format = NumberFormat.getInstance(Locale.US);
		try
		{
			movie.setIMDB_VOTES(Math.toIntExact((Long) format.parse(movieJson.optString("imdbVotes"))));
		}
		catch (ParseException e)
		{
			e.printStackTrace();
			movie.setIMDB_VOTES(-1);
		}
		
		return movie;
	}
	
	/**
	 * <p>Searches OMDB for a movie.</p>
	 *
	 * @param title Title of movie to search for
	 * @return An array list of movies
	 */
	public static ArrayList<Movie> searchMovies(String title)
	{
		String requestURL = "https://www.omdbapi.com/?apikey=e8a0955b&s=\"" + title + "\"";
		String unformattedJsonResponse = "";
		
		try (Scanner scanner = new Scanner(new URL(requestURL).openStream(), StandardCharsets.UTF_8.toString()))
		{
			scanner.useDelimiter("\\A");
			unformattedJsonResponse = scanner.hasNext() ? scanner.next() : "";
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		JSONObject searchJson = new JSONObject(unformattedJsonResponse);
		
		// Check if the returned value is valid
		if (searchJson.isEmpty() || !searchJson.optString("Response").equals("True"))
		{
			return null;
		}
		
		int results;
		try
		{
			results = Integer.parseInt(searchJson.optString("totalResults", "0"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			results = 0;
		}
		
		if (results < 1)
		{
			return null;
		}
		
		ArrayList<Movie> movies = new ArrayList<Movie>();
		JSONArray jArray = searchJson.getJSONArray("Search");
		
		for (int i = 0; i < results; i++)
		{
			JSONObject obj = jArray.getJSONObject(i);
			Movie movie = new Movie();
			movie.setTitle(obj.optString("Title"));
			movie.setYear(Integer.parseInt(obj.optString("Year", "0")));
			movie.setIMDB_ID(obj.optString("imdbID"));
			movie.setType(obj.optString("Type"));
			movie.setPosterLink(obj.optString("Poster"));
			movies.add(movie);
		}
		
		return movies;
	}
}

package com.iiwii.myoscar.movie_data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
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
	/**
	 * <p>Get a movie from OMDB by using the title of the movie.</p>
	 *
	 * @param title The title of the movie
	 * @return A movie object
	 */
	public static Movie getMovie(String title)
	{
		return getMovie(title, 0, null, null, null);
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
		return getMovie(title, year, null, null, null);
	}
	
	/**
	 * <p>Get a movie from OMDB using an IMDB ID.</p>
	 *
	 * @param id The IMDB ID
	 * @return A movie object
	 */
	public static Movie getMovieById(String id)
	{
		return getMovie(null, 0, null, null, id);
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
	public static Movie getMovie(String title, int year, String type, String plotLength, String id)
	{
		String requestURL;
		JSONObject movieJson;
		Movie movie = new Movie();
		
		if (title != null)
		{
			// Title is surrounded with quotation marks to allow spaces
			requestURL = "https://www.omdbapi.com/?apikey=e8a0955b&t=\"" + title + "\"";
			
			if (year != 0)
			{
				requestURL += "&y=" + year;
			}
			
			if (type != null && !type.equals(""))
			{
				requestURL += "&type=" + type;
			}
			
			if (plotLength != null && !plotLength.equals(""))
			{
				requestURL += "&plot=" + plotLength;
			}
		}
		else
		{
			requestURL = "https://www.omdbapi.com/?apikey=e8a0955b&i=" + id;
		}
		
		movieJson = getJsonFromUrl(requestURL);
		
		// Check if the returned value is valid
		if (movieJson == null || !movieJson.optString("Response", "False").equals("True"))
		{
			return null;
		}
		
		// optString(String) allows us to request a value even if it doesn't exist. optString will, by default, 
		// return "". 
		movie.setTitle(movieJson.optString("Title"));
		movie.setGenre(movieJson.optString("Genre"));
		movie.setDirector(movieJson.optString("Director"));
		movie.setWriter(movieJson.optString("Writer"));
		movie.setActors(movieJson.optString("Actors"));
		movie.setPlot(movieJson.optString("Plot"));
		movie.setLanguage(movieJson.optString("Language"));
		movie.setCountry(movieJson.optString("Country"));
		movie.setAwards(movieJson.optString("Awards"));
		movie.setReleased(movieJson.optString("Released"));
		movie.setRated(movieJson.optString("Rated"));
		movie.setRuntime(movieJson.optString("Runtime"));
		movie.setIMDB_ID(movieJson.optString("imdbID"));
		movie.setType(movieJson.optString("Type"));
		movie.setPosterLink(movieJson.optString("Poster"));
		
		try
		{
			movie.setYear(Integer.parseInt(movieJson.optString("Year", "-1")));
		}
		catch (Exception ignored)
		{
			movie.setYear(-1);
		}
		
		try
		{
			movie.setMetaScore(Integer.parseInt(movieJson.optString("Metascore", "-1")));
		}
		catch (Exception ignored)
		{
			movie.setMetaScore(-1);
		}
		
		try
		{
			movie.setIMDB_RATING(Double.parseDouble(movieJson.optString("imdbRating", "-1")));
		}
		catch (Exception ignored)
		{
			movie.setIMDB_RATING(-1);
		}
		
		// Convert ratings into an array list of string arrays
		ArrayList<String[]> ratings = new ArrayList<>();
		JSONArray jArray = movieJson.getJSONArray("Ratings");
		for (int i = 0; i < jArray.length(); i++)
		{
			ratings.add(new String[]{jArray.getJSONObject(i).optString("Source"),
					jArray.getJSONObject(i).optString("Value")});
		}
		movie.setRatings(ratings);
		
		// Convert the votes to an integer (votes use the comma notation, we need to remove that)
		NumberFormat format = NumberFormat.getInstance(Locale.US);
		try
		{
			movie.setIMDB_VOTES(Math.toIntExact((Long) format.parse(movieJson.optString("imdbVotes", "-1"))));
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
	 * @return An array list of matching movies
	 */
	public static ArrayList<Movie> searchMovies(String title)
	{
		JSONObject searchJson = getJsonFromUrl("https://www.omdbapi.com/?apikey=e8a0955b&s=\"" + title + "\"");
		
		// Check if the returned value is valid
		if (searchJson == null || !searchJson.optString("Response", "False").equals("True"))
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
		
		ArrayList<Movie> movies = new ArrayList<>();
		JSONArray jArray = searchJson.getJSONArray("Search");
		
		// The response will only show a max of 10 movies, so we need to make sure 10 iterations is the max we will run
		for (int i = 0; i < Math.min(10, results); i++)
		{
			JSONObject obj = jArray.getJSONObject(i);
			Movie movie = new Movie();
			movie.setTitle(obj.optString("Title"));
			movie.setIMDB_ID(obj.optString("imdbID"));
			movie.setType(obj.optString("Type"));
			movie.setPosterLink(obj.optString("Poster"));
			
			try
			{
				movie.setYear(Integer.parseInt(obj.optString("Year", "-1")));
			}
			catch (Exception ignored)
			{
				movie.setYear(-1);
			}
			
			movies.add(movie);
		}
		
		return movies;
	}
	
	/**
	 * <p>Gets a JSON object from the passed URL</p>
	 *
	 * @param url URL to get JSON object from
	 * @return A JSONObject
	 */
	private static JSONObject getJsonFromUrl(String url)
	{
		String unformattedJsonResponse;
		
		try (Scanner scanner = new Scanner(new URL(url).openStream(), StandardCharsets.UTF_8.toString()))
		{
			scanner.useDelimiter("\\A");
			unformattedJsonResponse = scanner.hasNext() ? scanner.next() : "";
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		return new JSONObject(unformattedJsonResponse);
	}
}

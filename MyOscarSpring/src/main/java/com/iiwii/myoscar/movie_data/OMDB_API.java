package com.iiwii.myoscar.movie_data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

/**
 * <p>Gets a movies data using the OMDB API</p>
 *
 * @author Eric Rodriguez
 * @version 1.0
 */
public class OMDB_API
{
	private Movie  movie;
	private String title;
	private String type;
	private String year;
	private String plotLength;
	
	/**
	 * <p>Create a new OMDB API request. The title is the only thing required.</p>
	 *
	 * @param titleRequest Title of the movie
	 */
	public OMDB_API(String titleRequest)
	{
		this.title = titleRequest;
	}
	
	/**
	 * <p>Used to set the type of media it is. i.e. movie, series, episode</p>
	 * <p>(Optional)</p>
	 *
	 * @param type Type of media
	 */
	public void setTypeRequest(Types type)
	{
		switch (type)
		{
			case MOVIE:
				this.type = "movie";
				break;
			case SERIES:
				this.type = "series";
				break;
			case EPISODE:
				this.type = "episode";
				break;
		}
	}
	
	/**
	 * <p>Used to specify the year the media was released.</p>
	 * <p>(Optional)</p>
	 *
	 * @param year The year it was released
	 */
	public void setYearRequest(int year)
	{
		this.year = String.valueOf(year);
	}
	
	/**
	 * <p>Set a plot length if needed. Defaults to "short".</p>
	 * <p>(Optional)</p>
	 *
	 * @param plotLength Specified plot length
	 */
	public void setPlotLengthRequest(PlotLength plotLength)
	{
		if (plotLength == PlotLength.SHORT)
		{
			this.plotLength = "short";
		}
		else
		{
			this.plotLength = "full";
		}
	}
	
	/**
	 * <p>Contacts the OMDB API with the title and optional requests.</p>
	 *
	 * @return A movie object if response was good, null otherwise
	 */
	public Movie getMovieFromRequests()
	{
		String requestURL = "https://www.omdbapi.com/?apikey=e8a0955b&t=" + title;
		String unformattedJsonResponse = "";
		
		if (type != null && !type.equals(""))
		{
			requestURL += "&type=" + type;
		}
		
		if (year != null && !year.equals(""))
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
		if (movieJson.isEmpty() || movieJson.optString("Response").equals("False"))
		{
			return null;
		}
		
		System.out.println(movieJson);
		
		movie = new Movie();
		movie.setTitle(movieJson.optString("Title"));
		movie.setGenre(movieJson.optString("Genre"));
		movie.setDirector(movieJson.optString("Director"));
		movie.setWriter(movieJson.optString("Writer"));
		movie.setActors(movieJson.optString("Actors"));
		movie.setPlot(movieJson.optString("Plot"));
		movie.setLanguage(movieJson.optString("Language"));
		movie.setCountry(movieJson.optString("Country"));
		movie.setAwards(movieJson.optString("Awards"));
		movie.setYear(Integer.parseInt(movieJson.optString("Year")));
		movie.setReleased(movieJson.optString("Released"));
		movie.setRated(movieJson.optString("Rated"));
		movie.setRuntime(movieJson.optString("Runtime"));
		movie.setMetascore(Integer.parseInt(movieJson.optString("Metascore")));
		movie.setIMDB_RATING(Double.parseDouble(movieJson.optString("imdbRating")));
		movie.setIMDB_ID(movieJson.optString("imdbID"));
		movie.setType(movieJson.optString("Type"));
		
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
	
	public enum Types
	{
		MOVIE, SERIES, EPISODE
	}
	
	
	public enum PlotLength
	{
		SHORT, FULL
	}
}

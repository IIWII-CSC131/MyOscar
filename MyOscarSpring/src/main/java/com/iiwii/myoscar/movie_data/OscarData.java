package com.iiwii.myoscar.movie_data;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * <p>Accesses the KaggleData Oscar Winner JSON data.</p>
 *
 * @author Eric Rodriguez
 * @version 1.0
 */
@Component
public class OscarData
{
	/**
	 * <p>Holds the current results.</p>
	 */
	private static ArrayList<Oscar> currentList;
	
	/**
	 * <p>Start a new query filtered by year.</p>
	 *
	 * @param year Year to filter
	 */
	public static void newQueryByYear(int year)
	{
		newSearch(SearchType.YEAR, String.valueOf(year));
	}
	
	/**
	 * <p>Start a new query filtered by category. Uses {@code String.contains(category)} to allow compatibility for 
	 * slightly different named categories.</p>
	 *
	 * @param category  Category to filter
	 */
	public static void newQueryByCategory(String category)
	{
		newSearch(SearchType.CATEGORY, category);
	}
	
	/**
	 * <p>Start a new query filtered by film.</p>
	 * @param film Film to filter
	 */
	public static void newQueryByFilm(String film)
	{
		newSearch(SearchType.FILM, film);
	}
	
	/**
	 * <p>Start a new query filtered by Oscar winners.</p>
	 * @param isWinner  Filter Oscar winners or non-winners
	 */
	public static void newQueryByWinner(Boolean isWinner)
	{
		newSearch(SearchType.WINNER, isWinner.toString());
	}
	
	/**
	 * <p>Refine the current search by filtering search by year.</p>
	 * 
	 * @param year Year to filter
	 */
	public static void refineByYear(int year)
	{
		refine(SearchType.YEAR, String.valueOf(year));
	}
	
	/**
	 * <p>Refine the current search by filtering search by category. Uses {@code String.contains(category)} to allow 
	 * compatibility for slightly different named categories.</p>
	 * 
	 * @param category  Category to filter
	 */
	public static void refineByCategory(String category)
	{
		refine(SearchType.CATEGORY, category);
	}
	
	/**
	 * <p>Refine the current search by filtering search by film.</p>
	 * @param film Film to filter
	 */
	public static void refineByFilm(String film)
	{
		refine(SearchType.FILM, film);
	}
	
	/**
	 * <p>Refine the current search by filtering search by Oscar winners.</p>
	 * @param isWinner  Filter Oscar winners or non-winners
	 */
	public static void refineByWinner(Boolean isWinner)
	{
		refine(SearchType.WINNER, isWinner.toString());
	}
	
	
	private static void newSearch(SearchType type, String value)
	{
		Resource fileResource = new ClassPathResource("KaggleData_oscar_award.json");
		String unformattedJson;
		currentList = new ArrayList<>();
		try
		{
			unformattedJson = Files.readString(Paths.get(fileResource.getURI()));
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return;
		}
		
		JSONArray oscarJson = new JSONArray(unformattedJson);
		for (int i = 0; i < oscarJson.length(); i++)
		{
			String compare = "";
			JSONObject obj = oscarJson.getJSONObject(i);
			switch (type)
			{
				case YEAR:
					compare = String.valueOf(obj.optInt("year_film"));
					break;
				case CATEGORY:
					compare = obj.optString("category");
					break;
				case FILM:
					compare = obj.optString("film");
					break;
				case WINNER:
					Boolean temp = obj.optBoolean("winner");
					compare = temp.toString();
					break;
			}
			
			
			if (compare.contains(value))
			{
				currentList.add(new Oscar(obj.optString("category"), obj.optString("name"),
						obj.optString("film"), obj.optBoolean("winner"), obj.optInt("year_film"),
						obj.optInt("year_ceremony"), obj.optInt("ceremony")));
			}
		}
	}
	
	private static void refine(SearchType type, String value)
	{
		ArrayList<Oscar> transfer = new ArrayList<>();
		for (Oscar oscars : currentList)
		{
			String compare = "";
			
			switch (type)
			{
				case YEAR:
					compare = String.valueOf(oscars.getYear());
					break;
				case CATEGORY:
					compare = oscars.getCategory();
					break;
				case FILM:
					compare = oscars.getFilm();
					break;
				case WINNER:
					compare = oscars.getIsWinner().toString();
					break;
			}
			
			if (compare.contains(value))
			{
				transfer.add(oscars);
			}
		}
		
		currentList = new ArrayList<>(transfer);
	}
	
	public static ArrayList<Oscar> getResults()
	{
//		if (currentList == null)
//		{
//			return new ArrayList<>();
//		}
		
		return currentList;
	}
	
	private enum SearchType
	{
		YEAR, CATEGORY, FILM, WINNER
	}
}

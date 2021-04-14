package com.iiwii.myoscar.movie_data;

/**
 * <p>Holds info of Oscar nominations and winners. Optimized for Kaggle data.</p>
 *
 * @author Eric Rodriguez
 * @version 1.0
 */
public class Oscar
{
	private final String  category;
	private final String  name;
	private final String  film;
	private final Boolean isWinner;
	private final int     year;
	private final int     ceremonyYear;
	private final int     ceremony;
	
	public Oscar(String category, String name, String film, Boolean isWinner, int year, int ceremonyYear, int ceremony)
	{
		this.category = category;
		this.name = name;
		this.film = film;
		this.isWinner = isWinner;
		this.year = year;
		this.ceremonyYear = ceremonyYear;
		this.ceremony = ceremony;
	}
	
	public String getCategory()
	{
		return category;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getFilm()
	{
		return film;
	}
	
	public Boolean getIsWinner()
	{
		return isWinner;
	}
	
	public int getYear()
	{
		return year;
	}
	
	public int getCeremonyYear()
	{
		return ceremonyYear;
	}
	
	public int getCeremony()
	{
		return ceremony;
	}
}

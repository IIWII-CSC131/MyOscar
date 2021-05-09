package com.iiwii.myoscar.movie_data;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OmdbSearchTest
{
	
	@Test
	void getMovie1()
	{
		String movieTitleTest = "Avengers";
		Movie movie = OmdbSearch.getMovie(movieTitleTest);
		assertTrue(movie.getTitle().contains(movieTitleTest));
	}
	
	@Test
	void getMovie2()
	{
		String movieTitleTest = "TRON";
		int movieYearTest = 2010;
		Movie movie = OmdbSearch.getMovie(movieTitleTest, movieYearTest);
		assertTrue(movie.getTitle().contains(movieTitleTest) && movie.getYear() == movieYearTest);
	}
	
	@Test
	void getMovieById()
	{
		String movieImdbIdTest = "tt6723592";
		Movie movie = OmdbSearch.getMovieById(movieImdbIdTest);
		assertEquals("Tenet", movie.getTitle());
	}
	
	@Test
	void getMovie3()
	{
		String movieTitleTest = "Oblivion";
		int movieYearTest = 2013;
		String movieImdbIdTest = "tt1483013";
		String movieTypeTest = "movie";
		String moviePlotLengthTest = "full";
		Movie movie = OmdbSearch.getMovie(movieTitleTest, movieYearTest, movieTypeTest, moviePlotLengthTest, null);
		String plot =
				"One of the few remaining drone repairmen assigned to Earth, its surface devastated after decades of " +
				"war with the alien Scavs, discovers a crashed spacecraft with contents that bring into question " +
				"everything he believed about the war, and may even put the fate of mankind in his hands.";
		assert movie != null;
		assertEquals(plot, movie.getPlot());
		
		movie = OmdbSearch.getMovie(null, 0, null, moviePlotLengthTest, movieImdbIdTest);
		assert movie != null;
		assertEquals(plot, movie.getPlot());
	}
	
	@Test
	void searchMovies()
	{
		String movieTitleSearchTest = "tenet";
		ArrayList<Movie> movies = OmdbSearch.searchMovies(movieTitleSearchTest);
		assert movies != null;
		for (Movie movie : movies)
		{
			assertTrue(movie.getTitle().toLowerCase().contains(movieTitleSearchTest));
		}
	}
}
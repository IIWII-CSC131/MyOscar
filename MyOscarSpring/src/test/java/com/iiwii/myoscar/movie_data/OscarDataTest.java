package com.iiwii.myoscar.movie_data;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OscarDataTest
{
	ArrayList<Oscar> list = new ArrayList<>();
	
	@Test
	void newQueryByYear()
	{
		int yearTest = 2001;
		OscarData.newQueryByYear(yearTest);
		list = OscarData.getResults();
		
		int yearCount = 0;
		for (Oscar oscar : list)
		{
			if (oscar.getYear() == yearTest)
			{
				yearCount++;
			}
		}
		
		assertEquals(yearCount, list.size());
	}
	
	@Test
	void newQueryByYears()
	{
		int yearTestA = 2001;
		int yearTestB = 2004;
		int yearCount = 0;
		
		OscarData.newQueryByYears(yearTestA, yearTestB);
		list = OscarData.getResults();
		
		for (Oscar oscar : list)
		{
			if (oscar.getYear() >= yearTestA && oscar.getYear() <= yearTestB)
			{
				yearCount++;
			}
		}
		
		assertEquals(yearCount, list.size());
	}
	
	@Test
	void newQueryByCategory()
	{
		String testCat = "actress";
		
		OscarData.newQueryByCategory(testCat);
		list = OscarData.getResults();
		
		boolean passed = true;
		
		for (Oscar oscar : list)
		{
			if (!oscar.getCategory().toLowerCase().contains(testCat))
			{
				passed = false;
				break;
			}
		}
		
		assertTrue(passed);
	}
	
	@Test
	void newQueryByFilm()
	{
		String testFilm = "avengers";
		
		OscarData.newQueryByFilm(testFilm);
		list = OscarData.getResults();
		
		boolean passed = true;
		
		for (Oscar oscar : list)
		{
			if (!oscar.getFilm().toLowerCase().contains(testFilm))
			{
				passed = false;
				break;
			}
		}
		
		assertTrue(passed);
	}
	
	@Test
	void newQueryByWinner()
	{
		OscarData.newQueryByWinner(true);
		list = OscarData.getResults();
		
		boolean passed = true;
		
		for (Oscar oscar : list)
		{
			if (!oscar.getIsWinner())
			{
				passed = false;
				break;
			}
		}
		
		assertTrue(passed);
	}
	
	@Test
	void refineByYear()
	{
		int yearTest = 1980;
		OscarData.newQueryByYears(1960, 2015);
		OscarData.refineByYear(yearTest);
		list = OscarData.getResults();
		
		boolean passed = true;
		for (Oscar oscar : list)
		{
			if (oscar.getYear() != yearTest)
			{
				passed = false;
				break;
			}
		}
		
		assertTrue(passed);
	}
	
	@Test
	void refineByYears()
	{
		int yearTestA = 2001;
		int yearTestB = 2004;
		OscarData.newQueryByYears(1960, 2015);
		OscarData.refineByYears(yearTestA, yearTestB);
		list = OscarData.getResults();
		boolean passed = true;
		
		for (Oscar oscar : list)
		{
			if (oscar.getYear() < yearTestA || oscar.getYear() > yearTestB)
			{
				passed = false;
				break;
			}
		}
		
		assertTrue(passed);
	}
	
	@Test
	void refineByCategory()
	{
		String testCat = "actress";
		OscarData.newQueryByYears(1960, 2015);
		OscarData.refineByCategory(testCat);
		list = OscarData.getResults();
		boolean passed = true;
		
		for (Oscar oscar : list)
		{
			if (!oscar.getCategory().toLowerCase().contains(testCat))
			{
				passed = false;
				break;
			}
		}
		
		assertTrue(passed);
	}
	
	@Test
	void refineByFilm()
	{
		String testFilm = "avengers";
		OscarData.newQueryByYears(1960, 2015);
		OscarData.refineByFilm(testFilm);
		list = OscarData.getResults();
		boolean passed = true;
		
		for (Oscar oscar : list)
		{
			if (!oscar.getFilm().toLowerCase().contains(testFilm))
			{
				passed = false;
				break;
			}
		}
		
		assertTrue(passed);
	}
	
	@Test
	void refineByWinner()
	{
		OscarData.newQueryByYears(1960, 2015);
		OscarData.refineByWinner(true);
		list = OscarData.getResults();
		boolean passed = true;
		
		for (Oscar oscar : list)
		{
			if (!oscar.getIsWinner())
			{
				passed = false;
				break;
			}
		}
		
		assertTrue(passed);
	}
}
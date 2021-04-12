package com.iiwii.myoscar.controller;

import com.iiwii.myoscar.movie_data.Oscar;
import com.iiwii.myoscar.movie_data.OscarData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

/**
 * <p>Handles the mapping of the REST API.</p>
 * <p>Default URL when running on the machine is <a href="http://localhost:8080/">http://localhost:8080/</a></p>
 *
 * @author Eric Rodriguez
 * @version 1.0
 */
@RestController
public class WebController
{
	/**
	 * <p>An example method.</p>
	 * <p>Maps this methods return to /categories/bestactress/1997</p>
	 * @return ArrayList of Oscars
	 */
	@RequestMapping("/categories/bestactress/1997")
	public ArrayList<Oscar> bestActress()
	{
		OscarData.newQueryByCategory("ACTRESS");
		OscarData.refineByYear(1997);
		return OscarData.getResults();
	}
}

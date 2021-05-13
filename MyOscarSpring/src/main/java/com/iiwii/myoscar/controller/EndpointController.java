package com.iiwii.myoscar.controller;

import com.iiwii.myoscar.models.Endpoints;
import org.springframework.web.bind.annotation.*;
import com.iiwii.myoscar.movie_data.Oscar;
import com.iiwii.myoscar.movie_data.OscarData;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 *
 * @author John Vardanyan
 *
 * Rest Endpoints
 * 11 May 2021
 */

/*
 * Collection Method displays actor/actress names based on category.
 */
@RestController
public class EndpointController {
	@ResponseBody
	@GetMapping("/categories/{categoryID}")
	public Endpoints categories(@PathVariable String categoryID) {
		ArrayList<String> names = new ArrayList<String>();
		OscarData.newQueryByCategory(categoryID);
		ListIterator<Oscar> lt = OscarData.getResults().listIterator();
		while(lt.hasNext()) {
			names.add(lt.next().getName());
		}
		Endpoints col = new Endpoints(names);
		return col;
	}
	/*
	 * Collection Method displays movie names based on category.
	 */
	@ResponseBody
	@GetMapping("movies/categories/{categoryID}")
	public Endpoints movieCategories(@PathVariable String categoryID) {
		ArrayList<String> names = new ArrayList<String>();
		OscarData.newQueryByCategory(categoryID);
		ListIterator<Oscar> lt = OscarData.getResults().listIterator();
		while(lt.hasNext()) {
			names.add(lt.next().getFilm());
		}
		Endpoints col = new Endpoints(names);
		return col;
	}
	/*
	 * Collection Method displays actor/actress names based on category and year.
	 */
	@ResponseBody
	@GetMapping("/categories/{categoryID}/year/{yearID}")
	public Endpoints categories(@PathVariable("categoryID") String categoryID,
	                            @PathVariable("yearID") int yearID) {
		ArrayList<String> names = new ArrayList<String>();
		OscarData.newQueryByCategory(categoryID);
		OscarData.refineByYear(yearID);
		ListIterator<Oscar> lt = OscarData.getResults().listIterator();
		while(lt.hasNext()) {
			names.add(lt.next().getName());
		}
		Endpoints col = new Endpoints(names);
		col.setYear(yearID);
		return col;
	}
	/*
	 * Collection Method displays movie names based on category & year.
	 */
	@ResponseBody
	@GetMapping("movies/categories/{categoryID}/{yearID}")
	public Endpoints movieCategories(@PathVariable("categoryID") String categoryID,
	                                 @PathVariable("yearID") int yearID) {
		ArrayList<String> names = new ArrayList<String>();
		OscarData.newQueryByCategory(categoryID);
		OscarData.refineByYear(yearID);
		ListIterator<Oscar> lt = OscarData.getResults().listIterator();
		while(lt.hasNext()) {
			names.add(lt.next().getFilm());
		}
		Endpoints col = new Endpoints(names);
		col.setYear(yearID);
		return col;
	}
	/*
	 * Singleton Method displays actor/actress names based on category & year & is winner.
	 */
	@ResponseBody
	@GetMapping("/categories/{categoryID}/year/{yearID}/winner")
	public Endpoints winner(@PathVariable("categoryID") String categoryID,
	                        @PathVariable("yearID") int yearID){
		ArrayList<String> names = new ArrayList<String>();
		OscarData.newQueryByWinner(true);
		OscarData.refineByCategory(categoryID);
		OscarData.refineByYear(yearID);
		ListIterator<Oscar> lt = OscarData.getResults().listIterator();
		while(lt.hasNext()) {
			names.add(lt.next().getName());
		}
		Endpoints col = new Endpoints(names);
		col.setYear(yearID);
		return col;
	}
	/*
	 * Singleton Method displays movie names based on category & year & is winner.
	 */
	@ResponseBody
	@GetMapping("movies/categories/{categoryID}/{yearID}/type/winner")
	public Endpoints movieWinner(@PathVariable("categoryID") String categoryID,
	                             @PathVariable("yearID") int yearID){
		ArrayList<String> names = new ArrayList<String>();
		OscarData.newQueryByWinner(true);
		OscarData.refineByCategory(categoryID);
		OscarData.refineByYear(yearID);
		ListIterator<Oscar> lt = OscarData.getResults().listIterator();
		while(lt.hasNext()) {
			names.add(lt.next().getFilm());
		}
		Endpoints col = new Endpoints(names);
		col.setYear(yearID);
		return col;
	}
	/*
	 * Query Method displays actor/actress names based on type & year.
	 */
	@GetMapping("/search")
	public Endpoints search(@RequestParam(value = "type", defaultValue = "actor") String category,
	                        @RequestParam(value = "year", defaultValue = "1997") int year) {
		ArrayList<String> names = new ArrayList<String>();
		OscarData.newQueryByCategory(category);
		OscarData.refineByYear(year);
		ListIterator<Oscar> lt = OscarData.getResults().listIterator();
		while(lt.hasNext()) {
			names.add(lt.next().getName());
		}
		Endpoints col = new Endpoints(names);
		col.setYear(year);
		return col;
	}
	/*
	 * Query Method displays movie names based on type & year.
	 */
	@GetMapping("movies/search")
	public Endpoints movieSearch(@RequestParam(value = "type", defaultValue = "actor") String category,
	                             @RequestParam(value = "year", defaultValue = "1997") int year) {
		ArrayList<String> names = new ArrayList<String>();
		OscarData.newQueryByCategory(category);
		OscarData.refineByYear(year);
		ListIterator<Oscar> lt = OscarData.getResults().listIterator();
		while(lt.hasNext()) {
			names.add(lt.next().getFilm());
		}
		Endpoints col = new Endpoints(names);
		col.setYear(year);
		return col;
	}
}

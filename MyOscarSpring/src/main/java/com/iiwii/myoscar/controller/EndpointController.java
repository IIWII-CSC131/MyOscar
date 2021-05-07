package com.iiwii.endpoints;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.iiwii.myoscar.movie_data.Oscar;
import com.iiwii.myoscar.movie_data.OscarData;
import java.util.ArrayList;
import java.util.ListIterator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author John Vardanyan
 * 
 * Rest Endpoints
 * 06 May 2021
 */


/**
 * 
 * This method search actress nomated for best actress
 * Can change the year
 */
@RestController
public class EndpointController {
	// insert year after "year/1997"
	// eg. /movies/categories/bestpicture/year/1997?year=1950
	@GetMapping("/categories/bestactress")
	public Endpoints bestActress(@RequestParam(value = "year",
	defaultValue = "1997") int year) {		
		ArrayList<String> names = new ArrayList<String>(); 
		OscarData.newQueryByCategory("ACTRESS");
		OscarData.refineByYear(year);
		ListIterator<Oscar> lt = OscarData.getResults().listIterator();
		while(lt.hasNext()) {
			names.add(lt.next().getName());
		}
		Endpoints col = new Endpoints(names);
		col.setYear(year);
		return col;
	}
	/**
	 * 
	 * This method search best picture nomination
	 * can change year
	 *
	 */
	// insert year after "?year=#
	// eg. /movies/categories/bestpicture?year=1970
	@RequestMapping("/movies/categories/bestpicture")
	public Endpoints bestPicture(@RequestParam(value = "year",
			defaultValue = "1997") int year) {
		ArrayList<String> names = new ArrayList<String>();
		OscarData.newQueryByCategory("BEST PICTURE");
		OscarData.refineByYear(year);
		ListIterator<Oscar> lt = OscarData.getResults().listIterator();
		while(lt.hasNext()) {
			names.add(lt.next().getName());
		}
		Endpoints col = new Endpoints(names);
		col.setYear(year);
		return col;
	}
	/**
	 * 
	 * This method search winners of "best picture"
	 * can change year and category
	 *
	 */
	// insert ?year = # or ?category = ""
	// eg. /movies/categories/bestpicture?year=1970
	@GetMapping("/bestpicture/year/1997/winner")
	public Endpoints winner(
			@RequestParam(value = "category",
			defaultValue = "BEST PICTURE") String category,
			@RequestParam(value = "year",
			defaultValue = "1964") int year) {
		ArrayList<String> names = new ArrayList<String>();
		OscarData.newQueryByCategory(category);
		OscarData.refineByYear(year);
		OscarData.refineByWinner(true);
		ListIterator<Oscar> lt = OscarData.getResults().listIterator();
		while(lt.hasNext()) {
			names.add(lt.next().getName());
		}
		Endpoints col = new Endpoints(names);
		col.setYear(year);
		return col;
	}
	/**
	 * 
	 * This method searches for what you are looking for
	 * can change year, category and film name
	 */
	@GetMapping("/search")
	public Endpoints search(
			@RequestParam(value = "year",
			defaultValue = "1997") int year,
			@RequestParam(value = "category",
			defaultValue = "DOCUMENTARY (Feature)") String category,
			@RequestParam(value = "film",
			defaultValue = "The Long Way Home") String film) {
		ArrayList<String> names = new ArrayList<String>();
		OscarData.newQueryByCategory(category);
		OscarData.refineByYear(year);
		OscarData.refineByFilm(film);
		ListIterator<Oscar> lt = OscarData.getResults().listIterator();
		while(lt.hasNext()) {
			names.add(lt.next().getName());
		}
		Endpoints col = new Endpoints(names);
		col.setYear(year);
		return col;
	}
}

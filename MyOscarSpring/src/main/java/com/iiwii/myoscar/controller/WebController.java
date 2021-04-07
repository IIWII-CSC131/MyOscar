package com.iiwii.myoscar.controller;

import com.iiwii.myoscar.models.BestActress;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	 * @return BestActress object
	 */
	@RequestMapping("/categories/bestactress/1997")
	public BestActress bestActress()
	{
		BestActress ba = new BestActress();
//		String[] actresses = {"a", "b", "c"};
		Map<String, String> actresses = new Hashtable<>();
		actresses.put("actress a", "mov");
		actresses.put("actress b", "mov");
		actresses.put("actress c", "mov");
		actresses.put("actress d", "mov");
		actresses.put("actress e", "mov");
		ba.setActresses(actresses);
		return ba;
	}
}

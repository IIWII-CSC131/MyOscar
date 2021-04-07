package com.iiwii.myoscar.models;

import java.util.Hashtable;
import java.util.Map;

/**
 * <p></p>
 *
 * @author Eric Rodriguez
 * @version 1.0
 */
public class BestActress
{
	private Map<String, String> actresses;
	
	public BestActress()
	{
		actresses = new Hashtable<>();
	}
	
	public void setActresses(Map<String, String> list)
	{
		actresses = list;
	}
	
	public Map<String, String> getActresses()
	{
		return actresses;
	}
}

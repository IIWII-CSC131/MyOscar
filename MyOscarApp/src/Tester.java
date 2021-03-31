import UAC.UserAccount;

import java.util.ArrayList;

public class Tester
{
	public static void main(String[] args)
	{
		UserAccount uac = new UserAccount();
//		uac.createNewUser();
		uac.createNewSession();
		
		ArrayList<String> movies = uac.getMovies();
		ArrayList<String> prefs = uac.getPreferences();
		
		for (String movie : movies)
		{
			pl(movie);
		}
		
		for (String pref : prefs)
		{
			pl(pref);
		}
	}
	
	static void pl(Object obj)
	{
		System.out.println(obj);
	}
}

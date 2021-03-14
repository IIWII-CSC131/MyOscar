package UAC;

import java.io.*;

/**
 * <p>Handles user control for storing information</p>
 * 
 * @author Eric Rodriguez
 * @version 1.0
 */
public class UAC
{
	private Sentinel sentinel;
	
	public UAC()
	{
		createPersistentSession();
	}
	
	private void createPersistentSession()
	{
		// TODO: init sentinel so that it can be reused
	}
	
	public void createNewUser()
	{
		// TODO: Ask for username and password and create necessary files
	}
	
	private static void verifyFile(File file)
	{
		try
		{
			FileOutputStream oFile = new FileOutputStream(file, true);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	private static int findUserLine(File file, String username)
	{
		try
		{
			FileReader reader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(reader);
			String readLine = bufferedReader.readLine();
			int line = 0;
			
			while (readLine != null)
			{
				if (readLine.equals("user:" + username))
				{
					bufferedReader.close();
					return line;
				}
				readLine = bufferedReader.readLine();
				line++;
			}
			
			bufferedReader.close();
			reader.close();
			return -1;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return -1;
		}
	}
}

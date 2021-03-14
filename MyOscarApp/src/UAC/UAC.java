package UAC;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

/**
 * <p>Handles user control for storing information</p>
 *
 * @author Eric Rodriguez
 * @version 1.0
 */
public class UAC
{
	private final static File usersFile = new File("users.dat");
	
	private Sentinel sentinel;
	
	public UAC()
	{
		if (!verifyFile(usersFile))
		{
			System.out.println("There was an error verifying the user file " + usersFile.getName());
		}
	}
	
	private static boolean verifyFile(File file)
	{
		try
		{
			// Creates the file if it does not exist
			// FileOutputStream oFile = new FileOutputStream(file, true);
			new FileOutputStream(file, true);
			return true;
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * <p>Finds a specified username in the user file.</p>
	 * @param username The username to look for.
	 * @return The line where the username is. Returns -1 if not found.
	 */
	private static int findUserLine(String username)
	{
		try
		{
			FileReader reader = new FileReader(usersFile);
			BufferedReader bufferedReader = new BufferedReader(reader);
			String readLine = bufferedReader.readLine();
			int line = 0;
			
			while (readLine != null)
			{
				if (readLine.equals("user:" + username))
				{
					bufferedReader.close();
					reader.close();
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
	
	/**
	 * <p>Create a new user session to allow multiple calls to the user file without having to log in again.</p>
	 */
	private void createNewSession()
	{
		Scanner scanner = new Scanner(System.in);
		String username = "";
		String password = "";
		int userLine = -1;
		
		// Get username
		while (userLine == -1)
		{
			System.out.print("Enter username: (type ':q' to quit) \n> ");
			username = scanner.nextLine().trim();
			
			if (username.equals(":q"))
			{
				return;
			}
			
			// Check if user exists
			if ((userLine = findUserLine(username)) == -1)
			{
				System.out.println("User doesn't exist");
			}
		}
		
		// Get password
		while (password.equals(""))
		{
			System.out.print("Enter password: (type ':q' to quit) \n> ");
			password = scanner.nextLine().trim();
			
			if (password.equals(":q"))
			{
				return;
			}
			
			// Change password to a long value that will be used for the seed
			long seed = 0;
			
			for (char temp : password.toCharArray())
			{
				seed += temp;
			}
			
			seed = seed * password.length();
			sentinel = new Sentinel(seed);
			String verification = readLine(userLine + 1);
			
			// Check the verification to see if the password is correct
			if (username.equals(sentinel.decrypt(verification)))
			{
				System.out.println("Successfully logged in");
			}
			else
			{
				System.out.println("Incorrect password");
				sentinel = null;
				password = "";
			}
		}
	}
	
	/**
	 * <p>Creates a new user and initializes a session.</p>
	 */
	public void createNewUser()
	{
		Scanner scanner = new Scanner(System.in);
		String username = "";
		String password = "";
		
		// Get a username
		while (username.equals(""))
		{
			System.out.print("Enter username: (type ':q' to quit) \n> ");
			username = scanner.nextLine().trim();
			
			if (username.equals(":q"))
			{
				return;
			}
			
			if (username.contains(" "))
			{
				System.out.println("Username can't contain spaces");
				username = "";
			}
			else if (username.length() < 6)
			{
				System.out.println("Username must be at least 6 characters");
				username = "";
			}
		}
		
		// Check if user already exists
		if (findUserLine(username) != -1)
		{
			System.out.println("User already exists");
			return;
		}
		
		// Get a password
		while (password.equals(""))
		{
			System.out.print("Enter password: (type ':q' to quit) \n> ");
			password = scanner.nextLine().trim();
			
			if (password.equals(":q"))
			{
				return;
			}
			
			if (password.contains(" "))
			{
				System.out.println("Password can't contain spaces");
				password = "";
			}
			else if (password.length() < 8)
			{
				System.out.println("Password must be at least 8 characters");
				password = "";
			}
		}
		
		// Change password to a long value that will be used for the seed
		long seed = 0;
		
		for (char temp : password.toCharArray())
		{
			seed += temp;
		}
		
		seed = seed * password.length();
		sentinel = new Sentinel(seed);
		
		String verification;
		String watchedMovies = "none";
		String userPref = "none";
		String nl = "\r\n";
		String end = "endUser";
		
		// Encrypt needed lines
		username = "user:" + username;
		verification = sentinel.encrypt(username);
		watchedMovies = sentinel.encrypt(watchedMovies);
		userPref = sentinel.encrypt(userPref);
		
		// The line to be written to the file
		String outLine = username + nl + verification + nl + watchedMovies + nl + userPref + nl + end + nl;
		
		try
		{
			// Write the new user into the file
			Files.write(Paths.get(usersFile.getName()), outLine.getBytes(), StandardOpenOption.APPEND);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>Reads a specific line from the user file and decrypts it.</p>
	 *
	 * @param line The specified line to read.
	 * @return The decrypted line.
	 */
	private String readLine(int line)
	{
		try
		{
			FileReader reader = new FileReader(usersFile);
			BufferedReader bufferedReader = new BufferedReader(reader);
			String out;
		
			// Use readLine() to skip to the part where we actually need to read
			for (int i = 0; i < line; i++)
			{
				bufferedReader.readLine();
			}
			
			// Read the line ad decrypt it 
			out = sentinel.decrypt(bufferedReader.readLine().trim());
			bufferedReader.close();
			reader.close();
			return out;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * <p>Writes a a string into the user file. The line will get replaced, and as such cannot be appended.</p>
	 * <p>This method can theoretically write multiple lines instead of replacing one line by using {@code "\n"} but
	 * this is not recommended.</p>
	 *
	 * @param output The string to write into the file.
	 * @param line The line to write to.
	 */
	private void writeToFile(String output, int line)
	{
		try
		{
			FileReader reader = new FileReader(usersFile);
			BufferedReader bufferedReader = new BufferedReader(reader);
			StringBuilder present = new StringBuilder();
			String end;
			
			// Record the data already present into StringBuilder
			for (int i = 0; i < line; i++)
			{
				present.append(bufferedReader.readLine()).append("\n");
			}
			
			// Add the new output and skip reading the line from the user file
			present.append(sentinel.encrypt(output)).append("\n");
			bufferedReader.readLine();
			
			// Add the remaining lines
			while ((end = bufferedReader.readLine()) != null)
			{
				present.append(end).append("\n");
			}
			
			bufferedReader.close();
			reader.close();
			
			// Write the file
			FileOutputStream out = new FileOutputStream(usersFile);
			out.write(present.toString().getBytes());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}

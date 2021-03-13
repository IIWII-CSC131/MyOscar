package UAC;

import java.util.Random;

/**
 * <p>An encryption tool that uses and Enigma-like system by implementing rotating arrays.</p>
 * <p>Works only with hexadecimal data. Converts data from String to String of hex values, and vice-versa</p>
 *
 * @author Eric Rodriguez
 * @version 1.0
 */
public class Sentinel
{
	private final Random rand;
	
	private final int[] rotary1;
	private final int[] rotary2;
	private final int[] rotary3;
	
	private int[] workingRotary1;
	private int[] workingRotary2;
	
	private int rStep;
	
	/**
	 * <p>Create and initialize the Sentinel class with all rotaries</p>
	 *
	 * @param seed The seed to initialize all the rotaries
	 */
	public Sentinel(long seed)
	{
		rand = new Random(seed);
		
		rotary1 = createHexRotary();
		rotary2 = createHexRotary();
		rotary3 = createHexRotary();
		
		workingRotary1 = rotary1.clone();
		workingRotary2 = rotary2.clone();
		
		rStep = 0;
	}
	
	/**
	 * <p>Creates an array of size 16 that acts as a pathway from one data value to another.</p>
	 * <p>Initializes using the method {@code array[a] = b} and {@code array[b] = a}</p>
	 *
	 * @return Array of size 16
	 */
	private int[] createHexRotary()
	{
		int lim = 16; // Always 16 because rotary handles numbers 0 to f
		int position = 0;
		
		int[] newRotary = new int[lim];
		
		for (int i = 0; i < lim; i++)
		{
			newRotary[i] = -1;
		}
		
		// initialize rotary
		while (position < lim)
		{
			// Ignore this position if a value is already there
			if (newRotary[position] > -1)
			{
				position++;
				continue;
			}
			
			// Find a new value from the possible remaining values
			// Only tries indices above position since the ones before are already filled
			int tempInt = rand.nextInt(lim - position) + position;
			
			if (newRotary[tempInt] == -1)
			{
				newRotary[position] = tempInt;
				newRotary[tempInt] = position;
				position++;
			}
		}
		
		return newRotary;
	}
	
	/**
	 * <p>Encrypts a line into hexadecimal</p>
	 *
	 * @param line The line to encrypt
	 * @return The encrypted line in hexadecimal
	 */
	public String encrypt(String line)
	{
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		
		for (char temp : line.toCharArray())
		{
			sb1.append(Integer.toHexString(temp));
		}
		
		for (int i = 0; i < sb1.length(); i++)
		{
			sb2.append(intToHex(workEncrypt(hexToInt(sb1.substring(i, i + 1)))));
		}
		
		// Reset shifting rotaries
		reset();
		
		return sb2.toString();
	}
	
	/**
	 * <p>Decrypts a line into hexadecimal</p>
	 *
	 * @param line The line to decrypt in hexadecimal
	 * @return The decrypted line
	 */
	public String decrypt(String line)
	{
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		
		for (int i = 0; i < line.length(); i++)
		{
			sb1.append(intToHex(workDecrypt(hexToInt(line.substring(i, i + 1)))));
		}
		
		for (int i = 0; i < sb1.length() - 1; i += 2)
		{
			String temp = sb1.substring(i, i + 2);
			sb2.append((char) Integer.parseInt(temp, 16));
		}
		
		// Reset shifting rotaries
		reset();
		
		return sb2.toString();
	}
	
	/**
	 * <p>Passes a value through the rotaries to encrypt it, then shifts the rotaries.</p>
	 * <p>Value gets passed "forward" so that {@code newVal = array[val]}</p>
	 * @param val Value to work on
	 * @return Value after going through rotaries
	 */
	private int workEncrypt(int val)
	{
		val = workingRotary1[val];
		val = workingRotary2[val];
		val = rotary3[val];
		val = workingRotary2[val];
		val = workingRotary1[val];
		
		step();
		
		return val;
	}
	
	/**
	 * <p>Passes a value through the rotaries to decrypt it, then shifts the rotaries.</p>
	 * <p>Value gets passed "backwards" so that the equation '{@code array[newVal] == val}' is satisfied. </p>
	 *
	 * @param val Value to work on
	 * @return Value after going through rotaries
	 */
	@SuppressWarnings("Duplicates")
	private int workDecrypt(int val)
	{
		for (int i = 0; i < workingRotary1.length; i++)
		{
			if (workingRotary1[i] == val)
			{
				val = i;
				break;
			}
		}
		
		for (int i = 0; i < workingRotary2.length; i++)
		{
			if (workingRotary2[i] == val)
			{
				val = i;
				break;
			}
		}
		
		val = rotary3[val];
		
		for (int i = 0; i < workingRotary2.length; i++)
		{
			if (workingRotary2[i] == val)
			{
				val = i;
				break;
			}
		}
		
		for (int i = 0; i < workingRotary1.length; i++)
		{
			if (workingRotary1[i] == val)
			{
				val = i;
				break;
			}
		}
		
		step();
		
		return val;
	}
	
	/**
	 * <p>Simulate rotating the array, so that duplicate characters may not always have the same output</p>
	 * <p>Shifts all values to the right by one</p>
	 */
	private void step()
	{
		rStep++;
		int switcher = workingRotary1[workingRotary1.length - 1];
		System.arraycopy(workingRotary1, 0, workingRotary1, 1, workingRotary1.length - 1);
		workingRotary1[0] = switcher;
		
		// Rotate the second rotary if the first has done one complete rotation
		if (rStep > workingRotary2.length - 1)
		{
			switcher = workingRotary2[workingRotary2.length - 1];
			System.arraycopy(workingRotary2, 0, workingRotary2, 1, workingRotary2.length - 1);
			workingRotary2[0] = switcher;
			rStep = 0;
		}
	}
	
	/**
	 * <p>Reset the rotaries so that it starts at the same position on each workload</p>
	 */
	private void reset()
	{
		workingRotary1 = rotary1.clone();
		workingRotary2 = rotary2.clone();
		rStep = 0;
	}
	
	/**
	 * <p>Turns a hexadecimal character into an integer value</p>
	 * <p>"0"..."f" to 0...15</p>
	 *
	 * @param val String to work on (should only be one character)
	 * @return Converted value as an integer
	 */
	public int hexToInt(String val)
	{
		switch (val)
		{
			case "f":
				return 15;
			case "e":
				return 14;
			case "d":
				return 13;
			case "c":
				return 12;
			case "b":
				return 11;
			case "a":
				return 10;
			default:
				try
				{
					return Integer.parseInt(val);
				}
				catch (Exception e)
				{
					e.printStackTrace();
					return -1;
				}
		}
	}
	
	/**
	 * <p>Turns an integer into a hexadecimal character</p>
	 * <p>0...15 to "0"..."f"</p>
	 *
	 * @param val Integer to work on
	 * @return Converted value as an character
	 */
	public String intToHex(int val)
	{
		if (val < 10 && val >= 0)
		{
			return ("" + val);
		}
		
		switch (val)
		{
			case 10:
				return "a";
			case 11:
				return "b";
			case 12:
				return "c";
			case 13:
				return "d";
			case 14:
				return "e";
			case 15:
				return "f";
			default:
				return "";
		}
	}
}

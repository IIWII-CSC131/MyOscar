package com.iiwii.myoscar;

import com.iiwii.myoscar.gui.MainWindow;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import javax.swing.*;

/**
 * <p>Starts the Spring-Boot module</p>
 *
 * @author Eric Rodriguez
 * @version 1.0
 */
@SpringBootApplication
public class MyOscarApplication
{
	/**
	 * <p>The starting point. The start is edited slightly to allow us to use Java Swing using
	 * {@code headless(false)} in the {@code SpringApplicationBuilder} object.</p>
	 *
	 * @param args Ignored
	 */
	public static void main(String[] args)
	{
		// This is the default way to start it
		// SpringApplication.run(MyOscarApplication.class, args);
		
		// We start it this way. It allows us to run swing
		new SpringApplicationBuilder(MyOscarApplication.class).headless(false).run();
	}
	
	/**
	 * <p>The @Bean annotation tells Spring-Boot that it should use and manage the object that gets returned. In this 
	 * case, it is the main window for the GUI.</p>
	 *
	 * @return The main GUI window.
	 */
	@Bean
	public MainWindow mainWindowInit()
	{
		// Set the look and feel to a more natural looking windows style (assuming this will run on a windows machine)
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		// Returns a new MainWindow object that Spring-Boot will handle
		return new MainWindow();
	}
}

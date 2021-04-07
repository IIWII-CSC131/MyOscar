package com.iiwii.myoscar.gui;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * <p>This class runs and shows the GUI window. Spring-Boot manages it by using the @Component annotation.</p>
 *
 * @author Eric Rodriguez
 * @version 1.0
 */
@Component
public class MainWindowRunner implements CommandLineRunner
{
	private final MainWindow window;
	
	public MainWindowRunner(MainWindow window)
	{
		this.window = window;
	}
	
	/**
	 * <p>Spring-Boot will run this. {@code invokeLater()} is used to run the main window in a thread-safe manner 
	 * handled by an AWT event-dispatching thread.</p>
	 *
	 * @param args Ignored
	 * @throws Exception Required throwable
	 */
	@Override
	public void run(String... args) throws Exception
	{
		java.awt.EventQueue.invokeLater(() -> window.setVisible(true));
	}
}

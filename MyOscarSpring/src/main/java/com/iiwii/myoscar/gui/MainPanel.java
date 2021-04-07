package com.iiwii.myoscar.gui;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel
{
	private MainWindow frame;
	
	private final Font FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
	
	public MainPanel(MainWindow frame)
	{
		this.frame = frame;
		initPanel();
	}
	
	private void initPanel()
	{
		setSize(frame.getMainPanelSize());
		SpringLayout lay = new SpringLayout();
		setLayout(lay);
	}
}

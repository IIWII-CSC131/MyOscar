package com.iiwii.myoscar.gui;

import javax.swing.*;
import java.awt.*;

public class SidebarPanel extends JPanel
{
	
	private final MainWindow frame;
	
	private final Font FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
	
	public SidebarPanel(MainWindow frame)
	{
		this.frame = frame;
		System.out.println(getWidth());
		init();
	}
	
	private void init()
	{
		setSize(frame.getSidebarPanelSize());
		SpringLayout lay = new SpringLayout();
		setLayout(lay);
	}
}

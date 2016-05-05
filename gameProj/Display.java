package gameProj;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Display extends JFrame 
{
	//attributes
	private JFrame frame;
	private Canvas canvas;
	private String title;
	private int width, height;
	
	//methods
	public Display(String title, int width, int height)
	{
		this.title = title;
		this.width = width;
		this.height = height;
		
		this.createDisplay();
	}
	private void createDisplay()
	{
		frame = new JFrame(this.title);
		frame.setSize(this.width, this.height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(this.width, this.height));
		canvas.setMaximumSize(new Dimension(this.width, this.height));
		canvas.setMinimumSize(new Dimension(this.width, this.height));
		canvas.setFocusable(false);
		
		frame.add(canvas);
		frame.pack();
	}
	public Canvas getCanvas()
	{
		return this.canvas;
	}
	public JFrame getFrame()
	{
		return this.frame;
	}
}

package gfx;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.*;

/**
 * Klasa odpowiadająca za wyświetlanie danych. Rozszerza JFrame.
 */
public class Display extends JFrame
{
	//attributes
	private JFrame frame;
	private Canvas canvas;
	private String title;
	private int width, height;

    //methods

    /**
     * Konstruktor parametryczny klasy Display
     * @param title tytuł wyświetlający się w nagłówku okna
     * @param width szerokość okna
     * @param height wysokość okna
     */
	public Display(String title, int width, int height)
	{
		this.title = title;
		this.width = width;
		this.height = height;
		
		this.createDisplay();
	}

    /**
     * Metoda ta tworzy nowe okno i ustawia jego parametry takie jak rozmiar, ikonę, tytuł itp.
     */
    private void createDisplay()
	{
		frame = new JFrame(this.title);
		frame.setSize(this.width, this.height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		frame.setIconImage(new ImageIcon(getClass().getResource("/images/icon.png")).getImage());
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(this.width, this.height));
		canvas.setMaximumSize(new Dimension(this.width, this.height));
		canvas.setMinimumSize(new Dimension(this.width, this.height));
		canvas.setFocusable(false);

		frame.add(canvas);
		frame.pack();
	}

    /**
     * Getter
     * @return obiekt klasy Canvas
     */
    public Canvas getCanvas()
	{
		return this.canvas;
	}

    /**
     * Getter
     * @return obiekt klasy JFrame
     */
    public JFrame getFrame()
	{
		return this.frame;
	}
}

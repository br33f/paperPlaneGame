package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener
{
	//attributes
	private boolean[] keys;
	public boolean up, down, left, right, esc;
	public boolean p_esc;
	
	//methods
	public KeyManager()
	{
		keys = new boolean[256];
	}
	public void tick()
	{
		up = keys[KeyEvent.VK_UP];
		down = keys[KeyEvent.VK_DOWN];
		left = keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_RIGHT];
		if(!esc && keys[KeyEvent.VK_ESCAPE])
			p_esc = true;
		else
			p_esc = false;
		esc = keys[KeyEvent.VK_ESCAPE];
	}
	@Override
	public void keyPressed(KeyEvent e) 
	{
		keys[e.getKeyCode()] = true;
	}
	@Override
	public void keyReleased(KeyEvent e) 
	{
		keys[e.getKeyCode()] = false;
	}
	@Override
	public void keyTyped(KeyEvent e) 
	{
		
	}
}

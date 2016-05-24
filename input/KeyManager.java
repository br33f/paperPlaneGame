package input;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Klasa KeyManager - zarządca klawiszy.
 * Implementuje interfejs KeyListener.
 * Reaguje na naciśnięty klawisz ustawiając odpowiednio zdefiniowane flagi.
 * Klasa pozwala na reakcje na klawisz naciśnięty, puszczony, a także na zbocze opadające (znaczy reaguje tylko raz na jedno naciśnięcie)
 */
public class KeyManager implements KeyListener
{
	//attributes
	private boolean[] keys;
	public boolean up, down, left, right, esc, enter;
	public boolean p_esc, p_up, p_down, p_enter;

    //methods

    /**
     * Konstuktor klasy KeyManager.
     * Nie przyjmuje parametrów, zeruje jedynie stany klawiszy.
     */
	public KeyManager()
	{
		this.clearKeys();
	}

    /**
     * Metoda ustawiająca flagi naciśniętych klawiszy.
     */
    public void tick()
	{
		//przyciski reagujace na zbocze dodatnie
		p_esc = this.getPKey(esc, keys[KeyEvent.VK_ESCAPE]);
		p_up = this.getPKey(up, keys[KeyEvent.VK_UP]);
		p_down = this.getPKey(down, keys[KeyEvent.VK_DOWN]);
        p_enter = this.getPKey(enter, keys[KeyEvent.VK_ENTER]);

		up = keys[KeyEvent.VK_UP];
		down = keys[KeyEvent.VK_DOWN];
		left = keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_RIGHT];
		esc = keys[KeyEvent.VK_ESCAPE];
		enter = keys[KeyEvent.VK_ENTER];
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

    /**
     * Metoda pobiera stan przycisku reagujący na zbocze.
     * @param k1 przycisk reagujący zwyczajnie
     * @param k2 keys[KeyEvent.KLUCZ_PRZYCISKU]
     * @return stan klawisza
     */
	private boolean getPKey(boolean k1, boolean k2)
	{
		if(!k1 && k2)
			return true;
		else
			return false;
	}

    /**
     * Metoda zeruje stany klawiszy. Wymagane przy ponownym uruchomieniu rozgrywki.
     */
    public void clearKeys()
	{
		keys = new boolean[256];
		up = down = left = right = esc = enter = false;
		p_esc = p_up = p_down = p_enter = false;
	}
}

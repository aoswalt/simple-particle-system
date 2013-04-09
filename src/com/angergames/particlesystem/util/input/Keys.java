package com.angergames.particlesystem.util.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Keys.java
 * Purpose: Handle keyboard input.
 * 
 * @author Adam Oswalt
 */
public class Keys implements KeyListener {
	
	public static boolean g;
	public static boolean w;
	public static boolean s;
	public static boolean m;
	public static boolean h;

	@Override
	public void keyPressed(KeyEvent e) {
		toggle(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		toggle(e.getKeyCode(), false);
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	private void toggle(int k, boolean state) {
		switch(k) {
			case KeyEvent.VK_G: 
				g = state;
				break;
			case KeyEvent.VK_W: 
				w = state;
				break;
			case KeyEvent.VK_S: 
				s = state;
				break;
			case KeyEvent.VK_M: 
				m = state;
				break;
			case KeyEvent.VK_H: 
				h = state;
				break;
		}
	}
}

package com.angergames.particlesystem.util.input;

import java.awt.Component;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import com.angergames.particlesystem.Demo;
import com.angergames.particlesystem.util.math.Vec2;

public class Mouse implements MouseListener {
	
	private static Component component;
	private static Point point;
	private static PointerInfo info;
	
	public static boolean pressed;
	public static Vec2 pos = new Vec2();
	private static Vec2 lastPos = new Vec2();
	private static Vec2 movement = new Vec2();
	
	public Mouse(Component component) {
		Mouse.component = component;
	}
	
	public static void update() {
		lastPos.setTo(pos);
		info = MouseInfo.getPointerInfo();
		point = info.getLocation();
		
		SwingUtilities.convertPointFromScreen(point, component);
		pos.setTo(point.x / Demo.SCALE, point.y / Demo.SCALE);
		
		movement.setTo(pos).subtract(lastPos);
	}
	
	public static Vec2 getMovement() {
		return movement;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pressed = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}

package com.angergames.particlesystem.gfx;

import com.angergames.particlesystem.util.math.Math2;

/**
 * Colors.java
 * Purpose: Provide methods to manipulate hex colors.
 * 
 * @author Adam Oswalt
 */
public class Colors {
	
	public static int blend(int newColor, int oldColor, double percent) {
		
		percent = Math2.clamp(0, 1, percent);

		int a = (int) (percent * (newColor >> 24 & 0xFF) + (1 - percent) * (oldColor >> 24 & 0xFF));
		int r = (int) (percent * (newColor >> 16 & 0xFF) + (1 - percent) * (oldColor >> 16 & 0xFF));
		int g = (int) (percent * (newColor >> 8 & 0xFF) + (1 - percent) * (oldColor >> 8 & 0xFF));
		int b = (int) (percent * (newColor & 0xFF) + (1 - percent) * (oldColor & 0xFF));
		
		return (a << 24) + (r << 16) + (g << 8) + b;
	}
}

package com.angergames.particlesystem.gfx;

public class Colors {
	
	public static int blend(int newColor, int oldColor, double percent) {

		int r = (int) (percent * (newColor >> 16 & 0xFF) + (1 - percent) * (oldColor >> 16 & 0xFF));
		int g = (int) (percent * (newColor >> 8 & 0xFF) + (1 - percent) * (oldColor >> 8 & 0xFF));
		int b = (int) (percent * (newColor & 0xFF) + (1 - percent) * (oldColor & 0xFF));
		
		return (r << 16) + (g << 8) + b;
	}
}

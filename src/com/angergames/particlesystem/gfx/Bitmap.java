package com.angergames.particlesystem.gfx;

public class Bitmap {
	
	public int w;
	public int h;
	
	public int[] pixels;
	
	public Bitmap(int w, int h) {
		this.w = w;
		this.h = h;
		
		this.pixels = new int[w * h];
	}
	
	public void setColor(int c) {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = c;
		}
	}
	
	public void clear() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void render(Bitmap b, int xPos, int yPos) {
		for(int x = 0; x < b.w; x++) {
			int xPix = xPos + x;
			if(xPix < 0 || xPix >= w) continue;
			for(int y = 0; y < b.h; y++) {
				int yPix = yPos + y;
				if(yPix < 0 || yPix >= h) continue;
				
				pixels[xPix + yPix * w] = b.pixels[x + y * b.w];
			}
		}
	}
	
	public void render(int c, int xPos, int yPos) {
		if(xPos < 0 || xPos >= w) return;
		if(yPos < 0 || yPos >= h) return;
		pixels[xPos + yPos * w] = c; 
	}
}

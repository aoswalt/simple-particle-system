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
	
	public Bitmap setColor(int c) {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = c;
		}
		return this;
	}
	
	public void clear() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void render(Bitmap bm, int xPos, int yPos) {
		for(int x = 0; x < bm.w; x++) {
			int xPix = xPos + x;
			if(xPix < 0 || xPix >= w) continue;
			for(int y = 0; y < bm.h; y++) {
				int yPix = yPos + y;
				if(yPix < 0 || yPix >= h) continue;
				
				double alpha = ((bm.pixels[x + y * bm.w] >> 24) & 0xFF) / 255.0;
				if(alpha > 0) {
					if(alpha == 1) {
						pixels[xPix + yPix * w] = bm.pixels[x + y * bm.w];
					} else {
						// result_RGB = alpha * new + ( 1 - alpha ) * old   -   blending
						int a0 = (pixels[xPix + yPix * w] >> 24) & 0xFF;
						int r0 = (pixels[xPix + yPix * w] >> 16) & 0xFF;
						int g0 = (pixels[xPix + yPix * w] >> 8) & 0xFF;
						int b0 = pixels[xPix + yPix * w] & 0xFF;

						int a1 = (bm.pixels[x + y * bm.w] >> 24) & 0xFF;
						int r1 = (bm.pixels[x + y * bm.w] >> 16) & 0xFF;
						int g1 = (bm.pixels[x + y * bm.w] >> 8) & 0xFF;
						int b1 = bm.pixels[x + y * bm.w] & 0xFF;
						
						int a = (int)(alpha * a1 + (1 - alpha) * a0);
						int r = (int)(alpha * r1 + (1 - alpha) * r0);
						int g = (int)(alpha * g1 + (1 - alpha) * g0);
						int b = (int)(alpha * b1 + (1 - alpha) * b0);
						
						pixels[xPix + yPix * w] = (a << 24) + (r << 16) + (g << 8) + b;
					}
				}
			}
		}
	}
	
	public void render(int c, int xPos, int yPos) {
		if(xPos < 0 || xPos >= w) return;
		if(yPos < 0 || yPos >= h) return;
		pixels[xPos + yPos * w] = c; 
	}
}

package com.angergames.particlesystem.particles;

import com.angergames.particlesystem.gfx.Bitmap;
import com.angergames.particlesystem.util.math.Vec2;

public class Particle {
	private static final int LIFETIME = 40;
	private static final int NEWCOLOR = 0xFFFFFF;
	private static final int OLDCOLOR = 0x880000;
	
	public Vec2 pos = new Vec2();
	public Vec2 vel = new Vec2();
	public Vec2 acc = new Vec2();
	private boolean active = false;
	
	private int life = 0;
	
	public void create(Vec2 v) {
		create(v.x, v.y);
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void create(double x, double y) {
		pos.set(x, y);
		life = LIFETIME;
		active = true;
		vel.set(0, 0);
		acc.set(0, 0);
	}
	
	public void update(double delta) {
		if(!active) return;
		
		life--;
		if(life <= 0) {
			active = false;
			return;
		}
		
		
	}
	
	public int getLifeColor() {
		double percent = (double)(life) / LIFETIME;
		
		int r = (int) (percent * (NEWCOLOR >> 16 & 0xFF) + (1 - percent) * (OLDCOLOR >> 16 & 0xFF));
		int g = (int) (percent * (NEWCOLOR >> 8 & 0xFF) + (1 - percent) * (OLDCOLOR >> 8 & 0xFF));
		int b = (int) (percent * (NEWCOLOR & 0xFF) + (1 - percent) * (OLDCOLOR & 0xFF));
		
		return (r << 16) + (g << 8) + b;
	}
	
	public void renderTo(Bitmap b) {
		b.render(getLifeColor(), (int)pos.x, (int)pos.y);
	}
}

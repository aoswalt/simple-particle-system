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
		return (int) (percent * OLDCOLOR + (1 - percent) * NEWCOLOR);
	}
	
	public void renderTo(Bitmap b) {
		b.render(getLifeColor(), (int)pos.x, (int)pos.y);
	}
}

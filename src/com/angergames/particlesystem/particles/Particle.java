package com.angergames.particlesystem.particles;

import com.angergames.particlesystem.gfx.Bitmap;
import com.angergames.particlesystem.gfx.Colors;
import com.angergames.particlesystem.level.Map;
import com.angergames.particlesystem.util.math.Vec2;

public class Particle {
	private static final int LIFETIME = 80;
	private static final int NEWCOLOR = 0xFFFFFF;
	private static final int OLDCOLOR = 0x880000;
	
	public Vec2 pos = new Vec2();
	public Vec2 vel = new Vec2();
	public Vec2 acc = new Vec2();
	private boolean active = false;
	private Vec2 lastPos = new Vec2();
	private Vec2 nextPos = new Vec2();
	
	private int life = 0;
	
	private Map map;
	
	public Particle(Map level) {
		this.map = level;
	}
	
	public void create(Vec2 v) {
		create(v.x, v.y);
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void destroy() {
		active = false;
	}
	
	public void create(double x, double y) {
		pos.setTo(x, y);
		lastPos.setTo(pos);
		life = LIFETIME;
		active = true;
		vel.setTo(0, 0);
		acc.setTo(0, 0);
	}
	
	public void update(double delta) {
		if(!active) return;
		
		life--;
		if(life <= 0) {
			active = false;
			return;
		}
		
		if(map.hasGravity) {
			acc.add(map.gravity);
		}
		
		// verlet integration
		vel.setTo(pos).subtract(lastPos);
		nextPos.setTo(pos).add(vel).add(acc.scale(delta));
		
		if(map.isSolid(nextPos.x, pos.y)) {	//moving x direction
			nextPos.x = pos.x;
			vel.x *= -1;
		}
		
		if(map.isSolid(pos.x, nextPos.y)){
			nextPos.y = pos.y;
			vel.y *= -1;
		}

		lastPos.setTo(pos);
		pos.setTo(nextPos);
		
		acc.setTo(0, 0);
	}
	
	public int getLifeColor() {
		double percent = (double)(life) / LIFETIME;
		
		return Colors.blend(NEWCOLOR, OLDCOLOR, percent);
	}
	
	public void renderTo(Bitmap b) {
		b.render(getLifeColor(), (int)pos.x, (int)pos.y);
	}
}

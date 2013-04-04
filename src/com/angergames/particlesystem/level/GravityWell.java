package com.angergames.particlesystem.level;

import com.angergames.particlesystem.util.math.Vec2;

public class GravityWell {
	
	public Vec2 pos = new Vec2();
	public double mass;
	
	public boolean removed = true;
	
	public GravityWell(Vec2 pos, double mass) {
		set(pos, mass);
	}
	
	public void set(Vec2 pos, double mass) {
		this.pos.setTo(pos);
		this.mass = mass;
		removed = false;
	}
	
	public void setMass(double mass) {
		this.mass = mass;
		//TODO set bitmap
	}
}

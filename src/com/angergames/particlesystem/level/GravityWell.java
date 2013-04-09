package com.angergames.particlesystem.level;

import com.angergames.particlesystem.gfx.Bitmap;
import com.angergames.particlesystem.gfx.Colors;
import com.angergames.particlesystem.util.math.Vec2;

public class GravityWell {
	
	public Vec2 pos = new Vec2();
	public double mass;
	
	public boolean removed = true;
	
	private Bitmap img;
	private Vec2 center;
	
	public GravityWell(Vec2 pos, double mass) {
		set(pos, mass);
	}
	
	public void set(Vec2 pos, double mass) {
		this.pos.setTo(pos);
		setMass(mass);
	}
	
	public void setMass(double mass) {
		this.mass = mass;
	}
	
	public void updateImage() {
		img = new Bitmap((int) mass + 50, (int) mass + 50);
		center = new Vec2(img.w / 2, img.h / 2);
		
		for(int x = 0; x < img.w; x++) {
			for(int y = 0; y < img.h; y++) {
				double dist = center.distanceTo(x, y);
				
				img.pixels[(int)(x + y * img.w)] = Colors.blend(0, 0xFF220099, dist / mass);
			}
		}
	}
	
	public void renderTo(Bitmap screen) {
		screen.render(img, pos.x - img.w / 2, pos.y - img.h / 2);
	}
}

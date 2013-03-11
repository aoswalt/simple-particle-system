package com.angergames.particlesystem.particles;

import com.angergames.particlesystem.gfx.Bitmap;
import com.angergames.particlesystem.gfx.Colors;
import com.angergames.particlesystem.level.Map;
import com.angergames.particlesystem.util.input.Keys;
import com.angergames.particlesystem.util.input.Mouse;
import com.angergames.particlesystem.util.math.Vec2;

public class ParticleSystem {
	
	private static final int PARTICLE_COUNT = 1000;
	
	private int width;
	private int height;
	
	public Bitmap screen;
	private Map map;
	
	private Particle[] particles = new Particle[PARTICLE_COUNT];
	
	private Vec2 spawner;
	private Vec2 well;
	private Vec2 wellDir = new Vec2();
	private double wellMass = 75;
	
	public ParticleSystem(int width, int height) {
		this.width = width;
		this.height = height;
		map = new Map(width, height);
		
		screen = new Bitmap(width, height);
		
		for(int i = 0; i < particles.length; i++) {
			particles[i] = new Particle(map);
		}
	}
	
	public void update(double delta) {
		if(Keys.g) {
			map.hasGravity = true;
		} else {
			map.hasGravity = false;
		}
		
		if(Keys.s && Mouse.pressed) {
			if(spawner == null) {
				spawner = new Vec2(Mouse.pos);
			} else {
				spawner.setTo(Mouse.pos);
			}
		}
		
		if(Keys.w && Mouse.pressed) {
			if(well == null) {
				well = new Vec2(Mouse.pos);
			} else {
				well.setTo(Mouse.pos);
			}
		}
		
		if((Mouse.pressed || spawner != null) & !Keys.w) {
			int i = 0;
			for(Particle p : particles) {
				if(i == 10) break;
				
				if(!p.isActive()) {
					if(spawner == null) {
						p.create(Mouse.pos);
					} else {
						p.create(spawner);
					}
					p.pos.add(Math.random() - 0.5, Math.random() - 0.5);
					i++;
				}
			}
		}
		
		for(Particle p : particles) {
			if(p.isActive()) {
				if(well != null) {
					double dist = p.pos.distanceTo(well);
					wellDir.setTo(well).subtract(p.pos).normalize();
					// F = G * m1 * m2 / dist * dist
					wellDir.scale(wellMass / dist);
					
					double dx = p.pos.x - well.x;
					double dy = p.pos.y - well.y;
					double angle = Math.atan2(dy, dx);
					//System.out.println(Math.toDegrees(angle));
					dist = Math.sqrt(dx * dx + dy * dy);
					double f = wellMass / (dist * dist);
					
					wellDir.setTo(f * Math.sin(angle), f * Math.cos(angle));
				}
				
				p.acc.setTo(wellDir);
				
				p.update(delta);
			}
		}
	}
	
	public void render() {
		screen.clear();
		renderLevel();
		renderParticles();
		renderLighting();
	}
	
	private void renderLevel() {
		Vec2 dist = new Vec2();
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				if(map.isActiveTile(x, y)) {
					screen.pixels[x + y * width] = Map.TILE_COLOR;
				} else if(well != null){
					dist.setTo(well).subtract(x, y);
					if(dist.length <= wellMass) {
						screen.pixels[x + y * width] = Colors.blend(0, 0x220099, dist.length / wellMass);
					}
				}
			}
		}
	}
	
	private void renderParticles() {
		for(Particle p : particles) {
			if(p.isActive()) {
				p.renderTo(screen);
			}
		}
	}
	
	private void renderLighting() {
		
	}
}

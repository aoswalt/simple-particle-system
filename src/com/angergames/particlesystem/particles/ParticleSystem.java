package com.angergames.particlesystem.particles;

import com.angergames.particlesystem.gfx.Bitmap;
import com.angergames.particlesystem.level.Level;
import com.angergames.particlesystem.util.input.Mouse;

public class ParticleSystem {
	
	private static final int PARTICLE_COUNT = 1000;
	
	private int width;
	private int height;
	
	public Bitmap screen;
	private Level level;
	
	private Particle[] particles = new Particle[PARTICLE_COUNT];
	
	public ParticleSystem(int width, int height) {
		this.width = width;
		this.height = height;
		level = new Level(width, height);
		
		screen = new Bitmap(width, height);
		
		for(int i = 0; i < particles.length; i++) {
			particles[i] = new Particle();
		}
	}
	
	public void update(double delta) {
		if(Mouse.pressed) {
			for(Particle p : particles) {
				if(!p.isActive()) {
					p.create(Mouse.pos);
					break;
				}
			}
		}
		
		for(Particle p : particles) {
			if(p.isActive()) {
				p.update(delta);
			}
		}
	}
	
	public void render() {
		screen.clear();
		renderLevel();
		renderParticles();
	}
	
	private void renderLevel() {
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				if(level.isActiveTile(x, y)) {
					screen.pixels[x + y * width] = Level.TILE_COLOR;
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
}

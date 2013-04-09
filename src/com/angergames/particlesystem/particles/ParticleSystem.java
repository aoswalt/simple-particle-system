package com.angergames.particlesystem.particles;

import java.util.ArrayList;

import com.angergames.particlesystem.gfx.Bitmap;
import com.angergames.particlesystem.gfx.Colors;
import com.angergames.particlesystem.level.GravityWell;
import com.angergames.particlesystem.level.Map;
import com.angergames.particlesystem.util.input.Keys;
import com.angergames.particlesystem.util.input.Mouse;
import com.angergames.particlesystem.util.math.Math2;
import com.angergames.particlesystem.util.math.Vec2;

public class ParticleSystem {
	
	private static final int PARTICLE_COUNT = 1000;
	
	private int width;
	private int height;
	
	public Bitmap screen;
	private Map map;
	
	private Particle[] particles = new Particle[PARTICLE_COUNT];
	private ArrayList<GravityWell> wells = new ArrayList<GravityWell>();
	
	private Vec2 spawner;
	private Vec2 wellDir = new Vec2();
	private double wellMass = 0;
	//private double springConst = 0.005;
	private double gravityConst = 25;
	
	private boolean canPlaceGravityWell = true;
	private boolean settingMass = false;
	private GravityWell settingWell;
	
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
		handleInput();
		
		/*
		// clear collision tree and re-add all particles 
		map.clearCollisionTree();
		for(Particle p : particles) {
			if(p.isActive()) map.insertIntoTree(p);
		}
		*/
		
		for(Particle p : particles) {
			if(p.isActive()) {
				if(!wells.isEmpty()) {
					for(GravityWell w : wells) {
						if(!w.removed) {
							double dist = p.pos.distanceTo(w.pos);
							wellDir.add(w.pos).subtract(p.pos).normalize();
							/*
							if(dist > wellMass * 4) {
								wellDir.scale(gravityConst * wellMass / (dist * dist));
							} else {
								wellDir.scale(springConst * (dist - wellMass));
							}
							*/
							double gravAcc = gravityConst * w.mass / (dist * dist);
							gravAcc = Math2.clamp(0.025, 0.25, gravAcc);
							wellDir.scale(gravAcc);
							
							p.acc.add(wellDir);
						}
					}
				}
				
				p.update(delta);
			}
		}
	}
	
	private void handleInput() {
		if(Keys.g) {
			map.hasGravity = true;
		} else {
			map.hasGravity = false;
		}
		
		if(Keys.s && Mouse.leftPressed) {
			if(spawner == null) {
				spawner = new Vec2(Mouse.pos);
			} else {
				spawner.setTo(Mouse.pos);
			}
		}
		
		if(Keys.s && Mouse.rightPressed) {
			spawner = null;
		}
		
		if(Keys.w && Mouse.leftPressed && canPlaceGravityWell && !settingMass) {
			if(wells.isEmpty()) {
				settingWell = new GravityWell(Mouse.pos, wellMass);
				wells.add(settingWell);
			} else {
				boolean add = true;
				for(GravityWell w : wells) {
					if(w.removed) {
						w.set(Mouse.pos, wellMass);
						settingWell = w;
						add = false;
						break;
					}
				}
				if(add) {
					settingWell = new GravityWell(Mouse.pos, wellMass);
					wells.add(settingWell);
				}
			}
			canPlaceGravityWell = false;
			settingMass = true;
		} else if((!Keys.w || !Mouse.leftPressed) && settingMass){
			canPlaceGravityWell = true;
			settingMass = false;
			settingWell.updateImage();
			settingWell.removed = false;
			settingWell = null;
		}
		
		if(Keys.w && Mouse.leftPressed && !canPlaceGravityWell && settingMass) {
			settingWell.setMass(settingWell.pos.distanceTo(Mouse.pos));
		}
		
		if(Keys.w && Mouse.rightPressed) {
			double distanceLimitMod = 2;
			GravityWell closest = null;
			double closestDist = -1;
			if(!wells.isEmpty()) {
				for(GravityWell w : wells) {
					double distanceTo = Mouse.pos.distanceTo(w.pos);
					if(distanceTo <= w.mass / distanceLimitMod) {
						if(closestDist == -1) {
							closestDist = distanceTo;
							closest = w;
						} else if(distanceTo < closestDist) {
							closestDist = distanceTo;
							closest = w;
						}
					}
				}
				if(closest != null) {
					closest.removed = true;
				}
			}
		}
		
		if(Keys.m && Mouse.leftPressed) {
			map.setTile(Mouse.pos);
		}
		
		if(Keys.m && Mouse.rightPressed) {
			map.clearTile(Mouse.pos);
		}
		
		//create particles if a spawner is there or mouse is held down without keys
		if((spawner != null) || (Mouse.leftPressed && !Keys.w && !Keys.m)) {
			int i = 0;
			for(Particle p : particles) {
				if(i == 10) break;
				
				if(!p.isActive()) {
					if(spawner == null) {
						p.create(Mouse.pos);
						//p.create(Mouse.pos.add(Math.random() - 0.5, Math.random() - 0.5));
						//p.create(Mouse.pos.add(4 * (Math.random() - 0.5), 4 * (Math.random() - 0.5)));
					} else {
						p.create(spawner);
					}
					p.pos.add(Math.random() - 0.5, Math.random() - 0.5);
					i++;
				}
			}
		}
	}
	
	public void render() {
		screen.clear();
		renderMap();
		renderParticles();
	}
	
	private void renderMap() {
		for(GravityWell w : wells) {
			if(!w.removed) {
				w.renderTo(screen);
			}
		}
		
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				if(settingWell != null) {
					double dist = settingWell.pos.distanceTo(x, y);
					//only override the pixels within range
					if(dist <= settingWell.mass) {
						screen.pixels[x + y * width] = Colors.blend(0, 0x220099, dist / settingWell.mass);
					}
				}
				
				if(map.isActiveTile(x, y)) {
					screen.pixels[x + y * width] = Map.TILE_COLOR;
				}
				
				if(Keys.m) {
					if(x % Map.TILE_SIZE == 0 || y % Map.TILE_SIZE == 0) {
						screen.pixels[x + y * width] = 0xFF006600;
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
}

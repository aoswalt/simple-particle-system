package com.angergames.particlesystem.util;

import java.util.ArrayList;

import com.angergames.particlesystem.level.Map;
import com.angergames.particlesystem.particles.Particle;

public class CollisionGrid {
	private final int gridCount = 100;
	
	private double cellWidth;
	private double cellHeight;
	
	private Cell[] grid;
	
	public CollisionGrid(Map map) {
		this.cellWidth = (double)(map.width) / gridCount;
		this.cellHeight = (double)(map.height) / gridCount;
		grid = new Cell[(gridCount + 1) * (gridCount + 1)];
		for(int i = 0; i < grid.length; i++) {
			grid[i] = new Cell();
		}
	}
	
	public ArrayList<Particle> getParticles(double x, double y) {
		int i = (int)(x / cellWidth + y / cellHeight * gridCount);
		return grid[i].particles;
	}
	
	public boolean isBlocked(double x, double y) {
		for(Particle p : getParticles(x, y)) {
			if(p.pos.x == x && p.pos.y == y && p.isActive()) {
				return true;
			}
		}
		return false;
	}
	
	public void insert(Particle p) {
		getParticles(p.pos.x, p.pos.y).add(p);
	}
	
	public void clear() {
		for(int i = 0; i < grid.length; i++) {
			grid[i].particles.clear();
		}
	}
	
	private class Cell {
		private ArrayList<Particle> particles = new ArrayList<Particle>();
	}
}

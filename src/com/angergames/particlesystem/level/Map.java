package com.angergames.particlesystem.level;

import com.angergames.particlesystem.particles.Particle;
import com.angergames.particlesystem.util.CollisionGrid;
import com.angergames.particlesystem.util.math.Vec2;

public class Map {
	
	public static final int TILE_SIZE = 16;
	public static final int TILE_COLOR = 0xBBBBBB;
	
	public boolean hasGravity = false;
	public Vec2 gravity = new Vec2(0, 0.1);
	
	public int width;
	public int height;
	
	private int rows;
	private int cols;
	
	private boolean[][] tiles;
	private CollisionGrid grid;
	
	public Map(int width, int height) {
		this.width = width;
		this.height = height;
		this.rows = height / TILE_SIZE;
		this.cols = width / TILE_SIZE;
		grid = new CollisionGrid(this);
		
		tiles = new boolean[rows + 1][cols + 1];	//handles overflow
		tiles[5][5] = true;
	}
	
	public void toggleTile(Vec2 v) {
		toggleTile(v.x, v.y);
	}
	
	public void toggleTile(double x, double y) {
		tiles[(int) (y / TILE_SIZE)][(int) (x / TILE_SIZE)] = !tiles[(int) (y / TILE_SIZE)][(int) (x / TILE_SIZE)];
	}
	
	public boolean isActiveTile(double x, double y) {
		return tiles[(int) (y / TILE_SIZE)][(int) (x / TILE_SIZE)];
	}
	
	public boolean isBlocked(double x, double y) {
		if(x < 0 || x >= width || y < 0 || y >= height) return true;
		if(isActiveTile(x, y)) return true;
		if(grid.isBlocked(x, y)) return true;
		return false;
	}
	
	public void insertIntoGrid(Particle p) {
		grid.insert(p);
	}
	
	public void clearGrid() {
		grid.clear();
	}
}

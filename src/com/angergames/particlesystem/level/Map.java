package com.angergames.particlesystem.level;

import com.angergames.particlesystem.util.math.Vec2;

public class Map {
	
	public static final int TILE_SIZE = 32;
	public static final int TILE_COLOR = 0xBBBBBB;
	
	public boolean hasGravity = false;
	public Vec2 gravity = new Vec2(0, 0.1);
	
	public int width;
	public int height;
	
	private boolean[] tiles;
	
	public Map(int width, int height) {
		this.width = width;
		this.height = height;
		
		tiles = new boolean[this.width / TILE_SIZE * this.height / TILE_SIZE];
	}
	
	public void toggleTile(double x, double y) {
		int index = (int) ((x / TILE_SIZE) + (y / TILE_SIZE) * width);
		if(index >= tiles.length) return;
		
		tiles[index] = !tiles[index];
	}
	
	public boolean isActiveTile(double x, double y) {
		int index = (int) ((x / TILE_SIZE) + (y / TILE_SIZE) * width);
		
		return index < tiles.length ? tiles[index] : false;
	}
	
	public boolean isSolid(double x, double y) {
		if(x < 0 || x >= width || y < 0 || y >= height) return true;
		return isActiveTile(x, y);
	}
}

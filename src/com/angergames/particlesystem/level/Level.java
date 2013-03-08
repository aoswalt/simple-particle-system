package com.angergames.particlesystem.level;

public class Level {
	
	public static final int TILE_SIZE = 32;
	public static final int TILE_COLOR = 0xBBBBBB;
	
	private int width;
	private int height;
	
	private boolean[] tiles;
	
	public Level(int width, int height) {
		this.width = width / TILE_SIZE;
		this.height = height / TILE_SIZE;
		
		tiles = new boolean[this.width * this.height];
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
}

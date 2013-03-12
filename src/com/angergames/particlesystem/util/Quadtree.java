package com.angergames.particlesystem.util;

import java.util.ArrayList;
import java.util.HashMap;

import com.angergames.particlesystem.level.Map;
import com.angergames.particlesystem.particles.Particle;
import com.angergames.particlesystem.util.math.Vec2;

public class Quadtree {
	private enum Cell {NW, NE, SE, SW};
	private final int MAX_DEPTH = 5;
	
	private Map map;
	private Node root;
	private ArrayList<Node> occupiedNodes = new ArrayList<Node>();
	
	public Quadtree(Map map) {
		this.map = map;
		root = new Node(new Vec2(map.width / 2, map.height / 2), map.width, map.height);
	}
	
	public void insert(Particle p) {
		root.insert(p);
	}
	
	public void clear() {
		root.clear();
		occupiedNodes.clear();
	}
	
	public ArrayList<Node> getOccupiedNodes() {
		return occupiedNodes;
	}
	
	private class Node {
		private HashMap<Cell, Node> nodes = new HashMap<Cell, Node>();
		private ArrayList<Particle> particles = new ArrayList<Particle>();
		
		private int depth;
		private Vec2 center;
		private double halfWidth;
		private double halfHeight;
		
		private Node(Vec2 center, double width, double height) {
			this.center = center;
			this.halfWidth = width / 2;
			this.halfHeight = height / 2;
		}
		
		private void insert(Particle p) {
			
		}
		
		private void clear() {
			
		}
		
		private boolean contains(Particle p) {
			return  p.pos.x >= center.x - halfWidth &&
					p.pos.x <= center.x + halfWidth &&
					p.pos.y >= center.y - halfWidth && 
					p.pos.y <= center.y + halfWidth;
		}
		
		private void subdivide() {
			double cx = center.x;
			double cy = center.y;
			double newWidth = halfWidth / 2;
			double newHeight = halfHeight / 2;

			nodes.put(Cell.NW, new Node(new Vec2(cx - newWidth, cy - newHeight), halfWidth, halfHeight));
			nodes.put(Cell.NE, new Node(new Vec2(cx + newWidth, cy - newHeight), halfWidth, halfHeight));
			nodes.put(Cell.SE, new Node(new Vec2(cx + newWidth, cy + newHeight), halfWidth, halfHeight));
			nodes.put(Cell.SW, new Node(new Vec2(cx - newWidth, cy + newHeight), halfWidth, halfHeight));
		}
	}
}

package com.angergames.particlesystem.util;

import java.util.ArrayList;
import java.util.List;

import com.angergames.particlesystem.level.Map;
import com.angergames.particlesystem.particles.Particle;
import com.angergames.particlesystem.util.math.Vec2;

public class Quadtree {
	private final int MAX_OBJECTS = 25;
	private final int MAX_DEPTH = 7;
	
	//private Map map;
	private Node root;
	private List<Particle> returnObjects = new ArrayList<Particle>();
	
	public Quadtree(Map map) {
		//this.map = map;
		root = new Node(new Vec2(map.width / 2, map.height / 2), map.width, map.height, 0);
	}
	
	public void insert(Particle p) {
		root.insert(p);
	}
	
	public List<Particle> retrieveCollidable(Particle p) {
		returnObjects.clear();
		return root.retrieve(returnObjects, p);
	}
	
	public void clear() {
		root.clear();
	}
	
	private class Node {
		private Node[] nodes = new Node[4];
		private ArrayList<Particle> particles = new ArrayList<Particle>();
		
		private int depth;
		private Vec2 center;
		private double halfWidth;
		private double halfHeight;
		
		private Node(Vec2 center, double width, double height, int depth) {
			this.center = center;
			this.halfWidth = width / 2;
			this.halfHeight = height / 2;
			this.depth = depth;
		}
		
		private void insert(Particle p) {
			if(nodes[0] != null) {
				nodes[getIndex(p)].insert(p);
				return;
			}
			
			particles.add(p);

			if(particles.size() > MAX_OBJECTS && depth < MAX_DEPTH) {
				if(nodes[0] == null) {
					subdivide();
				}
				
				int i = 0;
				while(i < particles.size()) {
					nodes[getIndex(particles.get(i))].insert(particles.remove(i));
				}
			}
		}
		
		public List<Particle> retrieve(List<Particle> returnObjects, Particle p) {
			if(nodes[0] != null) {
				nodes[getIndex(p)].retrieve(returnObjects, p);
			}
			
			returnObjects.addAll(particles);
			
			return returnObjects;
		}
		
		private int getIndex(Particle p) {
			// return index of particle NW 0, NE 1, SE 2, SW 3
			boolean top;
			boolean left;
			if(p.pos.y < center.y) {
				top = true;
			} else {
				top = false;
			}

			if(p.pos.x < center.x) {
				left = true;
			} else {
				left = false;
			}
			
			if(top) {
				if(left) return 0;
				return 1;
			} else {
				if(left) return 3;
				return 2;
			}
		}
		
		private void clear() {
			particles.clear();
			
			for(int i = 0; i < nodes.length; i++) {
				if(nodes[i] != null) {
					nodes[i].clear();
					nodes[i] = null;
				}
			}
		}
		
		private void subdivide() {
			double cx = center.x;
			double cy = center.y;
			double newWidth = halfWidth / 2;
			double newHeight = halfHeight / 2;
			
			nodes[0] = new Node(new Vec2(cx - newWidth, cy - newHeight), halfWidth, halfHeight, depth + 1);
			nodes[1] = new Node(new Vec2(cx + newWidth, cy - newHeight), halfWidth, halfHeight, depth + 1);
			nodes[2] = new Node(new Vec2(cx + newWidth, cy + newHeight), halfWidth, halfHeight, depth + 1);
			nodes[3] = new Node(new Vec2(cx - newWidth, cy + newHeight), halfWidth, halfHeight, depth + 1);
		}
	}
}

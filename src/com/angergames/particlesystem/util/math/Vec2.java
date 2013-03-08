package com.angergames.particlesystem.util.math;

public class Vec2 {
	public double x;
	public double y;
	public double length;
	
	public Vec2() {
		this(0, 0);
	}
	
	public Vec2(double x, double y) {
		set(x, y);
	}
	
	public Vec2(Vec2 v) {
		this(v.x, v.y);
	}
	
	public Vec2 set(double x, double y) {
		this.x = x;
		this.y = y;
		updateLength();
		return this;
	}
	
	public Vec2 set(Vec2 v) {
		return set(v.x, v.y);
	}
	
	private void updateLength() {
		length = Math.sqrt(x * x + y * y);
	}
	
	public String toString() {
		return "[" + x + ", " + y + "  len: " + length + "]";
	}
	
	public Vec2 add(Vec2 v) {
		return add(v.x, v.y);
	}
	
	public Vec2 add(double x, double y) {
		this.x += x;
		this.y += y;
		updateLength();
		return this;
	}
	
	public Vec2 subtract(Vec2 v) {
		return subtract(v.x, v.y);
	}
	
	public Vec2 subtract(double x, double y) {
		this.x -= x;
		this.y -= y;
		updateLength();
		return this;
	}
	
	public Vec2 scale(double s) {
		this.x *= s;
		this.y *= s;
		updateLength();
		return this;
	}
	
	public Vec2 normalize() {
		x /= length;
		y /= length;
		updateLength();
		return this;
	}
	
	public double distanceTo(Vec2 v) {
        //sqrt((xDiff)² + (yDiff)²)
		double xd = v.x - x;
		double yd = v.y - y;
		return Math.sqrt(xd * xd + yd * yd);
	}
	
	public double dot(Vec2 v) {
		return x * v.x + y * v.y;
	}
	
	public Vec2 reflectAcrossNormal(Vec2 v) {
		Vec2 n = (v.length == 1) ? v : new Vec2(v).normalize();
		
		// I' = I-2 * dot(N,I) * N;
		return this.subtract(n.scale(2 * n.dot(this)));
	}
}

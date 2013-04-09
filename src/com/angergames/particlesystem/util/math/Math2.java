package com.angergames.particlesystem.util.math;

/**
 * Math2.java
 * Purpose: Provide access to generic math functions.
 * 
 * @author Adam Oswalt
 */
public class Math2 {
	
	public static double clamp(double min, double max, double input) {
		if(input < min) return min;
		if(input > max) return max;
		return input;
	}
}

package com.angergames.particlesystem;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.angergames.particlesystem.particles.ParticleSystem;
import com.angergames.particlesystem.util.input.Keys;
import com.angergames.particlesystem.util.input.Mouse;

/**
 * Demo.java
 * Purpose: Entry point and program loop for the Particle System.
 * 
 * @author Adam Oswalt
 */
public class Demo extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final double SCALE = 2;
	private static final String TITLE = "Particle System";
	
	private static JFrame frame;
	
	private BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int pixels[] = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
	
	private boolean running = false;
	private Thread thread;
	
	private ParticleSystem particles = new ParticleSystem(WIDTH, HEIGHT);
	
	public void init() {
		this.addMouseListener(new Mouse(this));
		this.addKeyListener(new Keys());
		this.setFocusTraversalKeysEnabled(false);
		this.requestFocus();
	}
	
	public void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		long currentTime;
		long lastTime = System.nanoTime();
		double nsPerLoop = 1000000000 / 60.0;
		double unprocessed = 0;
		long fpsTime = System.currentTimeMillis();
		int frames = 0;
		
		init();
		
		while(running) {
			currentTime = System.nanoTime();
			unprocessed += (currentTime - lastTime) / nsPerLoop;
			lastTime = currentTime;
			
			while(unprocessed >= 1) {
				Mouse.update();
				particles.update(unprocessed);
				unprocessed--;
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) { e.printStackTrace(); }
			
			render();
			frames++;
			
			if(System.currentTimeMillis() - fpsTime >= 1000) {
				frame.setTitle(TITLE + "  |  " + frames + " fps");
				fpsTime += 1000;
				frames = 0;
			}
		}
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		particles.render();
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = particles.screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, (int)(WIDTH * SCALE), (int)(HEIGHT * SCALE), null);
		if(Keys.h) {
			drawHelpText(g);
		}
		
		g.dispose();
		bs.show();
	}

	private void drawHelpText(Graphics g) {
		int left = (int)((WIDTH - 125) * SCALE);
		int top = 15;
		int lineSpace = 15;
		
		g.setColor(Color.white);
		g.drawString("Help ", left, top);
		g.drawString("  g: hold for gravity", left, top += lineSpace);
		g.drawString("  s: spawner", left, top += lineSpace);
		g.drawString("    leftMouse: set spawner", left, top += lineSpace);
		g.drawString("    rightMouse: remove spawner", left, top += lineSpace);
		g.drawString("  w: gravity well", left, top += lineSpace);
		g.drawString("    leftMouse: add gravity well, drag to set size", left, top += lineSpace);
		g.drawString("    rightMouse: remove gravity well", left, top += lineSpace);
		g.drawString("  m: map tiles", left, top += lineSpace);
		g.drawString("    leftMouse: add wall tile", left, top += lineSpace);
		g.drawString("    rightMouse: remove wall tile", left, top += lineSpace);
	}

	public static void main(String[] args) {
		Demo ps = new Demo();
		
		frame = new JFrame(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setPreferredSize(new Dimension((int)(WIDTH * SCALE), (int)(HEIGHT * SCALE)));
		frame.setResizable(false);
		frame.add(ps);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		ps.start();
	}
	
}

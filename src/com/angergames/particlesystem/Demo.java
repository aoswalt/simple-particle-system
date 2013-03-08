package com.angergames.particlesystem;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.angergames.particlesystem.particles.ParticleSystem;
import com.angergames.particlesystem.util.input.Mouse;

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
		//add listeners
		this.addMouseListener(new Mouse(this));
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
		
		g.dispose();
		bs.show();
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

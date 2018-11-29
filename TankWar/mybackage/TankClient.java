package mybackage;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class TankClient extends Frame {
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 500;
	private Image offScreenImage = null;
	ArrayList<Missile> missiles  = new ArrayList<Missile>();
	ArrayList<Explode> explodes  = new ArrayList<Explode>();
	ArrayList<Tank> tanks  = new ArrayList<Tank>();
	
	Tank t = new Tank(50,400,true,Tank.Direction.STOP, this);
	
	public static void main(String[] args) {
		new TankClient().launch();
	}
	
	public void launch(){
		this.setLocation(300, 150);
		this.setSize(WIDTH, HEIGHT);
		this.setBackground(Color.GRAY);
		this.setTitle("Ì¹¿Ë´óÕ½");
		this.setVisible(true);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter(){
			
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.addKeyListener(new KeyMonitor());
		new Thread(new PaintThread()).start();
		
		for(int i=0;i<6;i++){
			tanks.add(new Tank(110*(i+1),80,false,Tank.Direction.D, this));
		}
	}
	
	@Override
	public void paint(Graphics g) {
		t.draw(g);
		
		for(int i=0;i<missiles.size();i++){
			Missile m = missiles.get(i);
			m.hitTanks(tanks);
			m.hitTank(t);
			m.draw(g);
		}
		
		for(int i=0;i<explodes.size();i++){
			Explode e = explodes.get(i);
			e.draw(g);
		}
		
		for(int i=0;i<tanks.size();i++){
			Tank t = tanks.get(i);
			t.draw(g);
		}
		
	}
	
	@Override
	public void update(Graphics g) {
		if(offScreenImage == null){
			offScreenImage = this.createImage(WIDTH, HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.GRAY);
		gOffScreen.fillRect(0, 0, WIDTH, HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	private class PaintThread implements Runnable{

		@Override
		public void run() {
			while(true){
				repaint();
				
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private class KeyMonitor extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			t.KeyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			t.keyReleased(e);
		}

		
	}

}

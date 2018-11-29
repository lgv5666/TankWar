package mybackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import mybackage.Tank.Direction;

public class Missile {
	
	private int x;
	private int y;
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	private Tank.Direction dir;
	private TankClient tc;
	private boolean good;
	
	public Missile(int x, int y, Direction dir, boolean good, TankClient tc) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.tc = tc;
		this.good = good;
	}

	public void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		
		move();
	}
	
	private void move(){
		switch(dir) {
		case L:
			x -= 12;
			break;
		case LU:
			x -= 7;
			y -= 7;
			break;
		case U:
			y -= 12;
			break;
		case RU:
			x += 7;
			y -= 7;
			break;
		case R:
			x += 12;
			break;
		case RD:
			x += 7;
			y += 7;
			break;
		case D:
			y += 12;
			break;
		case LD:
			x -= 7;
			y += 7;
			break;
		}
		
		if(x<0 || y<0 || x>TankClient.WIDTH || y>TankClient.HEIGHT){
			tc.missiles.remove(this);
		}
			
	}
	
	public boolean hitTank(Tank t){
		if(this.getRect().intersects(t.getRect()) && t.isLive() && good!=t.isGood()){
			t.setLive(false);
			tc.missiles.remove(this);
			tc.explodes.add(new Explode(x,y,tc));
			return true;
		}
		return false;
	}
	
	public boolean hitTanks(ArrayList<Tank> tanks) {
		for(int i=0; i<tanks.size(); i++) {
			if(hitTank(tanks.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	public Rectangle getRect(){
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}

}

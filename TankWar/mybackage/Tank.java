package mybackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;


public class Tank {
	private int x;
	private int y;
	private boolean good;
	private boolean live = true;
	public static final int WIDTH = 50;
	public static final int HEIGHT = 50;
	
	private static Random r = new Random();

	private TankClient tc;
	private boolean bL=false, bU=false, bD=false, bR=false;
	 enum Direction{L, LU, U, RU, D, LD, RD, R, STOP};
	private Direction dir = Direction.STOP;
	private Direction ptDir = Direction.D;
	private int step = r.nextInt(15)+5;
	public Tank(int x, int y, boolean good, Direction dir, TankClient tc) {
		super();
		this.x = x;
		this.y = y;
		this.good = good;
		this.tc = tc;
		this.dir = dir;
	}
	
	public void draw(Graphics g){
		
		if(!live){
			if(!good){
				tc.tanks.remove(this);
			}
			return;
		}
		
		Color c = g.getColor();
		
		if (good) g.setColor(Color.GREEN);
		else  g.setColor(Color.BLUE);
		
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		
		switch(ptDir){
		case L:
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x, y+HEIGHT/2);
			break;
		case LU:
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x, y);
			break;
		case U:
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x+WIDTH/2, y);
			break;
		case RU:
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x+WIDTH, y);
			break;
		case R:
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x+WIDTH, y+HEIGHT/2);
			break;
		case RD:
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x+WIDTH, y+HEIGHT);
			break;
		case D:
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x+WIDTH/2, y+HEIGHT);
			break;
		case LD:
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x, y+HEIGHT);
			break;
		}
		move();
		if(dir!=Direction.STOP){
			ptDir = dir;
		}
		
	}
	
	public void KeyPressed(KeyEvent e){
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W: bU=true; break;
		case KeyEvent.VK_S: bD=true; break;
		case KeyEvent.VK_A: bL=true; break;
		case KeyEvent.VK_D: bR=true; break;

		}
		locateDirection();
	}
	
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SPACE:fire(); break;
		case KeyEvent.VK_W: bU=false; break;
		case KeyEvent.VK_S: bD=false; break;
		case KeyEvent.VK_A: bL=false; break;
		case KeyEvent.VK_D: bR=false; break;

		}
		locateDirection();
	}
	
	private void move(){
		switch(dir) {
		case L:
			x -= 5;
			break;
		case LU:
			x -= 5;
			y -= 5;
			break;
		case U:
			y -= 5;
			break;
		case RU:
			x += 5;
			y -= 5;
			break;
		case R:
			x += 5;
			break;
		case RD:
			x += 5;
			y += 5;
			break;
		case D:
			y += 5;
			break;
		case LD:
			x -= 5;
			y += 5;
			break;
		case STOP:
			break;
		}
		
		if(x<0) x = 0;
		if(y<30) y = 30;
		if(x+WIDTH>TankClient.WIDTH) x = TankClient.WIDTH - WIDTH;
		if(y+HEIGHT>TankClient.HEIGHT) y = TankClient.HEIGHT - HEIGHT;
		
		if (!good) {
			if (step==0) {
				step = r.nextInt(15)+5;
				Direction[] dir = Direction.values();
				int num = r.nextInt(dir.length);
				this.dir = dir[num];
				if(r.nextInt(30)>15) fire();
			}
			step--;
			
			
		}
	}
	
	private void locateDirection() {
		if(bL && !bU && !bR && !bD) dir = Direction.L;
		else if(bL && bU && !bR && !bD) dir = Direction.LU;
		else if(!bL && bU && !bR && !bD) dir = Direction.U;
		else if(!bL && bU && bR && !bD) dir = Direction.RU;
		else if(!bL && !bU && bR && !bD) dir = Direction.R;
		else if(!bL && !bU && bR && bD) dir = Direction.RD;
		else if(!bL && !bU && !bR && bD) dir = Direction.D;
		else if(bL && !bU && !bR && bD) dir = Direction.LD;
		else if(!bL && !bU && !bR && !bD) dir = Direction.STOP;
		
	}
	
	private Missile fire(){
		if(!live) return null;
		int x = this.x + WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + HEIGHT/2 - Missile.HEIGHT/2;
		Missile m = new Missile(x,y,ptDir,good,tc);
		tc.missiles.add(m);
		return m;
	}
	
	public Rectangle getRect(){
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isLive() {
		return live;
	}

	public boolean isGood() {
		return good;
	}

}

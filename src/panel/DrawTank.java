package panel;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.*;

public class DrawTank extends JPanel implements KeyListener, Runnable {
	MyTank mt = null;
	EnmTk et = null;
	boolean pre_tank = true;//if the game object started at the beginning
	int tk_size = 50;
	int tk_speed = 10;
	static int kill_by_mt = 0;
	int input_space = 0;
	// int tk_locx, tk_locy;
	int border_x1, border_y1;
	int border_x2, border_y2;
	int extra_space;
	Vector<EnmTk> vtk;
	Vector<Bomb> bomb;
	Image bmb_im1 = null;
	Image bmb_im2 = null;
	Image bmb_im3 = null;
	Image steel_wall = null;
	Image my_tank = null;
	Image other_tank = null;
	Image grass_wall = null;
	Image my_home= null;
	int totank = 2;
	public DrawTank(int win_locx, int win_locy, int win_width, int win_height, int space) {
		this.border_x1 = 0;//win_locx
		this.border_y1 = 0;//win_locy
		this.border_x2 = win_width-9;//win_locx + 
		this.border_y2 = win_height-60;//win_locy + 
		this.extra_space = space;
		this.input_space = 0;
		mt = new MyTank(0, 0, this.border_y2-tk_size, tk_size, tk_speed, 1,4, this.border_x1,
				this.border_y1, this.border_x2, this.border_y2, true);
		//System.out.println("windows:"+win_locx+" "+win_locy+" "+win_locx
		// +" "+ win_width+" "+win_locy+" " + win_height);
		et = new EnmTk(0, 0, 0,
				tk_size, tk_speed, 4,this.totank, this.border_x1,
				this.border_y1, this.border_x2, this.border_y2, true);
		vtk = new Vector<EnmTk>();
		Random rand = new Random();
		Thread t_mv = null;
		for (int i = 0; i < totank; i++) {
			EnmTk emy_tk1 = new EnmTk(rand.nextInt(4), (i + 1) * 100, 0,
					tk_size, tk_speed, 4,this.totank, this.border_x1,
					this.border_y1, this.border_x2, this.border_y2, true);
			vtk.add(emy_tk1);
			t_mv = new Thread(emy_tk1);
			t_mv.start();
		}
		// initial the bombs
		bomb = new Vector<Bomb>();
		try {
			my_tank = Toolkit.getDefaultToolkit().getImage(
					Panel.class.getResource("/tank" + mt.type + ".png"));
			my_home = Toolkit.getDefaultToolkit().getImage(
					Panel.class.getResource("home.bmp"));
			other_tank = Toolkit.getDefaultToolkit().getImage(
					Panel.class.getResource("/tank" + et.type + ".png"));
			steel_wall = javax.imageio.ImageIO.read(DrawTank.class
					.getClassLoader().getResourceAsStream("wall.png"));
			grass_wall = javax.imageio.ImageIO.read(DrawTank.class
					.getClassLoader().getResourceAsStream("grass.jpg"));
			bmb_im1 = javax.imageio.ImageIO.read(DrawTank.class
					.getClassLoader().getResourceAsStream("bomb_1.gif"));
			// ImageIO.read(new File("bomb_1.gif"));
			bmb_im2 = javax.imageio.ImageIO.read(DrawTank.class
					.getClassLoader().getResourceAsStream("bomb_2.gif"));
			bmb_im3 = javax.imageio.ImageIO.read(DrawTank.class
					.getClassLoader().getResourceAsStream("bomb_3.gif"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// bmb_im1 = Toolkit.getDefaultToolkit().getImage(
		// Panel.class.getResource("/bomb_1.gif"));
		// bmb_im2 = Toolkit.getDefaultToolkit().getImage(
		// Panel.class.getResource("/bomb_2.gif"));
		// bmb_im3 = Toolkit.getDefaultToolkit().getImage(
		// Panel.class.getResource("/bomb_3.gif"));

	}

	public void paint(Graphics g) {

		super.paint(g);
		//draw the dividen string
		for (int i = 0; i <= this.border_y2; i += steel_wall.getHeight(null))
			g.drawImage(steel_wall, this.border_x2, i, steel_wall.getWidth(null), steel_wall.getHeight(null), this);
		
		//display the life of me and enemies
		//display the score of the player
		String scores = "1P成绩";
		g.setFont(new Font("宋体", Font.BOLD, 25));
		g.setColor(Color.gray);
		g.drawString(scores, this.border_x2+this.extra_space-110, 40);
		g.drawString(String.valueOf(this.kill_by_mt*10), 
					this.border_x2+this.extra_space-80, 80);
		//display the existed tank number of me and enemy
		g.drawImage(my_tank, this.border_x2+this.extra_space-115, 100, mt.size-10, mt.size-10, this);
		String my_life = String.valueOf(mt.num);
		g.setFont(new Font("宋体", Font.BOLD, 30));
		g.setColor(Color.red);
		g.drawString(my_life, this.border_x2+this.extra_space-60, 130);
		
		g.drawImage(other_tank, this.border_x2+this.extra_space-115, 110+(mt.size-10), et.size-10, et.size-10, this);
		String other_life = String.valueOf(et.num);
		//g.setFont(new Font("宋体", Font.BOLD, 32));
		g.setColor(Color.yellow);
		g.drawString(other_life,  this.border_x2+this.extra_space-60, 140+(mt.size-10));
		
		// paint the whole tanks added in
		drawTank(g, this.mt);
		for (int i = 0; i < vtk.size(); i++) {
			drawTank(g, this.vtk.get(i));
		}
		
		//draw my home
		//g.drawImage(my_home, 500, 500, 50, 50, this);
		
		
		//draw the grass
		int counter = 0;
		
		for (int i = 0; counter != 14; i += grass_wall.getWidth(null), counter ++){
			g.drawImage(grass_wall, i, this.mt.size, grass_wall.getWidth(null)+18, grass_wall.getHeight(null), this);
		}
		
		// paint the bullet
		// My bullet
		if (!(mt.blt_v.isEmpty())) {
			drawBullet(g, mt);
		}
		// enemy's bullet
		for (int i = 0; i < vtk.size(); i++) {
			if (!(vtk.get(i).blt_v.isEmpty())) {
				drawBullet(g, vtk.get(i));
			}
		}
		// paint the bomb
		for (int i = 0; i < bomb.size(); i++) {
			Bomb b = bomb.get(i);
			if (b.type > 6) {
				g.drawImage(bmb_im1, b.x, b.y, mt.size, mt.size, this);
			} else if (b.type > 3) {
				g.drawImage(bmb_im2, b.x, b.y, mt.size, mt.size, this);
			} else if (b.type > 0) {
				g.drawImage(bmb_im3, b.x, b.y, mt.size, mt.size, this);
			}
			b.lifeDown();
			if (b.type == 0)
				bomb.remove(b);
		}
	}

	boolean judge_bord(int x, int y) {
		return (x >= 0 && y >= 0 && x <= this.border_x2 && y <= this.border_y2);
	}

	public static BufferedImage Rotate(Image src, int angel) {
		int src_width = src.getWidth(null);
		int src_height = src.getHeight(null);
		// calculate the new image size
		// System.out.println("width1:"+src_width+" "+src_height);
		Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(
				src_width, src_height)), angel);

		BufferedImage res = null;
		res = new BufferedImage(rect_des.width, rect_des.height,
				BufferedImage.TYPE_INT_RGB);

		Graphics2D g2 = res.createGraphics();
		g2.translate((rect_des.width - src_width) / 2,
				(rect_des.height - src_height) / 2);
		g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);
		g2.drawImage(src, null, null);

		return res;
	}

	public static Rectangle CalcRotatedSize(Rectangle src, int angel) {
		// if angel is greater than 90 degree, we need to do some conversions
		if (angel >= 90) {
			if (angel / 90 % 2 == 1) {
				int temp = src.height;
				src.height = src.width;
				src.width = temp;
			}
			angel = angel % 90;
		}

		double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
		double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
		double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
		double angel_dalta_width = Math.atan((double) src.height / src.width);
		double angel_dalta_height = Math.atan((double) src.width / src.height);

		int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha
				- angel_dalta_width));
		int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha
				- angel_dalta_height));
		int des_width = src.width + len_dalta_width * 2;
		int des_height = src.height + len_dalta_height * 2;
		return new java.awt.Rectangle(new Dimension(des_width, des_height));
	}

	public double dist(int x1, int y1, int x2, int y2) {
		return Math.sqrt((Math.pow(1.0 * (x1 - x2), 2) + Math.pow(
				1.0 * (y1 - y2), 2)));
	}

	public void keyPressed(KeyEvent k) {
		// TODO Auto-generated method stub
		
		int gowhere = -1;
		boolean shoot = false;
		if (k.getKeyCode() == KeyEvent.VK_W)
			gowhere = 0;
		if (k.getKeyCode() == KeyEvent.VK_D)
			gowhere = 1;
		if (k.getKeyCode() == KeyEvent.VK_S)
			gowhere = 2;
		if (k.getKeyCode() == KeyEvent.VK_A)
			gowhere = 3;
		if (k.getKeyCode() == KeyEvent.VK_J)
			shoot = true;
		if (k.getKeyCode() == KeyEvent.VK_SPACE){
			ArrayList<Integer> tmp = new ArrayList<Integer>();
			//int mt_speed = this.mt.speed;
			//this.mt.speed = 0;
			
			int enm_speed = et.speed;
			for (int i = 0; i < this.vtk.size(); i ++){
				this.vtk.get(i).speed = 0;
				tmp.add(this.vtk.get(i).rotate);
			}
		}
		if (shoot) {
			shoot_enemy(mt);
		}
		moveTank(gowhere, this.mt);
		repaint();
	}
	
	public void moveTank(int check, Tank tk) //AI inside here
	{
		boolean can_go = true;
		if (check == 2) {
			tk.rotate = 2;
			// System.out.println("/t1"+tk.border_x1+" " + tk.border_y1+" " +
			// tk.border_x2+" " + tk.border_y2+".JPG");

			// System.out.println("/t2"+tk.tank.get(i).x+" "+tk.tank.get(i).size+" "+tk.tank.get(i).y+" "+tk.tank.get(i).size+" "+tk.tank.get(i).speed+".JPG");
			// what the fuck why there have to be added with 37 for y-axis?;+ 63
			if (tk.judge_bord(tk.x + tk.size, tk.y + tk.size + tk.speed )) {
				// System.out.println("/t1"+tk.border_x1+" " + tk.border_y1+" "
				// + tk.border_x2+" " + tk.border_y2+".JPG");
				
				for (int i = 0; i < this.vtk.size(); i ++){
					Tank tmp = this.vtk.get(i);
					if (tmp != tk){
						if (pIsin(tmp.x, tmp.y, tmp.x+tmp.size, tmp.y+tmp.size, tk.x+tk.size, tk.y+tk.size+tk.speed)
							|| pIsin(tmp.x, tmp.y, tmp.x+tmp.size, tmp.y+tmp.size, tk.x, tk.y+tk.size+tk.speed)
							){
							can_go = false;
							//tk.y += tk.speed;
						}
						
					}
				}
				if (can_go){
					if (pIsin(this.mt.x, this.mt.y, this.mt.x+this.mt.size, this.mt.y+this.mt.size, tk.x+tk.size, tk.y+tk.size+tk.speed)
							|| pIsin(this.mt.x, this.mt.y, this.mt.x+this.mt.size, this.mt.y+this.mt.size, tk.x, tk.y+tk.size+tk.speed)
							){
						
					}
					else 
						tk.y += tk.speed;
				}
				else {
//					int cg_dir = (int)Math.random()*2;
//					if (cg_dir == 1){
//						tk.rotate = (tk.rotate + 1);
//					}
//					else tk.rotate = (tk.rotate - 1);
					if (tk != this.mt)
						tk.rotate ++;
				}
				
			} else {
				//  - 63System.out.println("x1:"+tk.x+" "+tk.y+" "+tk.speed);
				tk.y = tk.border_y2 - tk.size;
				if (tk != this.mt)
					tk.rotate ++;
			}

		}
		if (check == 0) {
			tk.rotate = 0;
			if (tk.judge_bord(tk.x, tk.y - tk.speed)){
				for (int i = 0; i < this.vtk.size(); i ++){
					Tank tmp = this.vtk.get(i);
					if (tmp != tk){
						if (pIsin(tmp.x, tmp.y, tmp.x+tmp.size, tmp.y+tmp.size, tk.x, tk.y - tk.speed)
							|| pIsin(tmp.x, tmp.y, tmp.x+tmp.size, tmp.y+tmp.size, tk.x+tk.size, tk.y - tk.speed)
							){
							can_go = false;
						}
					}
				}
				if (can_go){
					if (pIsin(this.mt.x, this.mt.y, this.mt.x+this.mt.size, this.mt.y+this.mt.size, tk.x, tk.y - tk.speed)
							|| pIsin(this.mt.x, this.mt.y, this.mt.x+this.mt.size, this.mt.y+this.mt.size, tk.x+tk.size, tk.y - tk.speed)
							){
						
					}
					else 
						tk.y -= tk.speed;
				}
				else {
//					int cg_dir = (int)Math.random()*2;
//					if (cg_dir == 1){
//						tk.rotate = (tk.rotate + 1);
//					}
//					else tk.rotate = (tk.rotate - 1);
					if (tk != this.mt)
						tk.rotate ++;
				}
			}
			else {
				tk.y = tk.border_y1;
				if (tk != this.mt)
					tk.rotate ++;
			}
		}
		if (check == 3) {
			tk.rotate = 3;
			if (tk.judge_bord(tk.x - tk.speed, tk.y)){
				for (int i = 0; i < this.vtk.size(); i ++){
					Tank tmp = this.vtk.get(i);
					if (tmp != tk){
						if (pIsin(tmp.x, tmp.y, tmp.x+tmp.size, tmp.y+tmp.size, tk.x - tk.speed, tk.y)
							|| pIsin(tmp.x, tmp.y, tmp.x+tmp.size, tmp.y+tmp.size, tk.x - tk.speed, tk.y+tk.size)
							){
							can_go = false;
						}
					}
				}
				if (can_go){
					if (pIsin(this.mt.x, this.mt.y, this.mt.x+this.mt.size, this.mt.y+this.mt.size, tk.x - tk.speed, tk.y)
							|| pIsin(this.mt.x, this.mt.y, this.mt.x+this.mt.size, this.mt.y+this.mt.size, tk.x - tk.speed, tk.y+tk.size)
							){
						
					}
					else 
					tk.x -= tk.speed;
				}
				else {
//					int cg_dir = (int)Math.random()*2;
//					if (cg_dir == 1){
//						tk.rotate = (tk.rotate + 1);
//					}
//					else tk.rotate = (tk.rotate - 1);
					if (tk != this.mt)
						tk.rotate ++;
				}
			}
			else {
				tk.x = tk.border_x1;
				if (tk != this.mt)
					tk.rotate ++;
			}
		}
		if (check == 1) {
			tk.rotate = 1;//+ 16
			if (tk.judge_bord(tk.x + tk.size + tk.speed , tk.y + tk.size)){
				for (int i = 0; i < this.vtk.size(); i ++){
					EnmTk tmp = this.vtk.get(i);
					if (tmp != tk){
						if (pIsin(tmp.x, tmp.y, tmp.x+tmp.size, tmp.y+tmp.size, tk.x + tk.size + tk.speed, tk.y + tk.size)
							|| pIsin(tmp.x, tmp.y, tmp.x+tmp.size, tmp.y+tmp.size, tk.x + tk.size + tk.speed, tk.y)
							){
							can_go = false;
							
						}
					}
				}
				if (can_go){
					if (pIsin(this.mt.x, this.mt.y, this.mt.x+this.mt.size, this.mt.y+this.mt.size, tk.x + tk.size + tk.speed, tk.y + tk.size)
							|| pIsin(this.mt.x, this.mt.y, this.mt.x+this.mt.size, this.mt.y+this.mt.size, tk.x + tk.size + tk.speed, tk.y)
							){
						
					}
					else 
					tk.x += tk.speed;
				}
				else {
//					int cg_dir = (int)Math.random()*2;
//					if (cg_dir == 1){
//						tk.rotate = (tk.rotate + 1);
//					}
//					else tk.rotate = (tk.rotate - 1);
					if (tk != this.mt)
						tk.rotate ++;
				}
				
			}
				
			else {//- 16
				tk.x = tk.border_x2 - tk.size ;
				if (tk != this.mt)
					tk.rotate ++;
			}
			
		}
		//some AIs
		//
		if (tk != this.mt){
<<<<<<< HEAD
			Random rand = new Random();
			
			int jud = rand.nextInt(100);
			//System.out.println("jud:"+jud);
			if (this.mt.rotate != (tk.rotate+2)%4 && jud<50){
				if (this.mt.x < tk.x)tk.rotate = 3;
				else if (this.mt.x > tk.x)tk.rotate = 1;
				else if (this.mt.y < tk.y)tk.rotate = 0;
				else if (this.mt.y > tk.y)tk.rotate = 2;
=======
			Random rand = new Random(1000);
			int jud = rand.nextInt();
			if (this.mt.rotate != (tk.rotate+2)%4 && jud>=2&&jud<=5){
				if (this.mt.x < tk.x)tk.rotate = 3;
				if (this.mt.x > tk.x)tk.rotate = 1;
				if (this.mt.y < tk.y)tk.rotate = 0;
				if (this.mt.y > tk.y)tk.rotate = 2;
>>>>>>> 869d2af16fe06e1bbb7be0df1a4f8fd6d2ec8b8b
				
				
			}
			//while my bullet is facing an enemy's tank. it will change its direction to avoid being hitten
			for (int i = 0; i < mt.blt_v.size(); i ++){
				if ( (mt.blt_v.get(i).x >= (tk.x) && mt.blt_v.get(i).x <= (tk.x+tk.size))|| (mt.blt_v.get(i).y >= (tk.y) && mt.blt_v.get(i).y <= (tk.y+tk.size))){
					if (mt.blt_v.get(i).direction == (check+2)%4){ 
						tk.rotate ++;
						break;
					}
				}
			}
		}
		tk.rotate %= 4;
		repaint();
	}

	// draw a tank
	public void drawTank(Graphics g, Tank tk) {
		Image im = Toolkit.getDefaultToolkit().getImage(
				Panel.class.getResource("/tank" + tk.type + ".png"));;
		
		// System.out.println("width1:"+im.getWidth(null)+" "+im.getHeight(null));

		if (im.getWidth(null) != -1 && im.getHeight(null) != -1) {
			g.drawImage(im, tk.x, tk.y, tk.size, tk.size, this);
			BufferedImage des = Rotate(im, tk.rotate * 90);
			g.drawImage(des, tk.x, tk.y, tk.size, tk.size, this);
			// System.out.println("prex:"+ tk.x+" "+ tk.y);

		} else
			repaint();
	}

	// control the bullet
	public void shoot_enemy(Tank tk) {
		Bullet blt = new Bullet(tk.rotate, (tk.speed), 1, 15, tk.x, tk.y,
				this.border_x1, this.border_y1, this.border_x2, this.border_y2);
		switch (blt.direction) {
		case 0:
			blt.x += (tk.size / 2 - blt.size / 2);
			blt.y -= blt.size;
			break;
		case 1:
			blt.y += (tk.size / 2 - blt.size / 2);
			blt.x += (tk.size);
			break;
		case 2:
			blt.y += (tk.size);
			blt.x += (tk.size / 2 - blt.size / 2);
			break;
		case 3:
			blt.y += (tk.size / 2 - blt.size / 2);
			blt.x -= (blt.size);
			break;
		default:
		}
		repaint();
		// keep some distances between two bullets
		if (tk.blt_v.isEmpty()
				|| blt.direction != tk.blt_v.lastElement().direction
				|| (blt.direction == tk.blt_v.lastElement().direction && dist(
						blt.x, blt.y, tk.blt_v.lastElement().x, tk.blt_v
								.lastElement().y) >= 1000 / (1.0 * tk.speed))) {
			tk.blt_v.add(blt);
			Thread dtk_t = new Thread(blt);
			dtk_t.start();
		}
	}

	// draw the bullet
	public void drawBullet(Graphics g, Tank tk) {
		for (int i = 0; i < tk.blt_v.size(); i++) {

			// read the image;
			Image im = null;
			try {
				im = Toolkit.getDefaultToolkit().getImage(
						Panel.class.getResource("/bullet"
								+ tk.blt_v.get(i).type + ".png"));
			} catch (Exception e) {
				// TODO: handle exception
			}
			// draw it;
			if (!tk.blt_v.isEmpty()) {
				g.drawImage(im, tk.blt_v.get(i).x, tk.blt_v.get(i).y, tk.blt_v
						.get(i).size, tk.blt_v.get(i).size, this);
				if (!judge_bord(tk.blt_v.get(i).x+tk.blt_v.get(i).size, tk.blt_v.get(i).y+tk.blt_v.get(i).size)) {
//					if (tk.blt_v.get(i).x+tk.blt_v.get(i).speed >= this.border_x2){
//						Bomb bmb = new Bomb(tk.blt_v.get(i).x-bmb_im1.getWidth(null)/4, tk.blt_v.get(i).y-bmb_im1.getHeight(null)/2, true);
//						bomb.add(bmb);
//					}
					tk.blt_v.remove(i);
					
					continue;
				}
				if (im.getWidth(null) != -1 && im.getHeight(null) != -1) {

					g.drawImage(im, tk.blt_v.get(i).x, tk.blt_v.get(i).y,
							tk.blt_v.get(i).size, tk.blt_v.get(i).size, this);
				} else
					repaint();
			}
		}
	}

	// if a bullet hit a tank, it will kill that;
	// judge one point A(x1,y1) is whether in the range of a rectangle
	public boolean pIsin(int x1, int y1, int x2, int y2, int x3, int y3) {
		return ((x3 >= x1 && x3 <= x2) && (y3 >= y1 && y3 <= y2));
	}

	public boolean killTank(Tank tk, Bullet blt) {

		return (pIsin(tk.x, tk.y, tk.x + tk.size, tk.y + tk.size,
				(2 * blt.x + blt.size) / 2, (2 * blt.y + blt.size) / 2));
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// enemy's tank try to move and attack
			
			for (int i = 0; i < this.vtk.size(); i++) {
				// Random rand = new Random();
				// for (int j = 0; j < rand.nextInt(10); j ++){
				moveTank(this.vtk.get(i).rotate, this.vtk.get(i));
				//shoot_enemy(this.vtk.get(i));
				// repaint();
				// }
			}

			// attack me
			for (int i = 0; i < this.vtk.size(); i++) {
				if (this.vtk.isEmpty())
					break;
				Tank tm_tk = this.vtk.get(i);
				for (int j = 0; j < tm_tk.blt_v.size(); j++) {
					if (tm_tk.blt_v.isEmpty())
						break;
					Bullet tm_blt = tm_tk.blt_v.get(j);
					if (killTank(mt, tm_blt)) {
						if (mt.type > 1) {
							mt.type--;
						} 
						else {
							mt.is_alive = false;
							int life = --mt.num;
							Bomb bmb = new Bomb(mt.x, mt.y, true);
							bomb.add(bmb);
							mt = new MyTank(0, 0, 0, tk_size, tk_speed, 1,life,
									this.border_x1, this.border_y1, this.border_x2,
									this.border_y2, true);
							//repaint();
							//System.gc();
						}
						//if (this.vtk.get(i).is_alive){
						//System.out.println("adsfafsf");
						if (tm_tk.is_alive)
							tm_tk.blt_v.remove(j);
							//System.out.println("i:"+i);
						//}
						
						repaint();
					}
				}
			}

			// attack them
			for (int i = 0; i < this.vtk.size(); i++) {
				if (this.vtk.isEmpty())
					break;
				Tank tm_tk = this.vtk.get(i);
				for (int j = 0; j < this.mt.blt_v.size(); j++) {
					if (this.mt.blt_v.isEmpty())
						break;
					Bullet tm_blt = this.mt.blt_v.get(j);
					if (killTank(tm_tk, tm_blt)) {

						// if that tank dead, recreate a lower one
						if (tm_tk.type > 1) {
							tm_tk.type--;
							// EnmTk emy_tk1 = new
							// EnmTk(2,tm_tk.x,tm_tk.y,tk_size,tk_speed,tm_tk.type,this.border_x1,this.border_y1,
							// this.border_x2,this.border_y2,true);
							// vtk.add(emy_tk1);
						} else {
							tm_tk.is_alive = false;
							this.kill_by_mt++;
							for (int k = 0; k < this.vtk.size(); k ++){
								 this.vtk.get(k).num --;
							}
							et.num --;
							Bomb bmb = new Bomb(tm_tk.x, tm_tk.y, true);
							bomb.add(bmb);
							this.vtk.remove(i);
						}
						// wipe the bullet and enemy's tank
						if (this.mt.is_alive)
							this.mt.blt_v.remove(j);
						repaint();
					}
				}
			}
			repaint();
		}
	}
}

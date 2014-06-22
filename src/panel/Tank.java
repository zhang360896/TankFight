package panel;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.*;


public class Tank {
		int rotate;
		int x,y;//坦克坐标
		int size;//坦克大小
		int speed;//坦克速度
		int type;//坦克类型
		int num;//坦克数量
		//the range of map
		int border_x1, border_y1;
		int border_x2, border_y2;
		boolean is_alive;
		Vector <Bullet>  blt_v;
		public Tank(int rt, int x, int y, int size, int speed, int type, int num,
					int x1, int y1, int x2, int y2, boolean alive){
			blt_v = new Vector<Bullet>();
			this.rotate = rt;
			this.x = x;
			this.y = y;
			this.size = size;
			this.speed = speed;
			this.type = type;
			this.num = num;
			this.is_alive = alive;
			
			this.border_x1 = x1;
			this.border_y1 = y1;
			this.border_x2 = x2;
			this.border_y2 = y2;
		}
		boolean judge_bord(int x, int y)
		{
			return ( x >= this.border_x1 && y >= this.border_y1 && x <= this.border_x2 && y <= this.border_y2);
		}
		//two functions to implement the rotation of tanks
		public boolean getIs_alive() {
			return is_alive;
		}
		public void setIs_alive(boolean isAlive) {
			is_alive = isAlive;
		}
		
}

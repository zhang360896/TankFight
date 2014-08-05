package panel;


public class Bullet implements Runnable{
	int speed;//pixels per second
	int x, y;//location
	int type;
	int direction;
	int size;
	int border_x1, border_y1;
	int border_x2, border_y2;
	
	public Bullet(int direction, int speed, int type, int size, int x, int y,
				  int x1, int y1, int x2, int y2){
		this.direction = direction;
		this.speed = speed;
		this.type = type;
		this.size = size;
		this.x = x;
		this.y = y;
		this.border_x1 = x1;
		this.border_y1 = y1;
		this.border_x2 = x2; 
		this.border_y2 = y2;
		
	}
	public void run() {
		// TODO Auto-generated method stub
		while (true){
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			switch (this.direction){
				case 0: this.y -= this.speed;break;
				case 1: this.x += this.speed;break;
				case 2: this.y += this.speed;break;
				case 3: this.x -= this.speed;break;
				default:
			}
			if (!judge_bord(this.x, this.y))break;
		}
	}
	boolean judge_bord(int x, int y)
	{
		return ( x > -1*this.size && y > -1*this.size && x <= this.border_x2 && y <= this.border_y2);
	}
}

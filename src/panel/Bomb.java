package panel;

public class Bomb {
	int x,y;//the location of a bomb;
	boolean isAlive;
	int type = 9;
	public Bomb(int x, int y, boolean live){
		this.x = x;
		this.y = y;
		this.isAlive = live;
	}
	public void lifeDown(){
		this.type --;
		if (this.type == 0)this.isAlive = false;
	}
}

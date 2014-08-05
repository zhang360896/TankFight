package panel;

import java.util.Random;

public class EnmTk extends Tank implements Runnable{
	public EnmTk(int rt, int x, int y, int size, int speed, int type,
			int num, int x1, int y1, int x2, int y2, boolean alive){
		super(rt,x,y,size,speed,type,num,x1,y1,x2,y2,alive);
	}
<<<<<<< HEAD
	
=======
>>>>>>> 869d2af16fe06e1bbb7be0df1a4f8fd6d2ec8b8b
	public void run() {
		// TODO Auto-generated method stub
		//while (true)
		
			while (true){
				if (this.getIs_alive() == false)break;
				try {
					Thread.sleep((long)(Math.random() * 2000));
				} catch (Exception e) {
				// TODO: handle exception
					e.printStackTrace();
				}
				Random rand = new Random();
				//if ((rand.nextInt(15))< 1){
				
					this.rotate = (rand.nextInt(4));
					
				//}
			}
		
	}
}

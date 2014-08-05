package panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyStartPanel extends JPanel implements Runnable{
	int win_width, win_height; 
	int win_locx, win_locy;
	int more_space;
	int fram;
	public MyStartPanel(int locx, int locy, 
						int width, int height, int space)
	{
		this.win_locx = locx;
		this.win_locy = locy;
		this.win_width = width;
		this.win_height = height;
		this.fram = 0;
		this.more_space = space;
	}
	public void paint(Graphics g)
	{
		super.paint(g);
		if (this.fram % 2 == 0) {
			Font ft = new Font("»ªÎÄÐÂÎº", Font.BOLD, 64);
			String stack = "Stack 1";
			//g.fillRect(this.win_locx, this.win_locy, this.win_width,
			//		this.win_height);
			g.setFont(ft);
			g.setColor(Color.yellow);
			//this.setBackground(Color.black);
			g.drawString(stack, (this.win_width+this.more_space-180)/2,
					(this.win_height+this.more_space-100)/2);
		}
	}
<<<<<<< HEAD
	
=======
>>>>>>> 869d2af16fe06e1bbb7be0df1a4f8fd6d2ec8b8b
	public void run() {
		// TODO Auto-generated method stub
		while (true){
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.fram ++;
			//this.fram %= 2;
			repaint();
		}
	}
	
}

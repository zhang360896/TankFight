package panel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

import java.util.*;

public class scene extends JFrame implements ActionListener,Runnable  {
	DrawTank dtk = null;
	GraphicPanel gp = null;
	Thread dtk_t_st = null;//the thread of the game at the beginning
	boolean gamestarted = false;
	int win_width, win_height;
	int win_locx, win_locy;
	int extra_space = 150;
	int ng_clicked = 0;
	// define the menu ingredients below
	JMenu jm_gm;
	JMenuBar jmb;
	JMenuItem jmi_ng, jmi_o, jmi_s, jmi_ext;
	MyStartPanel msp = null;

	public static void main(String[] args) {
		scene sn = new scene();
	}

	public scene() {
		this.win_width = 800;
		this.win_height = 700;
		this.win_locx = 200;
		this.win_locy = 0;
		this.dtk = new DrawTank(this.win_locx, this.win_locy, this.win_width,
				this.win_height, this.extra_space);
		// create tanks and add it into Vector

		this.setLocation(this.win_locx, this.win_locy);

		// add panel and listened object
		// add the ingredients to the menu;
		jm_gm = new JMenu("游戏(G)");
		jm_gm.setMnemonic('G');
		jmi_ng = new JMenuItem("新游戏(N)");
		jmi_ng.setMnemonic('N');
		jmi_o = new JMenuItem("打开(O)");
		jmi_o.setMnemonic('O');
		jmi_s = new JMenuItem("保存(S)");
		jmi_s.setMnemonic('S');
		jmi_ext = new JMenuItem("退出(E)");
		jmi_ext.setMnemonic('E');

		jmb = new JMenuBar();

		jmi_ng.addActionListener(this);
		jmi_ng.setActionCommand("newgame");

		jmi_o.addActionListener(this);
		jmi_o.setActionCommand("open");

		jmi_s.addActionListener(this);
		jmi_s.setActionCommand("save");
		jmi_s.setEnabled(false);
		
		jmi_ext.addActionListener(this);
		jmi_ext.setActionCommand("exit");

		jm_gm.add(jmi_ng);
		jm_gm.add(jmi_o);
		jm_gm.add(jmi_s);
		jm_gm.add(jmi_ext);
		
		jmb.add(jm_gm);
		// configure the parameters of the window
		gp = new GraphicPanel(this.win_width, this.win_height, this.extra_space);
		this.add(gp);
		this.setJMenuBar(jmb);
		this.setTitle("TankFight");
		this.setSize(this.win_width+this.extra_space, this.win_height);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	@SuppressWarnings("deprecation")
	
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		Fileprocess fp = new Fileprocess();
		if (e.getActionCommand().equals("open")) {
			
			// if the "open" item is clicked
			// System.out.println("prex:"+dtk.mt.x+" "+dtk.mt.y);
			//if start the game at beginning
			
//				dtk = new DrawTank(this.win_locx, this.win_locy, this.win_width,
//						this.win_height, this.extra_space);
//				this.remove(gp);
//				dtk.mt = fp.open_battle(dtk.mt, dtk.vtk);
//				//dtk_t_st = new Thread(dtk);
//				//dtk_t_st.start();
//				dtk.setBackground(Color.black);
//				
//				this.add(dtk);
//				this.addKeyListener(dtk);
//				this.setVisible(true);
//			}
			//else {
			// start the DrawTank thread;
			dtk.mt = fp.open_battle(dtk.mt, dtk.vtk);
			dtk.repaint();
			if (gamestarted == false){
				this.remove(gp);
				Thread dtk_t = new Thread(dtk);
				dtk_t.start();
				dtk.setBackground(Color.black);
				this.add(dtk);
				this.addKeyListener(dtk);
				this.setVisible(true);
				gamestarted = true;
			}
		
			//}
			//this.remove(gp);
			// System.out.println(dtk.vtk.get(1).is_alive+" "+
			// dtk.vtk.get(1).x+" "+dtk.vtk.get(1).y);
			//repaint();
			
			// System.out.println("afterx:"+dtk.mt.x+" "+dtk.mt.y);
		} else if (e.getActionCommand() == "save") {
					fp.save_battle(dtk.mt, dtk.vtk);
				} else 
					if (e.getActionCommand() == "newgame") {
						if (this.ng_clicked == 0){
							msp = new MyStartPanel(this.win_locx, this.win_locy, this.win_width,
											this.win_height, this.extra_space);
							msp.setBackground(Color.black);
							Thread td = new Thread(msp);
							td.start();
							this.add(msp);
							this.setVisible(true);
							Thread td_f = new Thread(this);
							td_f.start();
						}
						else {
							this.remove(msp);
							this.remove(dtk);
							this.remove(gp);
							msp = new MyStartPanel(this.win_locx, this.win_locy, this.win_width,
									this.win_height, this.extra_space);
							msp.setBackground(Color.black);
							Thread td = new Thread(msp);
							td.start();
							this.add(msp);
							this.setVisible(true);
							Thread td_f = new Thread(this);
							td_f.start();
							this.ng_clicked = 0;
						}
						this.ng_clicked ++;
						
						// count the border
//						if (msp.fram >= 50){

//						}
					  }
					else 
						if (e.getActionCommand() == "exit"){
							System.exit(0);
						}
	}

	
	public void run() {
		// TODO Auto-generated method stub
		while (true){
			if (msp.fram >= 10){
				jmi_s.setEnabled(true);
				this.remove(this.msp);
				this.remove(gp);
				// start the DrawTank thread;
				Thread dtk_t = new Thread(dtk);
				dtk_t.start();
				dtk.setBackground(Color.black);
				this.add(dtk);
				this.addKeyListener(dtk);
				this.setVisible(true);
				break;
			}
		}
	}
	public class GraphicPanel extends JPanel
	{
		Image img_bg = null;
		int wd, hi;
		int ex_space;
		public GraphicPanel(int width, int height, int extra_space){
//			try {
//				img_bg = Toolkit.getDefaultToolkit().
//							getImage(Panel.class.getResource("20694.jpg"));
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				
//			}
			
			this.wd = width;
			this.hi = height;
			this.ex_space = extra_space;
		}
		public void paint(Graphics g){
			Image steel_wall = null;
			try {
				steel_wall = javax.imageio.ImageIO.read(DrawTank.class
						.getClassLoader().getResourceAsStream("20694.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			g.drawImage(img_bg, 0, 0, this.wd, this.hi, this);
			String scores = "1P成绩";
			g.setColor(Color.gray);
			g.drawString(scores, 0, 40);
			g.drawImage(steel_wall, 0, 0, this.wd+this.ex_space, this.hi, this);
		}
	}
}

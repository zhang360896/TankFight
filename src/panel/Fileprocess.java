package panel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JFileChooser;

public class Fileprocess {
	public MyTank open_battle(Tank tk, Vector<EnmTk> etk)
	{
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("请选择文件");
		jfc.showOpenDialog(null);
		jfc.setVisible(true);
		String filename = jfc.getSelectedFile().getAbsolutePath();
		//System.out.println(filename) ;
		FileReader fr = null;
		BufferedReader br = null;
		MyTank mt = null;
		try {
			fr = new FileReader(filename);
			br = new BufferedReader(fr);
			//String show_all = "";
			String tmp = "";
			tmp = br.readLine();
			tk = extract_data(tmp, tk);
			mt = new MyTank(tk.rotate,tk.x,tk.y,tk.size,tk.speed,tk.type,tk.num,
					tk.border_x1,tk.border_x2,tk.border_y1,tk.border_y2,tk.is_alive);
			
			//System.out.println(tk.is_alive+" "+tk.rotate+" "+tk.x+" "+tk.y);
			int etk_num = 0;
			etk.clear();
			Thread t_mv = null;
			while ((tmp = br.readLine()) != null){
				//show_all +=  tmp+"\r\n";
				//etk.remove(etk.get(etk_num));
				System.out.println(tmp);
				EnmTk enm_tk = extract_data(tmp, tk);
				etk.add(enm_tk);
				t_mv = new Thread(enm_tk);
				t_mv.start();
				etk_num ++;
			}
			//System.out.println(etk.size());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally{
			try {
				fr.close();
				br.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		return mt;
	}
	void save_battle(Tank tk, Vector<EnmTk> etk)
	{
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("保存文件");
		jfc.showSaveDialog(null);
		jfc.setVisible(true);
		String filename = jfc.getSelectedFile().getAbsolutePath();
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			fw = new FileWriter(filename);
			//System.out.println(filename);
			bw = new BufferedWriter(fw);
			String inf_tk = tk.is_alive+" "+tk.rotate+" "+tk.size+" "
							+tk.speed+" "+tk.type+" "+tk.num+" "+tk.x+" "+tk.y;
			bw.write(inf_tk);
			bw.newLine();
			for (int i = 0; i < etk.size();i ++){
				inf_tk= etk.get(i).is_alive+" "+etk.get(i).rotate+" "
						+etk.get(i).size+" "+etk.get(i).speed+" "
						+etk.get(i).type+" "+etk.get(i).num+" "+etk.get(i).x+" "+etk.get(i).y;
				bw.write(inf_tk);
				bw.newLine();
			}
			//inf_mt = mt.
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		  }
			finally{
				try {
					//closing bw can close the fw at the same time;
					//fw.close();
					bw.close();
				} catch (IOException e1) {
				// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			}
	}
	public EnmTk extract_data(String x, Tank t)
	{
		int i = 0;
		int counter = 0;
		String tmp = "";//rt,x,y,size,speed,type,x1,y1,x2,y2,alive
		EnmTk tk = new EnmTk(t.rotate,t.x,t.y,t.size,t.speed,t.type,t.num,
							t.border_x1,t.border_x2,t.border_y1,t.border_y2,t.is_alive);
		//System.out.println("x:"+x);
		while (i < x.length()){
			while (i < x.length() && x.charAt(i) != ' ' ){
				tmp += x.charAt(i);
				i ++;
				if (i == x.length())break;
			}
			//System.out.println("i:"+i+" "+x.length()+" "+tmp);
			switch (counter){
				case 0:tk.is_alive = Boolean.parseBoolean(tmp);break;
				case 1:tk.rotate = Integer.parseInt(tmp);break;
				case 2:tk.size = Integer.parseInt(tmp);break;
				case 3:tk.speed = Integer.parseInt(tmp);break;
				case 4:tk.type = Integer.parseInt(tmp);break;
				case 5:tk.num = Integer.parseInt(tmp);break;
				case 6:tk.x = Integer.parseInt(tmp);break;
				case 7:tk.y = Integer.parseInt(tmp);break;
			}
			i ++;
			tmp = "";
			counter ++;
		}
		return tk;
	}
}

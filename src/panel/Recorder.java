package panel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

/*
 * record the amounts of enemies , and prepare for the next configuring panel
 * */
public class Recorder {
	private static int tot_enm;
	private static int killed_by_mt;
	private static int my_life;
	private static Vector<EnmTk> vtk;
	public Recorder(int totenm, int kill, int ml, Vector<EnmTk> vtk){
		this.tot_enm = totenm;
		this.killed_by_mt = kill;
		this.my_life = ml;
		this.vtk = vtk;
	}
	FileWriter fw = null;
	BufferedWriter bw = null;
	String filename = "C:\\tankrecorder";
	public void save()
	{
		try {
			fw = new FileWriter(filename);
			//System.out.println(filename);
			bw = new BufferedWriter(fw);
			String inf_tk = this.tot_enm+" "+this.killed_by_mt+" "
							+this.my_life;
			bw.write(inf_tk);
			bw.newLine();
			for (int i = 0; i < this.vtk.size();i ++){
				inf_tk= vtk.get(i).is_alive+" "+vtk.get(i).rotate+" "
						+vtk.get(i).size+" "+vtk.get(i).speed+" "
						+vtk.get(i).type+" "+vtk.get(i).num+" "+vtk.get(i).x+" "+vtk.get(i).y;
				bw.write(inf_tk);
				bw.newLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

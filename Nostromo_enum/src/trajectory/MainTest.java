package trajectory;

import infos.Constant;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;



public class MainTest {

	public static void main(String[] args) throws ParseException {
		
		double ISP = 3000;
		double distance = 0.1*Constant.UA;
		/**EscapeEarth traj=new EscapeEarth(15000, 4.5, ISP);
		double dV=traj.deltaV_LEO_to_Escape(1000000);
		double T=traj.temps_LEO_to_Escape(1000000);
		System.out.println(T);
		System.out.println(dV);
		
		EscapeEarth trajGTO=new EscapeEarth(10000, 4.5, ISP);
		double dV=trajGTO.deltaV_GTO_to_Escape(200000, 36000000);
		System.out.println(dV);*/
		

		Reach_Asteroid re=new Reach_Asteroid(ISP);
		//ArrayList<Double> list= re.rdv(1000, 4.5, 15000.0);
		//Return_To_Earth ret=new Return_To_Earth(ISP);
		//ArrayList<Double> listret= ret.rdv(30*Math.PI/180,500, 4.5, 15000.0, 1000);
	//	System.out.println(list.toString());
		//System.out.println(listret.toString());
	}

}

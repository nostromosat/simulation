package trajectory;

import infos.Constant;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;



public class MainTest {

	public static void main(String[] args) throws ParseException {
		
		double ISP = 3000;
		double distance = 0.1*Constant.UA;
		/**Escape_Earth traj=new Escape_Earth(15000, 4.5, ISP);
		ArrayList<Double> listtogeo = traj.LEO_to_Escape(100000);
		System.out.println(listtogeo.toString());
		*/

		/**
		double dV=traj.deltaV_LEO_to_Escape(1000000);
		
		double T=traj.temps_LEO_to_Escape(1000000);
		System.out.println(T);
		System.out.println(dV);
		
		EscapeEarth trajGTO=new EscapeEarth(10000, 4.5, ISP);
		double dV=trajGTO.deltaV_GTO_to_Escape(200000, 36000000);
		System.out.println(dV);*/
		/*
		
		Reach_Asteroid re=new Reach_Asteroid(ISP);
		ArrayList<Double> listH =re.rdvHohmann(15000,2);
		ArrayList<Double> list =re.rdvFastHohmann(listH.get(2),15000,2);
		ArrayList<Double> listS =re.rdvSpiral(15000,2);
		System.out.println(listH.toString());
		System.out.println(list.toString());
		System.out.println(listS.toString());
		
		double duree=list.get(2);
		while (list.get(3)==1)
		{
			list =re.rdvFastHohmann(duree-6,15000,2);
			duree=list.get(2);
			System.out.println(duree);

		}
		double dephasage=180-list.get(4);
		double periode = dephasage/Math.abs((re.meanmotion-re.meanmotion_Earth)*365);
		System.out.println("periode  "+ periode + " ans");
		//ArrayList<Double> list= re.rdv(1000, 4.5, 15000.0);
		//Return_To_Earth ret=new Return_To_Earth(ISP);
		//ArrayList<Double> listret= ret.rdv(30*Math.PI/180,500, 4.5, 15000.0, 1000);
		System.out.println(list.toString());
		//System.out.println(listret.toString());
		 */
		
		/**Return_To_Earth ret=new Return_To_Earth(ISP);
		//ArrayList<Double> listret= ret.rdvHohmann(15000.0, 10000,2);
		//System.out.println(listret.toString());
		ArrayList<Double> listret2= ret.rdvFastHohmann(100,15000.0, 10000,2);
		System.out.println(listret2.toString());
		 */
		/**
		InterplanetaryToGEO togeo=new InterplanetaryToGEO(10000, 4.5, ISP);
		ArrayList<Double> listtogeo = togeo.toGEO();
		System.out.println(listtogeo.toString());

		 */
		
		/*
		Reach_Asteroid re=new Reach_Asteroid(ISP);
		ArrayList<Double> listH =re.rdvHohmann(15000,2);
	//	ArrayList<Double> list =re.rdvBiElliptic(400,15000,2);
		ArrayList<Double> list2 =re.rdvFastHohmann(100,15000,2);
	*/
		
		//Return_To_Earth ret=new Return_To_Earth(ISP);
		//ArrayList<Double> listret= ret.rdvBiElliptic(400,15000.0, 10000,2);
		
		//System.out.println(listret.toString());
		
		
		
	}

}

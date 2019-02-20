package trajectory;

import java.util.ArrayList;

import infos.Constant;

public class Trajectory {
	
	/**
	 * 
	 * @param masse : masse totale estimée (kg)
	 * @param masse_minerai (kg) à rapporter
	 * @param poussee : poussée totale du module (nb prop * poussee_prop) (N)
	 * @param Isp (s)
	 * @param alt_LEO : altitude LEO du lanceur sans le rayon de la Terre (ex 100 km)
	 */
	public static ArrayList<Double> computeTrajectoryHohmann(double masse, double masse_minerai, double poussee, double Isp, double alt_LEO) {
		
		double masse_current;
		
		// Escape
		Escape_Earth esc_obj=new Escape_Earth(masse, poussee, Isp);
		ArrayList<Double> traj_esc = esc_obj.LEO_to_Escape(alt_LEO);
		masse_current=masse-traj_esc.get(1);
		double deltaV_esc=traj_esc.get(0);
		double masse_esc=traj_esc.get(1);
		double duree_esc=traj_esc.get(2);
		
		
		// Reach
		Reach_Asteroid reach_obj=new Reach_Asteroid(Isp);
		ArrayList<Double> traj_reach=reach_obj.rdvHohmann(masse_current, poussee);
		masse_current=masse_current-traj_reach.get(1);
		double deltaV_reach=traj_reach.get(0);
		double masse_reach=traj_reach.get(1);
		double duree_reach=traj_reach.get(2);

		//Mission
		double deltaV_mission=0.2*traj_reach.get(0); //20%
		double duree_mission=90; //(jours)
		double masse_mission=masse_current*(1-Math.exp(-deltaV_mission/(Constant.g0*Isp)));
		masse_current=masse_current-masse_mission;
		
		//Return
		Return_To_Earth return_obj=new Return_To_Earth(Isp);
		ArrayList<Double> traj_return = return_obj.rdvHohmann(masse_current, masse_minerai, poussee);
		masse_current=masse_current-traj_return.get(1);
		double deltaV_ret=traj_return.get(0);
		double masse_ret=traj_return.get(1);
		double duree_ret=traj_return.get(2);
		
		
		//ToGEO
		InterplanetaryToGEO toGEO_obj=new InterplanetaryToGEO(masse_current,poussee,Isp);
		ArrayList<Double> traj_toGEO = toGEO_obj.toGEO();
		double deltaV_toG=traj_toGEO.get(0);
		double masse_toG=traj_toGEO.get(1);
		double duree_toG=traj_toGEO.get(2);
		
		System.out.println("-------------------------------");		
		System.out.println("deltaV escape  : "+deltaV_esc);
		System.out.println("deltaV reach  : "+deltaV_reach);
		System.out.println("deltaV mission  : "+deltaV_mission);
		System.out.println("deltaV return  : "+deltaV_ret);
		System.out.println("deltaV toGEO  : "+deltaV_toG);
		
		System.out.println("-------------------------------");		
		System.out.println("masse escape  : "+masse_esc);
		System.out.println("masse reach  : "+masse_reach);
		System.out.println("masse mission  : "+masse_mission);
		System.out.println("masse return  : "+masse_ret);
		System.out.println("masse toGEO  : "+masse_toG);
		
		System.out.println("-------------------------------");		
		System.out.println("duree escape  : "+duree_esc);
		System.out.println("duree reach  : "+duree_reach);
		System.out.println("duree mission  : "+duree_mission);
		System.out.println("duree return  : "+duree_ret);
		System.out.println("duree toGEO  : "+duree_toG);

		
		System.out.println("-------------------------------");		
		double deltaV_tot=deltaV_esc+deltaV_reach+deltaV_mission+deltaV_ret+deltaV_toG;
		double masse_tot=masse_esc+masse_reach+masse_mission+masse_ret+masse_toG;
		double duree_tot=duree_esc+duree_reach+duree_mission+duree_ret+duree_toG;
		ArrayList<Double> traj=new ArrayList<Double>();
		traj.add(deltaV_tot);
		traj.add(masse_tot);
		traj.add(duree_tot);
		System.out.println("delta V total  : "+ deltaV_tot);
		System.out.println("Masse totale  : "+masse_tot);
		System.out.println("Duree totale  : "+duree_tot);
		
		if (traj_reach.get(3)==0) {
			System.out.println("Atteinte impossible en low thrust");
		}
		if (traj_return.get(3)==0) {
			System.out.println("Retour impossible en low thrust");
		}
		
		return traj;
		

		
	}
	
	public static ArrayList<Double> computeTrajectory(double masse, double masse_minerai, double poussee, double Isp, double alt_LEO) {

double masse_current;
		
		// Escape
		Escape_Earth esc_obj=new Escape_Earth(masse, poussee, Isp);
		ArrayList<Double> traj_esc = esc_obj.LEO_to_Escape(alt_LEO);
		masse_current=masse-traj_esc.get(1);
		double deltaV_esc=traj_esc.get(0);
		double masse_esc=traj_esc.get(1);
		double duree_esc=traj_esc.get(2);
		
		
		// Reach
		Reach_Asteroid reach_obj=new Reach_Asteroid(Isp);
		ArrayList<Double> traj_reachH=reach_obj.rdvHohmann(masse_current, poussee);
		ArrayList<Double> traj_reach =reach_obj.rdvFastHohmann(traj_reachH.get(2),masse_current,poussee);

		double duree_reach=traj_reachH.get(2);
		while (traj_reach.get(3)==1)
		{
			traj_reach =reach_obj.rdvFastHohmann(duree_reach-6,masse_current,poussee);
			duree_reach=traj_reach.get(2);
		//	System.out.println(duree_reach);

		}
		
		masse_current=masse_current-traj_reach.get(1);
		double deltaV_reach=traj_reach.get(0);
		double masse_reach=traj_reach.get(1);
		duree_reach=traj_reach.get(2);

		//Mission
		double deltaV_mission=0.2*traj_reach.get(0); //20%
		double duree_mission=90; //(jours)
		double masse_mission=masse_current*(1-Math.exp(-deltaV_mission/(Constant.g0*Isp)));
		masse_current=masse_current-masse_mission;
		
		//Return
		Return_To_Earth return_obj=new Return_To_Earth(Isp);
		ArrayList<Double> traj_return = return_obj.rdvHohmann(masse_current, masse_minerai, poussee);
		masse_current=masse_current-traj_return.get(1);
		double deltaV_ret=traj_return.get(0);
		double masse_ret=traj_return.get(1);
		double duree_ret=traj_return.get(2);
		
		
		//ToGEO
		InterplanetaryToGEO toGEO_obj=new InterplanetaryToGEO(masse_current,poussee,Isp);
		ArrayList<Double> traj_toGEO = toGEO_obj.toGEO();
		double deltaV_toG=traj_toGEO.get(0);
		double masse_toG=traj_toGEO.get(1);
		double duree_toG=traj_toGEO.get(2);
		
		System.out.println("-------------------------------");		
		System.out.println("deltaV escape  : "+deltaV_esc);
		System.out.println("deltaV reach  : "+deltaV_reach);
		System.out.println("deltaV mission  : "+deltaV_mission);
		System.out.println("deltaV return  : "+deltaV_ret);
		System.out.println("deltaV toGEO  : "+deltaV_toG);
		
		System.out.println("-------------------------------");		
		System.out.println("masse escape  : "+masse_esc);
		System.out.println("masse reach  : "+masse_reach);
		System.out.println("masse mission  : "+masse_mission);
		System.out.println("masse return  : "+masse_ret);
		System.out.println("masse toGEO  : "+masse_toG);
		
		System.out.println("-------------------------------");		
		System.out.println("duree escape  : "+duree_esc);
		System.out.println("duree reach  : "+duree_reach);
		System.out.println("duree mission  : "+duree_mission);
		System.out.println("duree return  : "+duree_ret);
		System.out.println("duree toGEO  : "+duree_toG);

		
		System.out.println("-------------------------------");		
		double deltaV_tot=deltaV_esc+deltaV_reach+deltaV_mission+deltaV_ret+deltaV_toG;
		double masse_tot=masse_esc+masse_reach+masse_mission+masse_ret+masse_toG;
		double duree_tot=duree_esc+duree_reach+duree_mission+duree_ret+duree_toG;
		ArrayList<Double> traj=new ArrayList<Double>();
		traj.add(deltaV_tot);
		traj.add(masse_tot);
		traj.add(duree_tot);
		System.out.println("delta V total  : "+ deltaV_tot);
		System.out.println("Masse totale  : "+masse_tot);
		System.out.println("Duree totale  : "+duree_tot);
		
		if (traj_reach.get(3)==0) {
			System.out.println("Atteinte impossible en low thrust");
		}
		if (traj_return.get(3)==0) {
			System.out.println("Retour impossible en low thrust");
		}
		
		
		
		return traj;
	}
	
	
	
	public static ArrayList<Double> computeTrajectoryEllipticLEO(double masse, double masse_minerai, double poussee, double Isp, double alt_LEO) {

		double masse_current;
				
				// Escape
				Escape_Earth esc_obj=new Escape_Earth(masse, poussee, Isp);
				ArrayList<Double> traj_esc = esc_obj.LEO_to_Escape(alt_LEO);
				masse_current=masse-traj_esc.get(1);
				double deltaV_esc=traj_esc.get(0);
				double masse_esc=traj_esc.get(1);
				double duree_esc=traj_esc.get(2);
				
				
				// Reach
				Reach_Asteroid reach_obj=new Reach_Asteroid(Isp);
				ArrayList<Double> traj_reach=reach_obj.rdvBiElliptic(500,masse_current, poussee);

				
				masse_current=masse_current-traj_reach.get(1);
				double deltaV_reach=traj_reach.get(0);
				double masse_reach=traj_reach.get(1);
				double duree_reach=traj_reach.get(2);

				//Mission
				double deltaV_mission=0.2*traj_reach.get(0); //20%
				double duree_mission=90; //(jours)
				double masse_mission=masse_current*(1-Math.exp(-deltaV_mission/(Constant.g0*Isp)));
				masse_current=masse_current-masse_mission;
				
				//Return
				Return_To_Earth return_obj=new Return_To_Earth(Isp);
				ArrayList<Double> traj_return = return_obj.rdvBiElliptic(400,masse_current, masse_minerai, poussee);
				masse_current=masse_current-traj_return.get(1);
				double deltaV_ret=traj_return.get(0);
				double masse_ret=traj_return.get(1);
				double duree_ret=traj_return.get(2);
				
				
				//ToGEO
				InterplanetaryToGEO toGEO_obj=new InterplanetaryToGEO(masse_current,poussee,Isp);
				ArrayList<Double> traj_toGEO = toGEO_obj.toGEO();
				double deltaV_toG=traj_toGEO.get(0);
				double masse_toG=traj_toGEO.get(1);
				double duree_toG=traj_toGEO.get(2);
				/**
				System.out.println("-------------------------------");		
				System.out.println("deltaV escape  : "+deltaV_esc);
				System.out.println("deltaV reach  : "+deltaV_reach);
				System.out.println("deltaV mission  : "+deltaV_mission);
				System.out.println("deltaV return  : "+deltaV_ret);
				System.out.println("deltaV toGEO  : "+deltaV_toG);
				
				System.out.println("-------------------------------");		
				System.out.println("masse escape  : "+masse_esc);
				System.out.println("masse reach  : "+masse_reach);
				System.out.println("masse mission  : "+masse_mission);
				System.out.println("masse return  : "+masse_ret);
				System.out.println("masse toGEO  : "+masse_toG);
				
				System.out.println("-------------------------------");		
				System.out.println("duree escape  : "+duree_esc);
				System.out.println("duree reach  : "+duree_reach);
				System.out.println("duree mission  : "+duree_mission);
				System.out.println("duree return  : "+duree_ret);
				System.out.println("duree toGEO  : "+duree_toG);

				
				System.out.println("-------------------------------");	
				*/	
				double deltaV_tot=deltaV_esc+deltaV_reach+deltaV_mission+deltaV_ret+deltaV_toG;
				double masse_tot=masse_esc+masse_reach+masse_mission+masse_ret+masse_toG;
				double duree_tot=duree_esc+duree_reach+duree_mission+duree_ret+duree_toG;
				ArrayList<Double> traj=new ArrayList<Double>();
				traj.add(deltaV_tot);
				traj.add(masse_tot);
				traj.add(duree_tot);
				//System.out.println("delta V total  : "+ deltaV_tot);
				//System.out.println("Masse totale  : "+masse_tot);
				//System.out.println("Duree totale  : "+duree_tot);
				
				if (traj_reach.get(3)==0) {
					//System.out.println("Atteinte impossible en low thrust");
				}
				if (traj_return.get(3)==0) {
					//System.out.println("Retour impossible en low thrust");
				}
				
				
				
				return traj;
			}
	
	public static ArrayList<Double> computeTrajectoryEllipticGTO(double masse, double masse_minerai, double poussee, double Isp, double alt_perigee,double alt_apogee) {

		double masse_current;
				
				// Escape
				Escape_Earth esc_obj=new Escape_Earth(masse, poussee, Isp);
				ArrayList<Double> traj_esc = esc_obj.GTO_to_Escape(alt_perigee, alt_apogee);
				masse_current=masse-traj_esc.get(1);
				double deltaV_esc=traj_esc.get(0);
				double masse_esc=traj_esc.get(1);
				double duree_esc=traj_esc.get(2);
				
				
				// Reach
				Reach_Asteroid reach_obj=new Reach_Asteroid(Isp);
				ArrayList<Double> traj_reach=reach_obj.rdvBiElliptic(500,masse_current, poussee);

				
				masse_current=masse_current-traj_reach.get(1);
				double deltaV_reach=traj_reach.get(0);
				double masse_reach=traj_reach.get(1);
				double duree_reach=traj_reach.get(2);

				//Mission
				double deltaV_mission=0.2*traj_reach.get(0); //20%
				double duree_mission=90; //(jours)
				double masse_mission=masse_current*(1-Math.exp(-deltaV_mission/(Constant.g0*Isp)));
				masse_current=masse_current-masse_mission;
				
				//Return
				Return_To_Earth return_obj=new Return_To_Earth(Isp);
				ArrayList<Double> traj_return = return_obj.rdvBiElliptic(400,masse_current, masse_minerai, poussee);
				masse_current=masse_current-traj_return.get(1);
				double deltaV_ret=traj_return.get(0);
				double masse_ret=traj_return.get(1);
				double duree_ret=traj_return.get(2);
				
				
				//ToGEO
				InterplanetaryToGEO toGEO_obj=new InterplanetaryToGEO(masse_current,poussee,Isp);
				ArrayList<Double> traj_toGEO = toGEO_obj.toGEO();
				double deltaV_toG=traj_toGEO.get(0);
				double masse_toG=traj_toGEO.get(1);
				double duree_toG=traj_toGEO.get(2);
				
				/**System.out.println("-------------------------------");		
				System.out.println("deltaV escape  : "+deltaV_esc);
				System.out.println("deltaV reach  : "+deltaV_reach);
				System.out.println("deltaV mission  : "+deltaV_mission);
				System.out.println("deltaV return  : "+deltaV_ret);
				System.out.println("deltaV toGEO  : "+deltaV_toG);
				
				System.out.println("-------------------------------");		
				System.out.println("masse escape  : "+masse_esc);
				System.out.println("masse reach  : "+masse_reach);
				System.out.println("masse mission  : "+masse_mission);
				System.out.println("masse return  : "+masse_ret);
				System.out.println("masse toGEO  : "+masse_toG);
				
				System.out.println("-------------------------------");		
				System.out.println("duree escape  : "+duree_esc);
				System.out.println("duree reach  : "+duree_reach);
				System.out.println("duree mission  : "+duree_mission);
				System.out.println("duree return  : "+duree_ret);
				System.out.println("duree toGEO  : "+duree_toG);

				
				System.out.println("-------------------------------");*/		
				double deltaV_tot=deltaV_esc+deltaV_reach+deltaV_mission+deltaV_ret+deltaV_toG;
				double masse_tot=masse_esc+masse_reach+masse_mission+masse_ret+masse_toG;
				double duree_tot=duree_esc+duree_reach+duree_mission+duree_ret+duree_toG;
				ArrayList<Double> traj=new ArrayList<Double>();
				traj.add(deltaV_tot);
				traj.add(masse_tot);
				traj.add(duree_tot);
				//System.out.println("delta V total  : "+ deltaV_tot);
				//System.out.println("Masse totale  : "+masse_tot);
				//System.out.println("Duree totale  : "+duree_tot);
				
				if (traj_reach.get(3)==0) {
					//System.out.println("Atteinte impossible en low thrust");
				}
				if (traj_return.get(3)==0) {
					//System.out.println("Retour impossible en low thrust");
				}
				
				
				
				return traj;
			}

	public static ArrayList<Double> computeTrajectoryEllipticEscape(double masse, double masse_minerai, double poussee, double Isp) {

	double masse_current;
			
			// Escape
			Escape_Earth esc_obj=new Escape_Earth(masse, poussee, Isp);
			ArrayList<Double> traj_esc = esc_obj.Escape();
			masse_current=masse-traj_esc.get(1);
			double deltaV_esc=traj_esc.get(0);
			double masse_esc=traj_esc.get(1);
			double duree_esc=traj_esc.get(2);
			
			
			// Reach
			Reach_Asteroid reach_obj=new Reach_Asteroid(Isp);
			ArrayList<Double> traj_reach=reach_obj.rdvBiElliptic(500,masse_current, poussee);

			
			masse_current=masse_current-traj_reach.get(1);
			double deltaV_reach=traj_reach.get(0);
			double masse_reach=traj_reach.get(1);
			double duree_reach=traj_reach.get(2);

			//Mission
			double deltaV_mission=0.2*traj_reach.get(0); //20%
			double duree_mission=90; //(jours)
			double masse_mission=masse_current*(1-Math.exp(-deltaV_mission/(Constant.g0*Isp)));
			masse_current=masse_current-masse_mission;
			
			//Return
			Return_To_Earth return_obj=new Return_To_Earth(Isp);
			ArrayList<Double> traj_return = return_obj.rdvBiElliptic(400,masse_current, masse_minerai, poussee);
			masse_current=masse_current-traj_return.get(1);
			double deltaV_ret=traj_return.get(0);
			double masse_ret=traj_return.get(1);
			double duree_ret=traj_return.get(2);
			
			
			//ToGEO
			InterplanetaryToGEO toGEO_obj=new InterplanetaryToGEO(masse_current,poussee,Isp);
			ArrayList<Double> traj_toGEO = toGEO_obj.toGEO();
			double deltaV_toG=traj_toGEO.get(0);
			double masse_toG=traj_toGEO.get(1);
			double duree_toG=traj_toGEO.get(2);
			/*
			System.out.println("-------------------------------");		
			System.out.println("deltaV escape  : "+deltaV_esc);
			System.out.println("deltaV reach  : "+deltaV_reach);
			System.out.println("deltaV mission  : "+deltaV_mission);
			System.out.println("deltaV return  : "+deltaV_ret);
			System.out.println("deltaV toGEO  : "+deltaV_toG);
			
			System.out.println("-------------------------------");		
			System.out.println("masse escape  : "+masse_esc);
			System.out.println("masse reach  : "+masse_reach);
			System.out.println("masse mission  : "+masse_mission);
			System.out.println("masse return  : "+masse_ret);
			System.out.println("masse toGEO  : "+masse_toG);
			
			System.out.println("-------------------------------");		
			System.out.println("duree escape  : "+duree_esc);
			System.out.println("duree reach  : "+duree_reach);
			System.out.println("duree mission  : "+duree_mission);
			System.out.println("duree return  : "+duree_ret);
			System.out.println("duree toGEO  : "+duree_toG);

			
			System.out.println("-------------------------------");*/		
			double deltaV_tot=deltaV_esc+deltaV_reach+deltaV_mission+deltaV_ret+deltaV_toG;
			double masse_tot=masse_esc+masse_reach+masse_mission+masse_ret+masse_toG;
			double duree_tot=duree_esc+duree_reach+duree_mission+duree_ret+duree_toG;
			ArrayList<Double> traj=new ArrayList<Double>();
			traj.add(deltaV_tot);
			traj.add(masse_tot);
			traj.add(duree_tot);
			//System.out.println("delta V total  : "+ deltaV_tot);
			//System.out.println("Masse totale  : "+masse_tot);
			//System.out.println("Duree totale  : "+duree_tot);
			
			if (traj_reach.get(3)==0) {
				//System.out.println("Atteinte impossible en low thrust");
			}
			if (traj_return.get(3)==0) {
				//System.out.println("Retour impossible en low thrust");
			}
			
			
			
			return traj;
		}
}
 package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import infos.Constant;
import cargo.*;
import asteroid.*;
import cost.*;
import trajectory.*;
import ui.*;

public class Mission {

	/** Mission attributs **/
	public Asteroid target;		// nom de l asteroide
	public Cargo nostromo;
	Launcher launcher;

	public double distance;		// distance totale a parcourir (ou du parcours jusqu a l asteroide ?) (m)
	public double duration;		// duree totale de la mission (ou du parcours jusqu a l asteroide ?) (s)
	public double dV_total;
	
	public double power; // definir l utilisation exacte
	//others
	
	private double factor_test = 1.2;

	Mission(Asteroid target, double returning_mass, Propulsion propulsion, Launcher launcher) {
		this.target = target;
		this.launcher = launcher;
		this.nostromo = new Cargo(propulsion, returning_mass);
		//... depending on others parts, if something is needed
			
		// ...
	}
	Mission(Asteroid target, Mass cargo_mass, Propulsion propulsion, Launcher launcher) {
		this.target = target;
		this.launcher = launcher;
		this.nostromo = new Cargo(propulsion, cargo_mass);
	}
	
	/** Print mission details **/
	public void showDetails(){
		System.out.println("Mission details:");
		System.out.println("	-target: "+this.target.getAsteroid().toString());
		System.out.println("	-distance done: "+this.distance+" m");
		System.out.println("	-mission duration: "+this.date(this.duration));
		System.out.println("	-launcher used: "+this.launcher.getLauncher().toString()+"\n");
	}
	
	/** date conversion **/
	public String date(double duration) {
		int years = (int) duration / Constant.s2y;
		int months = (int) (duration % Constant.s2y) / Constant.s2m;
		int days = (int) (duration % Constant.s2m) / Constant.s2d;
		int hours=(int) (duration % Constant.s2d) / Constant.s2h;
		int minutes=(int) (duration % Constant.s2h) / Constant.s2min;
		int secondes=(int) duration % Constant.s2min;
		
		if(years > 0)
			return Integer.toString(years)+"y "+Integer.toString(months)+"m "
					+Integer.toString(days)+"d | "+Integer.toString(hours)+ "h "
					+Integer.toString(minutes)+"min "+Integer.toString(secondes)+"s";
		else if(months > 0)
			return Integer.toString(months)+"m "+Integer.toString(days)+"d | "
					+Integer.toString(hours)+ "h "+Integer.toString(minutes)+"min "
					+Integer.toString(secondes)+"s";
		else if(days > 0)
			return Integer.toString(days)+"d | "+Integer.toString(hours)+ "h "
			+Integer.toString(minutes)+"min "+Integer.toString(secondes)+"s";
		else if(hours > 0)
			return Integer.toString(hours)+ "h "+Integer.toString(minutes)
					+"min "+Integer.toString(secondes)+"s";
		else if(minutes > 0)
			return Integer.toString(minutes)+"min "+Integer.toString(secondes)+"s";
		else 
			return Integer.toString(secondes)+"s";
			
	}
	
	/** dV conversion **/
	public String getdV() {
		if(this.dV_total > 1e6) 
			return ((this.dV_total - this.dV_total%1e4) / 1e6)+" *1e3 km/s";
		else if(this.dV_total > 1e3)
			return ((this.dV_total - this.dV_total%10) / 1e3)+" km/s";
		else
			return this.dV_total+" m/s";
	}

	public void print_cost(){
		Propulsion ionic = Propulsion.SPT230_fakel_russe;
		ionic.setnEngine(4);
		Launcher launcher = Launcher.FalconHeavy_Escape;
		Mass cargo_mass = new Mass(1000,ionic.getDryMass()*ionic.getnEngine(),500);
		CostModel mission_cost = new CostModel(this.duration,cargo_mass,this.power,this.factor_test);
		System.out.println("Mission total cost: "+mission_cost.cost()+"�.");
//		System.out.println("Mission cost per kg: "+mission_cost.KgCost(this.nostromo.getMass().getTransportableMass())+"�.");
		System.out.println("Mission cost per kilometer: "+mission_cost.KilometerCost(this.distance)+"�.");	
	}
	
	public CostModel getcost(){
		Propulsion ionic = Propulsion.SPT230_fakel_russe;
		ionic.setnEngine(4);
		Launcher launcher = Launcher.FalconHeavy_Escape;
		Mass cargo_mass = new Mass(1000,ionic.getDryMass()*ionic.getnEngine(),500);
		return new CostModel(this.duration,cargo_mass,this.power,this.factor_test);
	}
	
	
	/** Calculate the distance, duration etc... of the mission **/
	public void launch(double mineral, Propulsion propu) {
		
		double m0 = 8000; // masse cargo
		double m1 = mineral; // masse minerai
		
		double total_thrust = this.nostromo.getPropulsion().getThrust()*this.nostromo.getPropulsion().getnEngine(); 
		double ISP = this.nostromo.getPropulsion().getISP();
		
		
		double duration = 250;
		
		/**********************Dorian part******************************/
		Escape_Earth traj = new Escape_Earth(m0,total_thrust,ISP);
		ArrayList<Double> output_escape = traj.LEO_to_Escape(1000000);
		Reach_Asteroid reach = new Reach_Asteroid(ISP);
		ArrayList<Double> output_reach = reach.rdv(10*Constant.deg2rad, duration, total_thrust, m0);
		Return_To_Earth coming_back = new Return_To_Earth(ISP);
		ArrayList<Double> output_back = coming_back.rdv(10*Constant.deg2rad, duration*8, total_thrust, m0,m1); // duration *2, depend de l'entree
		
		double dV_total = output_escape.get(0) + output_reach.get(0) + output_back.get(0);
		double dM_total = output_escape.get(1) + output_reach.get(1) + output_back.get(1);
		double duration_total = output_escape.get(2) + output_reach.get(2) + output_back.get(2);
		
		System.out.println("\ndV escape: "+output_escape.get(0)+"      dV reach: "+output_reach.get(0)+"     dV back: "+output_back.get(0));
		System.out.println("dM escape: "+output_escape.get(1)+"      dM reach: "+output_reach.get(1)+"     dM back: "+output_back.get(1)+"\n");
		
		System.out.println("\n Pour une masse de "+m0+" kg, on a besoin de "+dM_total+" kg de carburant.");
		System.out.println("dV total: "+dV_total+" m/s.   Dur�e totale: "+duration_total+" d. \n");
		this.dV_total = dV_total;
		Mass masstot = new Mass(1000,propu.getDryMass()*propu.getnEngine(), dM_total);
		masstot.UpdateTotalMass();
		masstot.showDetails();
		//System.out.println("Masse totale recalculée :  " + masstot.getTotalMass());
		
	}
	
	
	
	
	
	public static void main(String[] args) {
		
		/**Mission Creation**/
		Asteroid target = Asteroid.ARM_341843_2008_EVS;
		Propulsion ionic = Propulsion.SPT230_fakel_russe;
		ionic.setnEngine(4);
	//	Panel solar_panel = new Panel(mission);
		Launcher launcher = Launcher.FalconHeavy_Escape;
		Mass cargo_mass = new Mass(1000,ionic.getDryMass()*ionic.getnEngine(),500);
		
		Mission first_try = new Mission(target, cargo_mass, ionic,launcher);
		
		
		/** Tests **/
		first_try.duration = 1e8;
		first_try.distance = 1.5e10;
		double mass_wanted = 10000;
		
		
		/** Mission launching **/
		first_try.showDetails();
		first_try.target.showDetails();
		first_try.nostromo.showDetails();
		first_try.nostromo.getPropulsion().showDetails();
		first_try.nostromo.getMass().showDetails();
		
		/** Trajectory test **/
		first_try.launch(mass_wanted, ionic);
		
		/** IHM **/
		//Window.WindowLaunch(first_try);
		
		/** Cost part **/
		System.out.println(first_try.getcost().costStr());
	}
	
}

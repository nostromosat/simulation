
 package main;

 import java.util.ArrayList;

 import infos.Constant;
 import cargo.*;
 import asteroid.*;
 import cost.*;
 import trajectory.*;

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
 		Mass cargo_mass = new Mass(1000,ionic.getDryMass()*ionic.getnEngine(),500,1000);
 		CostModel mission_cost = new CostModel(this.duration,this.nostromo.getMass(),this.power);
 		System.out.println("Mission total cost: "+mission_cost.cost()+"ï¿½.");
//	 		System.out.println("Mission cost per kg: "+mission_cost.KgCost(this.nostromo.getMass().getTransportableMass())+"ï¿½.");
 		System.out.println("Mission cost per kilometer: "+mission_cost.KilometerCost(this.distance)+"ï¿½.");	
 	}
 	
 	public CostModel getcost(){
 		this.launch(this.nostromo.getMass().getOreMass(),this.nostromo.getPropulsion());
 		System.out.println(this.duration);
 		System.out.println(this.nostromo.getMass().getDryMass());
 		System.out.println(this.nostromo.getPropulsion().getPower());
 		return new CostModel(this.duration,this.nostromo.getMass(),this.nostromo.getPropulsion().getPower());
 	}
 	
 	public double getKgCost(){
 		double got_mass = this.nostromo.getMass().getOreMass();

 		this.launch(got_mass,this.nostromo.getPropulsion());
 		return new CostModel(this.duration,this.nostromo.getMass(),this.nostromo.getPropulsion().getPower()).KgCost(got_mass);
 	}	
 	
 	/** Calculate the distance, duration etc... of the mission **/
 	public void launch(double mineral, Propulsion propu) {
 		
 		double m0 = 10000; // masse cargo
 		double m1 = mineral; // masse minerai
 		
 		double total_thrust = this.nostromo.getPropulsion().getThrust()*this.nostromo.getPropulsion().getnEngine(); 
 		double ISP = this.nostromo.getPropulsion().getISP();
 		
 		
 		/**********************Dorian part******************************/
 		for (int i=0;i<100;i++) {
 		
		ArrayList<Double> traj = Trajectory.computeTrajectoryHohmann(m0, m1, total_thrust, ISP, 100000);
		//ArrayList<Double> traj = Trajectory.computeTrajectoryEllipticGTO(m0, m1, total_thrust, ISP, 200000,36000000);

		//ArrayList<Double> traj = Trajectory.computeTrajectoryEllipticEscape(m0, m1, total_thrust, ISP); 		
		this.dV_total = traj.get(0);
		double dM_total=traj.get(1);
 		this.duration = traj.get(2);
 		Mass masstot = new Mass(this.nostromo.getPanel().getMass(),propu.getDryMass()*propu.getnEngine(), dM_total,m1);
 		masstot.UpdateTotalMass();
 		//masstot.showDetails();
 		m0=masstot.getTotalMass();
 		this.nostromo.setMass(masstot);
 		
 		System.out.println("Masse totale recalculee :  " + masstot.getTotalMass());
 		}
 		
 		
 	}
 	
 	
 	
 	
 	
 	public static void main(String[] args) {

 		double mass_wanted = 10000;
 		
 		/**Mission Creation**/
 		Asteroid target = Asteroid.ARM_341843_2008_EVS;
 		Propulsion ionic = Propulsion.SPT230_fakel_russe;
 		ionic.setnEngine(4);
 		Launcher launcher = Launcher.Ariane64_LEO;
 		Panel pan = new Panel(ionic, target);
 		System.out.println(pan.getMass());
 		Mass cargo_mass = new Mass(pan.getMass(),ionic.getDryMass()*ionic.getnEngine(),500,mass_wanted);
 		
 		Mission first_try = new Mission(target, cargo_mass, ionic,launcher);	
 		first_try.nostromo.setPanel(pan);
 		
 		/** Tests **/
 		first_try.duration = 1e8;
 		first_try.distance = 1.5e10;
 		
 		/** Mission launching **/
 		first_try.showDetails();
 		first_try.target.showDetails();
 		first_try.nostromo.showDetails();
 		first_try.nostromo.getPropulsion().showDetails();
 		first_try.nostromo.getMass().showDetails();
 		
 		/** Trajectory test **/
 		//first_try.launch(mass_wanted, ionic);
 		
 		/** IHM **/
 		//Window.WindowLaunch(first_try);
 		
 		/** Cost part **/

 		System.out.println(first_try.getcost().costStr());

 	}
 	
 }
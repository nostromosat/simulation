package main;

import java.util.ArrayList;
import java.util.List;

import cost.CostModel;
import cargo.Launcher;
import cargo.Mass;
import cargo.Panel;
import cargo.Propulsion;
import asteroid.Asteroid;

public class Optimization {

	private static String costformat(double cost){
		if(cost>1e10)
			return ""+Math.round(cost/1e7)/1e2+" G€";
		else if(cost>1e7)
			return ""+Math.round(cost/1e4)/1e2+" M€";
		else if(cost>1e4)
			return ""+Math.round(cost/10)/1e2+" k€";
		else 
			return ""+Math.round(cost/1e-2)/1e2+" €";
	}


	public static void main(String[]args){

		long startTime = System.currentTimeMillis();

		List<Mission> missions_done = new ArrayList<Mission>();
		Asteroid target = Asteroid.ARM_341843_2008_EVS;
		int n = 0;
		int nMax = 6;
		int best_ind = 0;
		double min_price = Double.MAX_VALUE;

		// Il faut fixer la masse des panneaux plus efficacement
		int fuel_mass = 5000;
		int max_ore_mass = 15000;
		Propulsion propu = Propulsion.PPS1350_safran;

		for(int ore_mass=250;ore_mass<=max_ore_mass;ore_mass += 250){
				//for(Propulsion propu : Propulsion.values()){
					for(int i=1;i<=nMax;i++){
						for(Launcher launch : Launcher.values()){
							n++;
							propu.setnEngine(i);
							Panel pan = new Panel(propu,target);
							Mass miss_mass = new Mass(pan.getMass(),
									propu.getDryMass()*propu.getnEngine(),
									fuel_mass,
									ore_mass
									);
							Mission miss = new Mission(target,miss_mass,propu,launch,pan);
							double kgcost =miss.getKgCost();
							//System.out.println("kgCost: "+kgcost);
							if(kgcost < min_price){
								System.out.println("Ore: "+ore_mass+" -- "+
													propu.getnEngine()+" "+
													propu.getName()+" "+
													launch.getLauncher()+" "+
													launch.getOrbit()+" "+
													" = "+kgcost+" €/kg"
													);
								missions_done.add(miss);
								min_price = kgcost;
								best_ind = missions_done.size()-1;
					//		}
						}
					}
				}
			} 
		System.out.println("Number of cases tried: "+n+".");
		System.out.println("Minimum price per kg achieved: "+costformat(min_price)+" for mission n°"+best_ind+".");


		long endTime = System.currentTimeMillis();

		System.out.println("\nThat took " + Math.round((endTime - startTime)/10)/100 + " seconds");
		
		Mission best = missions_done.get(best_ind);
		System.out.println("Best case caracteristics:");
		best.launcher.showDetails();
		best.nostromo.showDetails();
		best.nostromo.getMass().showDetails();
		System.out.println("");
		best.nostromo.getPropulsion().showDetails();
		System.out.println("Mission using "+best.nostromo.getPropulsion().getnEngine()+" engines.\n");
		System.out.println(new CostModel(best.duration,
				best.nostromo.getMass(),
				best.nostromo.getPropulsion().getPower()).bothCosts(best.nostromo.getMass().getOreMass()));
		System.out.println("\ndV total: "+best.dV_total);
		System.out.println("Total duration: "+best.duration);		
	}
}

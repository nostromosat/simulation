package main;

import java.util.ArrayList;
import java.util.List;

import cost.CostModel;
import cargo.Launcher;
import cargo.Mass;
import cargo.Propulsion;
import asteroid.Asteroid;

public class Optimization {

	private static String costformat(double cost){
		if(cost>1e10)
			return ""+Math.round(cost/1e7)/1e9+" G€";
		else if(cost>1e7)
			return ""+Math.round(cost/1e4)/1e6+" M€";
		else if(cost>1e4)
			return ""+Math.round(cost/10)/1e3+" k€";
		else 
			return ""+Math.round(cost/1e-2)+" €";
	}


	public static void main(String[]args){

		long startTime = System.currentTimeMillis();

		List<Mission> missions_done = new ArrayList<Mission>();
		Asteroid target = Asteroid.ARM_341843_2008_EVS;
		int n = 0;
		int nMax = 5;
		int best_ind = 0;
		double min_price = Double.MAX_VALUE;

		// Il faut fixer la masse des panneaux plus efficacement, et surtout la masse du fuel !! ici 1000 panel, 2000 fuel.
		int panel_mass = 1000;
		int max_fuel_mass = 3000;
		int max_ore_mass = 5000;

		for(int ore_mass=500;ore_mass<max_ore_mass;ore_mass += 500){
			for(int fuel_mass=500; fuel_mass < max_fuel_mass; fuel_mass += 500){
				for(Propulsion propu : Propulsion.values()){
					for(int i=1;i<nMax;i++){
						for(Launcher launch : Launcher.values()){
							n++;
							propu.setnEngine(i);
							Mass miss_mass = new Mass(panel_mass,propu.getDryMass()*propu.getnEngine(),fuel_mass,ore_mass);
							Mission miss = new Mission(target,miss_mass,propu,launch);
							CostModel cost = miss.getcost();
							double kgcost = cost.KgCost(miss.nostromo.getMass().getOreMass());
							if(kgcost < min_price){
								missions_done.add(miss);
								min_price = kgcost;
								best_ind = missions_done.size();
							}
						}
					}
				}
			}
		}
		System.out.println("Number of cases tried: "+n+".");
		System.out.println("Minimum price per kg achieved: "+costformat(min_price)+" for mission n°"+best_ind+".");


		long endTime = System.currentTimeMillis();

		System.out.println("\nThat took " + Math.round((endTime - startTime)/10)/100 + " seconds");
	}
}

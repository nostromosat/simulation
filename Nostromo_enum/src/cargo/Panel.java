package cargo;

import asteroid.Asteroid;
import main.Mission;

public class Panel {
	
	double power;
	double surface;
	double mass;
	
	public static void test(Mission mission){
	/***************Power calculation*************/
	double power = mission.nostromo.propulsion.getPower()*mission.nostromo.propulsion.getnEngine() + 500 ; // Watts ?
	
	}

	public Panel(Mission mission){
		double rendement = 0.25;
		double transfert = 0.8;
		double puissance_Terre = 1360.8;
		double distance_Terre = 149.6*Math.pow(10,9);	 //distance en m
		double rapport_masse_surface = 4; // kg/m^2
		double distance_max_soleil = mission.target.getOrbit().getSemi_major_axis()*(1+mission.target.getOrbit().getEccentricity()); //m
		
		this.surface = power/(rendement*transfert*puissance_Terre*Math.pow(distance_Terre/distance_max_soleil,2));
		this.mass = surface*rapport_masse_surface;
	}
	
	public Panel(Propulsion propu, Asteroid target){
		this.power = propu.getPower()*propu.getnEngine();
		double rendement = 0.25;
		double transfert = 0.8;
		double puissance_Terre = 1360.8;
		double distance_Terre = 149.6*Math.pow(10,9);	 //distance en m
		double rapport_masse_surface = 4; // kg/m^2
		double distance_max_soleil = target.getOrbit().getSemi_major_axis()*(1+target.getOrbit().getEccentricity()); //m
		
		this.surface = power/(rendement*transfert*puissance_Terre*Math.pow(distance_Terre/distance_max_soleil,2));
		this.mass = surface*rapport_masse_surface;
	}
	
	public double getMass(){
		return this.mass;
	}
}
package cost;

import java.util.ArrayList;

public class CostModel {
	
	double duration;		//s
	double dry_mass;		// kg, whole cargo
	double power;			// Watt
	double factor_esa_nasa; // sans unite 
	
	/* factor_esa_nasa => Modele calculated on NASA consideration, factor needed to go back to an european
	 * mission cost
	 */
	
	ArrayList<Double> cost; //
	double global_cost;
	double detailed_cost;
	
	double sol_segment = 5000000 * duration;
	double inflation = 1.78;
	double plateform_mass;
    double structure_mass;
	double thermic_mass;
	double scao_mass;
	double power_mass;
	double telemetry_mass;
	double command_mass;
	double propulsion_mass;
	
	public CostModel(double duration, double dry_mass, double power, double factor) {
		this.duration = duration;
		this.dry_mass = dry_mass;
		this.power = power;
		this.factor_esa_nasa = factor;
		
		/** Calcul du cout haut niveau dans le constructeur du model **/
		double data_rate = 0.3;
		int life = 12*5;
		double new_techno = 1.3; 
		int interplanetary = 1;
		int year = 2019-1960;
		int complexity = 1;
		int experience = 1;
		this.global_cost = 2.829 + (Math.pow(dry_mass,0.457)) * (Math.pow(power,0.157)) *
				(Math.pow(2.718,0.171*data_rate)) * (Math.pow(2.718,0.00209*life)) *
				(Math.pow(2.718,1.52*new_techno)) * (Math.pow(2.718,0.258*interplanetary)) *
				(1/(Math.pow(2.718,0.0145*year))) * (Math.pow(2.718,0.467*complexity)) * (1/(Math.pow(2.718,0.237*experience)));
		this.global_cost = this.global_cost * inflation;
		this.global_cost = this.global_cost * factor_esa_nasa;
		this.global_cost = this.global_cost * 1e3; // from k� to �
		
		
		/** Calcul du cout bas niveau dans le constructeur du model **/
		this.plateform_mass = dry_mass;
		this.structure_mass = 0.24 * dry_mass;
		this.thermic_mass = 0.02 * dry_mass;
		this.scao_mass = 0.07 * dry_mass;
		this.power_mass = 0.16 * dry_mass;
		this.telemetry_mass = 0.045* dry_mass;
		this.command_mass = 0.02 * dry_mass;
		double propulsion_mass;
		double plateform_cost = 1064 + 35.5 * Math.pow(plateform_mass, 1.261);
		//double plateform_cost = 108 * plateform_mass;
		double structure_cost = 407 + 19.3 * structure_mass * Math.log10(structure_mass);
		//double structure_cost = 22.8 * dry_mass;
		double thermic_cost = 335 + 5.7 * Math.pow(thermic_mass, 2);
		//double thermic_cost = 22.8 * dry_mass;
		double scao_cost = 1850 + 11.7 * Math.pow(scao_mass, 2);
		//double scao_cost = 165.9 * dry_mass;
		double power_cost = 1261 + 539 * Math.pow(power_mass, 0.72);
		//double power_cost = 25.5 * dry_mass;
		double propulsion_cost = 89 + 3 * Math.pow(command_mass, 1.261);
		//double propulsion_cost = 44.1 * dry_mass;
		double telemetry_cost = 486 + 55.5 * Math.pow(telemetry_mass, 1.35);
		//double telemetry_cost = 82.5 * dry_mass;;
		double command_cost = 685 * 75 * Math.pow(command_mass, 1.35);
		//double command_cost = 97.8 * dry_mass;
		double payload_cost = 0.4 * plateform_cost;
		double ait_cost = 0.139 * plateform_cost;
		//double ait_cost = 0.195 * (plateform_cost+ payload_cost);
		double program_cost = 0.229 * plateform_cost;
		//double program_cost = 0.357 * (plateform_cost+ait_cost+payload_cost);
		double launch_cost = 0.061 * plateform_cost;
		//double launch_cost = 11.25* dry_mass;
		double groundsegment_cost = 0.066 * plateform_cost;
		//double groundsegment_cost = 5000*5;

		this.detailed_cost = inflation * (plateform_cost + structure_cost + thermic_cost + scao_cost + power_cost + propulsion_cost + telemetry_cost + command_cost + payload_cost + ait_cost + program_cost + launch_cost + groundsegment_cost);

		
		this.detailed_cost = this.detailed_cost * 1e3;  // from k� to �
	}
	
	/** Get function and cost per kilometers or kg **/
	public double cost() {
		return this.detailed_cost;
	}
	public double KilometerCost(double distance) {
		//Calcul du cout par kilometres de la mission.
		return this.detailed_cost / distance ;
	}
	public double KgCost(double transportable_mass){
		return this.detailed_cost / transportable_mass;
	}
	
	private static String simplified(double cost, double factor) {
		cost = cost/factor;
		return ((cost > 1e6) ?
				(cost - cost%1e4)/1e6 +" M�" : ((cost > 1e3) ?
						(cost - cost%10)/1e3 +" k�" : ((cost > 1) ?
								(cost*100 - (cost*100)%1)/100+" �" : (cost*1000 - cost*1000%1)+" e-3�")));
	}
	
	public String costStr(){
		return simplified(cost(),1);
	}
	public String otherCostStr(double factor){
		return simplified(cost(),factor);
	}
}

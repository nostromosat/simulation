package cost;

import java.util.ArrayList;

import cargo.Mass;

public class CostModel {
	
	double duration;		//s
	double power;			// Watt
	double factor_esa_nasa; // sans unite 
	
	/* factor_esa_nasa => Model calculated on NASA consideration, factor needed to go back to an european
	 * mission cost
	 */
	Mass m;
	ArrayList<Double> cost; //
	double global_cost;
	double detailed_cost;
	double dry_mass;
	double inflation = 1.78;
	double plateform_mass;
    double structure_mass;
	double thermic_mass;
	double scao_mass;
	double power_mass;
	double command_mass;
	double communication_mass;
	double propulsion_mass;
	
	public CostModel(double duration, Mass m, double power, double factor) {
		this.duration = duration;
		this.dry_mass = m.getDryMass();
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
		this.structure_mass = m.getSTRMass();
		this.thermic_mass = m.getTCMass();
		this.scao_mass = m.getAOGNCMass();
		this.power_mass = m.getPWRMass();
		this.command_mass = m.getDHMass();
		this.communication_mass = m.getCOMMass();
		this.propulsion_mass = m.getPROPUMass();
		double structure_cost = 646 * Math.pow(structure_mass + thermic_mass, 0.684);
		double thermic_cost = 0;
		double scao_cost = 324 * scao_mass;
		double power_cost = 64.3 * power_mass;
		double propulsion_cost = 20 * Math.pow(1000000, 0.485);
		double telemetry_cost = 26916;
		double communication_cost = 618 * communication_mass;
		double payload_cost = 0;
		double ait_cost = 0.357 * (telemetry_cost + propulsion_cost + power_cost + scao_cost + structure_cost);
		double program_cost = 0.357 * (ait_cost + telemetry_cost + propulsion_cost + power_cost + scao_cost + structure_cost);
		double launch_cost = 11.25 * dry_mass;
		double groundsegment_cost = 5000 * (duration/(60*60*24*365));
		this.detailed_cost = inflation * (structure_cost + thermic_cost + scao_cost + power_cost + propulsion_cost + telemetry_cost + communication_cost + payload_cost + ait_cost + program_cost + launch_cost + groundsegment_cost);

		
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

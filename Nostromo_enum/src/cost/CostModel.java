package cost;

import java.util.ArrayList;

import cargo.Mass;

public class CostModel {
	
	double duration;		//s
	double power;			// Watt
	
	 * mission cost
	 */
	Mass m;
	ArrayList<Double> cost; //
	double global_cost;
	double detailed_cost;
	double dry_mass;
	double inflation = 1.15;
	double plateform_mass;
    double structure_mass;
	double thermic_mass;
	double scao_mass;
	double power_mass;
	double command_mass;
	double communication_mass;
	double propulsion_mass;
	double volume_tank;
	public CostModel(double duration, Mass m, double power) {
		this.duration = duration;
		this.dry_mass = m.getDryMass();
		this.power = power;
		
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
		this.volume_tank = 1000000;
		// NR for non recurring cost
		double structure_cost_nr = 646 * Math.pow(structure_mass + thermic_mass, 0.684);
		double scao_cost_nr = 324 * scao_mass;
		double power_cost_nr = 64.3 * power_mass;
		double propulsion_cost_nr = 20 * Math.pow(volume_tank, 0.485);
		double command_cost_nr = 26916;
		double communication_cost_nr = 618 * communication_mass;
		double ait_cost_nr = 0.357 * (command_cost_nr + propulsion_cost_nr + power_cost_nr + scao_cost_nr + structure_cost_nr);
		double program_cost_nr = 0.357 * (ait_cost_nr + command_cost_nr + propulsion_cost_nr + power_cost_nr + scao_cost_nr + structure_cost_nr);
		// R for recurring cost
		double structure_cost_r = 22.6 * (structure_mass + thermic_mass);
		double scao_cost_r = 795 * Math.pow(scao_mass, 0.593);
		double power_cost_r = 32.4 * power_mass;
		double propulsion_cost_r = 29 * propulsion_mass;
		double command_cost_r = 883.7 * Math.pow(command_mass, 0.491);
		double communication_cost_r = 189 * communication_mass;
		double ait_cost_r = 0.124 * (communication_cost_r + structure_cost_r + scao_cost_r + power_cost_r + propulsion_cost_r + command_cost_r);
		double program_cost_r = 0.320 * (communication_cost_r + structure_cost_r + scao_cost_r + power_cost_r + propulsion_cost_r + command_cost_r + ait_cost_r);
		double launch_cost = 11.25 * dry_mass + 5850;
		double groundsegment_cost = 5000 * (duration/(60*60*24*365));
		this.detailed_cost = 0.88429 * inflation * (structure_cost_nr + scao_cost_nr + power_cost_nr + propulsion_cost_nr + 
				command_cost_nr + communication_cost_nr + ait_cost_nr + program_cost_nr + 
				structure_cost_r + scao_cost_r + power_cost_r + propulsion_cost_r + command_cost_r + 
				communication_cost_r + ait_cost_r + program_cost_r + launch_cost + groundsegment_cost);
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

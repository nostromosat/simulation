package cost;

public class CostTest {
	public static double CostJuice() {
		
		double plateform_mass = 1800;
		double structure_mass = 185;
		double thermic_mass = 62;
		double scao_mass = 30;
		double power_mass = 368;
		double command_mass = 21;
		double communication_mass = 73;
		double propulsion_mass = 44;
		double structure_cost = 646 * Math.pow(structure_mass + thermic_mass, 0.684);
		double thermic_cost = 0;
		double scao_cost = 324 * scao_mass;
		double power_cost = 64.3 * power_mass;
		double propulsion_cost = 20 * Math.pow(750000, 0.485);
		double telemetry_cost = 26916;
		double communication_cost = 618 * communication_mass;
		double payload_cost = 0;
		double ait_cost = 0.357 * (telemetry_cost + propulsion_cost + power_cost + scao_cost + structure_cost);
		double program_cost = 0.357 * (ait_cost + telemetry_cost + propulsion_cost + power_cost + scao_cost + structure_cost);
		double detailed_cost = 1.15 * 0.88429 * (structure_cost + thermic_cost + scao_cost + power_cost + propulsion_cost + telemetry_cost + communication_cost + payload_cost + ait_cost + program_cost);
		detailed_cost = detailed_cost * 1e-3;
		System.out.println("Cost Juice = " + detailed_cost+ " en millions euros");
		return detailed_cost;
		
	}
	public static double CostBepi() {
		
		double plateform_mass = 2700;
		double structure_mass = 277;
		double thermic_mass = 94;
		double scao_mass = 45;
		double power_mass = 552;
		double command_mass = 32;
		double communication_mass = 109;
		double propulsion_mass = 66;
		double structure_cost = 646 * Math.pow(structure_mass + thermic_mass, 0.684);
		double thermic_cost = 0;
		double scao_cost = 324 * scao_mass;
		double power_cost = 64.3 * power_mass;
		double propulsion_cost = 20 * Math.pow(750000, 0.485);
		double telemetry_cost = 26916;
		double communication_cost = 618 * communication_mass;
		double payload_cost = 0;
		double ait_cost = 0.357 * (telemetry_cost + propulsion_cost + power_cost + scao_cost + structure_cost);
		double program_cost = 0.357 * (ait_cost + telemetry_cost + propulsion_cost + power_cost + scao_cost + structure_cost);
		double detailed_cost = 1.15 * 0.88429 * (structure_cost + thermic_cost + scao_cost + power_cost + propulsion_cost + telemetry_cost + communication_cost + payload_cost + ait_cost + program_cost);
		detailed_cost = detailed_cost * 1e-3;
		System.out.println("Cost BepiColombo = " + detailed_cost + " en millions euros");
		return detailed_cost;
		
	}
	public static double CostExoMars() {
		
		double plateform_mass = 4332;
		double structure_mass = 445;
		double thermic_mass = 151;
		double scao_mass = 73;
		double power_mass = 886;
		double command_mass = 51;
		double communication_mass = 176;
		double propulsion_mass = 106;
		double structure_cost = 646 * Math.pow(structure_mass + thermic_mass, 0.684);
		double thermic_cost = 0;
		double scao_cost = 324 * scao_mass;
		double power_cost = 64.3 * power_mass;
		double propulsion_cost = 20 * Math.pow(500000, 0.485);
		double telemetry_cost = 26916;
		double communication_cost = 618 * communication_mass;
		double payload_cost = 0;
		double ait_cost = 0.357 * (telemetry_cost + propulsion_cost + power_cost + scao_cost + structure_cost);
		double program_cost = 0.357 * (ait_cost + telemetry_cost + propulsion_cost + power_cost + scao_cost + structure_cost);
		double detailed_cost = 0.95 * 1.10 * (structure_cost + thermic_cost + scao_cost + power_cost + propulsion_cost + telemetry_cost + communication_cost + payload_cost + ait_cost + program_cost);
		detailed_cost = detailed_cost * 1e-3;
		System.out.println("Cost ExoMars = " + detailed_cost + " en millions d'euros");
		return detailed_cost;
		
	}
	public static void main(String[] args) {
		CostJuice();
		CostBepi();
		CostExoMars();
		
	}
}

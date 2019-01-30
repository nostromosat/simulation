 package cargo;

public class Cargo {
	double diameter; //m
	double length; //m
	Mass cargo_mass;
	Propulsion propulsion;
	Panel panel;
	
	/** 
	 * @param diameter
	 * @param length
	 * @param total_weight
	 * @param transportable_weight
	 * @param propulsion
	 */
	
	/**** Constructors ****/
	public Cargo(double diameter, double length, double returning_mass, Propulsion propulsion, Panel panel) {
		this.diameter = diameter;
		this.length = length;
		this.cargo_mass = new Mass();	
		this.propulsion = propulsion;
		this.panel = panel;
	}
	public Cargo(Propulsion propulsion, double returning_mass) {
		this.diameter = 5;
		this.length = 5;
		this.cargo_mass = new Mass();
		this.cargo_mass.setTransportableMass(returning_mass);
		this.propulsion = propulsion;
	}
	public Cargo(Propulsion propulsion, Mass mass){
		this.propulsion = propulsion;
		this.cargo_mass = mass;
		this.diameter = 5;
		this.length = 5;
	}
	
	
	public void showDetails(){
		System.out.println("Cargo details:");
		System.out.println("	-diameter: "+this.diameter+" m");
		System.out.println("	-length: "+this.length+" m");
		System.out.println("	-total mass: "+this.cargo_mass.getTotalMass()+" kg");
		System.out.println("	-propulsion type: "+this.propulsion.name.toString()+"\n");
	}
	
	/**** "get" functions ****/
	public double getDiameter() {
		return this.diameter;
	}
	public double getLength() {
		return this.length;
	}
	public Mass getMass(){
		return this.cargo_mass;
	}
	public Propulsion getPropulsion() {
		return this.propulsion;
	}
}

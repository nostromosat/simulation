package cargo;

public class Mass {
	private double panel_mass;
	private double propulsion_mass;
	private double structure_mass;
	private double fuel_mass;
	private double transportable_mass;
	private double transported_mass;
	private double total_mass;
	
	
	/** Constructors **/
	public Mass(double propulsion, double panel, double fuel){
		this.panel_mass = panel;
		this.propulsion_mass = propulsion;
		this.fuel_mass = fuel;
		
		/** Melvin part **/
	
	}
	public Mass(){
		this.panel_mass = 0;
		this.structure_mass = 0;
		this.transportable_mass = 0;
		this.transported_mass = 0;
		this.fuel_mass = 0;
		this.total_mass = 0;
	}
	
	
	
	/** Calcul final (iteratif) de la masse totale **/
	public void FinalCalculation(double deltV) {
		
	}
	
	
	/** Get functions **/
	public double getPanelMass(){
		return this.panel_mass;
	}
	public double getStructureMass(){
		return this.structure_mass;
	}
	public double getFuelMass(){
		return this.fuel_mass;
	}
	public double getPropulsionMass(){
		return this.propulsion_mass;
	}
	public double getTransportableMass(){
		return this.transportable_mass;
	}
	public double getTransportedMass(){
		return this.transported_mass;
	}
	public double getTotalMass(){
		return this.total_mass;
	}
	public double getDryMass(){
		return this.panel_mass+this.structure_mass+this.propulsion_mass;
	}
	
	/** Set functions **/
	public void setPanelMass(double panel){
		this.panel_mass = panel;
		this.total_mass = this.panel_mass + this.structure_mass + this.propulsion_mass + this.fuel_mass + this.transported_mass;
	}
	public void setStructureMass(double struct){
		this.structure_mass = struct;
		this.total_mass = this.panel_mass + this.structure_mass + this.propulsion_mass + this.fuel_mass + this.transported_mass;
	}
	public void setPropulsionMass(double prop){
		this.propulsion_mass = prop;
		this.total_mass = this.panel_mass + this.structure_mass + this.propulsion_mass + this.fuel_mass + this.transported_mass;
	}
	public void setFuelMass(double fuel){
		this.fuel_mass = fuel;
		this.total_mass = this.panel_mass + this.structure_mass + this.propulsion_mass + this.fuel_mass + this.transported_mass;
	}
	public void setTransportableMass(double mass){
		this.transportable_mass = mass;
		this.total_mass = this.panel_mass + this.structure_mass + this.propulsion_mass + this.fuel_mass + this.transported_mass;
	}
	public void setTransportedMass(double mass){
		if(this.transportable_mass >= mass)
			this.transported_mass = mass;
		else 
			System.out.println("The cargo can't transport this much mass.\n");
		this.total_mass = this.panel_mass + this.structure_mass + this.propulsion_mass + this.fuel_mass + this.transported_mass;
	}
	
	public void showDetails(){
		System.out.println("Mass details:");
		System.out.println("	-panel mass: "+this.panel_mass+" kg");
		System.out.println("	-structure mass: "+this.structure_mass+" kg");
		System.out.println("	-propulsion mass: "+this.propulsion_mass+" kg");
		System.out.println("	-fuel mass: "+this.fuel_mass+" kg");
		System.out.println("	-transportable mass: "+this.transportable_mass+" kg");
		System.out.println("	-transported mass: "+this.transported_mass+" kg");
		System.out.println("	-total mass: "+this.total_mass+" kg\n");
	}
}

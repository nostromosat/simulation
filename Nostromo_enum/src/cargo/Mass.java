package cargo;
import infos.*;

public class Mass {
	
	private double fuel_mass;	// propellant
	private double ore_mass;	// asteroid's ore we carry
	private double container_factor = 0.05;
	private double total_mass;	// total mass
	private boolean panel_done = false;
	
	private double AOGNC_mass;	// attitude, orbit, guidance, navigation, control
	private double COM_mass;	// communications
	private double DH_mass;		// data handler
	private double HAR_mass;	// harness
	private double MEC_mass;	// mechanisms
	private double PROPU_mass;	// propulsion
	private double PWR_mass;	// power
	private double STR_mass;	// structure
	private double TC_mass;		// thermal control
	
	/** Constructors **/
	public Mass(double powerMass, double propulsionMass, double fuelMass){
		System.out.println(powerMass+"  "+propulsionMass+"  "+fuelMass);
		setPwrMass(powerMass);
		setPropuMass(Constant.HYDMF*(propulsionMass + fuelMass/Constant.Xe_density*Constant.TCD));
		setFuelMass(fuelMass);
		System.out.println(this.PROPU_mass);

		
		// mass distribution calculated on 5 different missions (% of total mass)
		double pAOGNC = 0.0205;	// 2.05%
		double pCOM = 0.0490;	// 4.90%
		double pDH = 0.0144;	// 1.44%
		double pHAR = 0.0289;	// 2.89%
		double pMEC = 0.0579;	// 5.79%
		double pPROPU = 0.1191;	// 11.91%
		double pPWR = 0.1402;	// 14.02%
		double pSTR = 0.1236;	// 12.36%
		double pTC = 0.0421;	// 4.21%
		
		// we've noticed that pPROPU varies a lot so let's calculate everything only using pPROPU and pPWR
		double estimatedTotalMass = (propulsionMass / pPROPU + powerMass / pPWR) / 2; // temporary estimation
		
		setAogncMass(estimatedTotalMass * pAOGNC);
		setComMass(estimatedTotalMass * pCOM);
		setDhMass(estimatedTotalMass * pDH);
		setHarMass(estimatedTotalMass * pHAR);
		setMecMass(estimatedTotalMass * pMEC);
		setStrMass(estimatedTotalMass * pSTR);
		setTcMass(estimatedTotalMass * pTC);
		
		// how much asteroid's ore will we carry ?
		setOreMass(0);
		System.out.println(this.fuel_mass);

	}
	public Mass(double powerMass, double propulsionMass, double fuelMass,double ore_mass){
		//System.out.println(powerMass+"  "+propulsionMass+"  "+fuelMass);
		setPwrMass(powerMass);
		setPropuMass(Constant.HYDMF*(propulsionMass + fuelMass/Constant.Xe_density*Constant.TCD));
		setFuelMass(fuelMass);
		//System.out.println(this.PROPU_mass);

		
		// mass distribution calculated on 5 different missions (% of total mass)
		double pAOGNC = 0.0205;	// 2.05%
		double pCOM = 0.0490;	// 4.90%
		double pDH = 0.0144;	// 1.44%
		double pHAR = 0.0289;	// 2.89%
		double pMEC = 0.0579;	// 5.79%
		double pPROPU = 0.1191;	// 11.91%
		double pPWR = 0.1402;	// 14.02%
		double pSTR = 0.1236;	// 12.36%
		double pTC = 0.0421;	// 4.21%
		
		// we've noticed that pPROPU varies a lot so let's calculate everything only using pPROPU and pPWR
		double estimatedTotalMass = (propulsionMass / pPROPU + powerMass / pPWR) / 2; // temporary estimation
		
		setAogncMass(estimatedTotalMass * pAOGNC);
		setComMass(estimatedTotalMass * pCOM);
		setDhMass(estimatedTotalMass * pDH);
		setHarMass(estimatedTotalMass * pHAR);
		setMecMass(estimatedTotalMass * pMEC);
		setStrMass(estimatedTotalMass * pSTR);
		setTcMass(estimatedTotalMass * pTC);
		
		// how much asteroid's ore will we carry ?
		setOreMass(ore_mass);

	}
	public Mass(){
		this.fuel_mass = 0;
		this.ore_mass = 0;
		this.total_mass = 0;
		this.AOGNC_mass = 0;
		this.COM_mass = 0;
		this.DH_mass = 0;
		this.HAR_mass = 0;
		this.MEC_mass = 0;
		this.PROPU_mass = 0;
		this.PWR_mass = 0;
		this.STR_mass = 0;
		this.TC_mass = 0;
	}
	
	/** Calcul final (iteratif) de la masse totale **/
	public void FinalCalculation(double deltV) {
		// to fill later
	}
	
	/** Update the value of total mass **/
	public void UpdateTotalMass() {
		this.total_mass = this.AOGNC_mass + this.COM_mass + this.DH_mass + this.HAR_mass +
				this.MEC_mass + this.PROPU_mass + this.PWR_mass + this.STR_mass +
				this.TC_mass + this.fuel_mass + this.ore_mass*this.container_factor;
	}
	
	/** Get functions **/
	public double getFuelMass(){
		return this.fuel_mass;
	}
	public double getOreMass(){
		return this.ore_mass;
	}
	
	public double getAOGNCMass(){
		return this.AOGNC_mass;
	}
	public double getCOMMass(){
		return this.COM_mass;
	}
	public double getDHMass(){
		return this.DH_mass;
	}
	public double getHARMass(){
		return this.HAR_mass;
	}
	public double getMECMass(){
		return this.MEC_mass;
	}
	public double getPROPUMass(){
		return this.PROPU_mass;
	}
	public double getPWRMass(){
		return this.PWR_mass;
	}
	public double getSTRMass(){
		return this.STR_mass;
	}
	public double getTCMass(){
		return this.TC_mass;
	}
	
	public double getTotalMass(){
		return this.total_mass;
	}
	public double getDryMass(){
		return this.total_mass - this.fuel_mass;
	}
	
	/** Set functions **/
	public void setFuelMass(double fuelMass){
		this.fuel_mass = fuelMass;
		UpdateTotalMass();
	}
	public void setOreMass(double oreMass){
		this.ore_mass = oreMass;
		UpdateTotalMass();
	}
	
	public void setAogncMass(double aogncMass){
		this.AOGNC_mass = aogncMass;
		UpdateTotalMass();
	}
	public void setComMass(double comMass){
		this.COM_mass = comMass;
		UpdateTotalMass();
	}
	public void setDhMass(double dhMass){
		this.DH_mass = dhMass;
		UpdateTotalMass();
	}
	public void setHarMass(double harMass){
		this.HAR_mass = harMass;
		UpdateTotalMass();
	}
	public void setMecMass(double mecMass){
		this.MEC_mass = mecMass;
		UpdateTotalMass();
	}
	public void setPropuMass(double propuMass){
		this.PROPU_mass = propuMass;
		UpdateTotalMass();
	}
	public void setPwrMass(double pwrMass){
		this.PWR_mass = pwrMass;
		UpdateTotalMass();
	}
	public void setStrMass(double strMass){
		this.STR_mass = strMass;
		UpdateTotalMass();
	}
	public void setTcMass(double tcMass){
		this.TC_mass = tcMass;
		UpdateTotalMass();
	}
	public void setPanelMass(double mass){
		if(!this.panel_done){
			this.PWR_mass += mass;
			UpdateTotalMass();
		}
		else 
			System.out.println("Panel mass already added.");
	}
	
	
	public void showDetails(){
		System.out.println("Mass details:");
		System.out.println(".....AOGNC: " + this.AOGNC_mass + " kg");
		System.out.println(".....Communication: " + this.COM_mass + " kg");
		System.out.println(".....Data Handling: " + this.DH_mass + " kg");
		System.out.println(".....Harness: " + this.HAR_mass + " kg");
		System.out.println(".....Mechanisms: " + this.MEC_mass + " kg");
		System.out.println(".....Propulsion & Tanks: " + this.PROPU_mass + " kg");
		System.out.println(".....Power: " + this.PWR_mass + " kg");
		System.out.println(".....Structure: " + this.STR_mass + " kg");
		System.out.println(".....Thermal control: " + this.TC_mass + " kg");
		System.out.println(".....Container: " + this.ore_mass*this.container_factor + " kg");

		System.out.println(".....");
		System.out.println(".....Propellant: " + this.fuel_mass + " kg");
		System.out.println(".....Asteroid's ore: " + this.ore_mass + " kg");
		System.out.println(".....Total: " + this.total_mass + " kg");
	}
}

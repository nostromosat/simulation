package cargo;
import infos.*;

public enum Propulsion {
	
	// Moteur type GIE
	NSTAR_boeing("Xe",8.2,3100,0.092,2570,9),
	XIPS25_l3_boeing("Xe",13.7,3550,0.0092,4250,9),
	T6_qnetiq_bepi_colombo("Xe",8.5,4000,0.125,4500,9),
	T7_qnetiq("Xe",13.2,4000,0.290,7000,5),
	NEXTC_aerojet_rocketdyne("Xe",12,4190,0.236,6300,6),
	RIT2X_ariane("Xe",10,2800,0.205,5000,6),
	
	// Moteur type a� effet Hall
	PPS1350_safran("Xe",5.3,1700,0.088,1500,9),
	PPS5000_safran("Xe",10,1950,0.250,5000,8),
	PPS20000_safran("Xe",40,2500,0.980,20000,4),
	XR5_aerojet_rocketdyne("Xe",10,1850,0.280,4500,9),
	AEPS_aerojet_rocketdyne("Xe",100,2800,0.589,13300,6),
	SPT230_fakel_russe("Xe",30,2700,0.784,15000,5);
	
	
	/** !! Dry mass verifications needed !! **/
	
	String name; 
	String typeOf_fuel;
	//String type_moteur;   // GIE ou HALL
	double dry_mass; 	//Quantit� em kg
	double ISP; 		// impulsion sp�cifique em s (n�cessaire aux calculs) 
	double thrust; 		// pouss�e max en N (n�cessaire aux calculs)
	double power;      // puissance max en W (n�cessaire aux calculs)
	int nEngine;	   // number of propulsors
	int TRL;		  //Technology Readiness Level (1-9)
	

	Propulsion(String type, double mass, double ISP, double thrust, double power, int TRL)  {
		this.name = Propulsion.this.name();
		this.typeOf_fuel = type;
		this.dry_mass = mass+power*Constant.HMPF+Constant.PPSM;
		this.ISP = ISP;
		this.thrust = thrust;
		this.power = power;
		this.nEngine = 1;
		this.TRL = TRL;
	}
	
	/** Get functions **/
	public String getName(){
		return this.name;
	}
	public String getType(){
		return this.typeOf_fuel;
	}
	public double getDryMass(){
		return this.dry_mass;
	}
	public double getISP(){
		return this.ISP;
	}
	public double getThrust(){
		return this.thrust;
	}
	public double getPower(){
		return this.power;
	}
	public int getnEngine(){
		return this.nEngine;
	}
	
	/** Set number of Engines **/
	public void setnEngine(int n){
		this.nEngine = n;
	}
	
	public static String[] list() {
		String[] list = new String[values().length];
		for(int i=0; i<values().length; i++){
			list[i] = values()[i].toString();
		}
		return list;
	}
	
	public void showDetails() {
		System.out.println("Propulsion characteristics: ");
		System.out.println("	-name: "+this.name.toString());
		System.out.println("	-type: "+this.typeOf_fuel);
		System.out.println("	-dry mass: "+this.dry_mass+" kg");
		System.out.println("	-ISP: "+this.ISP+" s");
		System.out.println("	-thrust: "+this.thrust+" N");
		System.out.println("	-power: "+this.power+" W");
		System.out.println("	-TRL: "+this.TRL+" \n");
	}
}


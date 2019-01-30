package asteroid;

import infos.Constant;

public enum OrbitAsteroid {
	ARM_341843_2008_EVS(1.03811726949*Constant.UA,
			0.958219*Constant.UA,
			7.437383*Constant.deg2rad,
			0.8783214*Constant.UA,
			342.606677255,
			0.0833816742,
			123.951114*Constant.deg2rad,
			93.3820225*Constant.deg2rad,
			234.8554*Constant.deg2rad),
	Hayabusa_162173_Ryugu(1.4159*Constant.UA,
			1.1896*Constant.UA,
			5.8837*Constant.deg2rad,
			0.96331*Constant.UA,
			473.9148,
			0.19022575,
			3.983244*Constant.deg2rad,
			251.6197*Constant.deg2rad,
			211.4258*Constant.deg2rad),
	OSIRISREX_101955_Bennu(0,0,0,0,0,0,0,0,0);
			
	String asteroid = OrbitAsteroid.this.toString();
	double aphelion; //m
	double semi_major_axis; //m
	double inclination; //rad
	double perihelion; //m
	double period; //day
	double eccentricity;
	double mean_anomaly; //rad
	double ascending_node; //rad
	double arg_perihelion; //rad
	double emoid;
	
	OrbitAsteroid(double aph, double axis, double incl, double peri, double period, double ecc, double ano, double asc, double arg) {
		this.aphelion = aph;
		this.semi_major_axis = axis;
		this.inclination = incl;
		this.perihelion = peri;
		this.period = period;
		this.eccentricity = ecc;
		this.mean_anomaly = ano;
		this.ascending_node = asc;
		this.arg_perihelion = arg;
	}

	public void showDetails(){
		System.out.println("Orbit details:");
		System.out.println("	-asteroid name: "+this.asteroid);
		System.out.println("	-aphelion: "+this.aphelion+" m");
		System.out.println("	-semi-major axis: "+this.semi_major_axis+" m");
		System.out.println("	-inclinaison: "+this.inclination+" rad");
		System.out.println("	-perihelion: "+this.perihelion+" m");
		System.out.println("	-period: "+this.period+ "days");
		System.out.println("	-eccentricity: "+this.eccentricity);
		System.out.println("	-mean anomaly: "+this.mean_anomaly+" rad");
		System.out.println("	-ascending node: "+this.ascending_node+" rad");
		System.out.println("	-arg perihelion: "+this.arg_perihelion+" rad");
		System.out.println("	-emoid: "+this.emoid+"\n");
	}

	/** Get functions **/
	public String getAsteroid() {
		return this.asteroid;
	}
	public double getAphelion() {
		return this.aphelion;
	}
	public double getSemi_major_axis() {
		return this.semi_major_axis;
	}
	public double getInclination() {
		return this.inclination;
	}
	public double getPerihelion() {
		return this.perihelion;
	}
	public double getPeriod() {
		return this.period;
	}
	public double getEmoid() {
		return this.emoid;
	}
	public double getEccentricity() {
		return this.eccentricity;
	}
	public double getMean_anomaly() {
		return this.mean_anomaly;
	}
	public double getAscending_node() {
		return this.ascending_node;
	}
	public double getArg_perihelion() {
		return this.arg_perihelion;
	}
	
	/** Set functions **/
	public void setAsteroid(String asteroid) {
		this.asteroid = asteroid;
	}
	public void setAphelion(double aphelion) {
		this.aphelion = aphelion;
	}
	public void setSemi_major_axis(double semi_major_axis) {
		this.semi_major_axis = semi_major_axis;
	}
	public void setInclination(double inclination) {
		this.inclination = inclination;
	}
	public void setPerihelion(double perihelion) {
		this.perihelion = perihelion;
	}
	public void setPeriod(double period) {
		this.period = period;
	}
	public void setEccentricity(double eccentricity) {
		this.eccentricity = eccentricity;
	}
	public void setMean_anomaly(double mean_anomaly) {
		this.mean_anomaly = mean_anomaly;
	}
	public void setAscending_node(double ascending_node) {
		this.ascending_node = ascending_node;
	}
	public void setArg_perihelion(double arg_perihelion) {
		this.arg_perihelion = arg_perihelion;
	}
	public void setEmoid(double emoid) {
		this.emoid = emoid;
	} 
}

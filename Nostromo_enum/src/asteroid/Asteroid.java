package asteroid;

import infos.Constant;
import asteroid.OrbitAsteroid;

public enum Asteroid {
	ARM_341843_2008_EVS("NEO",400,2e11,
			(1/160000)*Constant.g0,3.725*Constant.s2h,0.137,
			OrbitAsteroid.ARM_341843_2008_EVS),
	Hayabusa_162173_Ryugu("NEO",900,4.5e11,
			(1/80000)*Constant.g0,7.627*Constant.s2h,0.050,
			OrbitAsteroid.Hayabusa_162173_Ryugu),
	OSIRISREX_101955_Bennu("NEO",900,4.5e11,
			(1/80000)*Constant.g0,7.627*Constant.s2h,0.050,
			OrbitAsteroid.OSIRISREX_101955_Bennu);
	
	private String name = Asteroid.this.toString();
	private String typeOf_Asteroid;
	private double mean_diameter;
	private double mass;
	private double eq_surf_gravity;
	private double rotation_period;
	private double albedo;
	
	OrbitAsteroid orbit;
	
	Asteroid(String type, double diam, double mass,
			double grav, double period, double albedo, OrbitAsteroid orbit){
		this.typeOf_Asteroid = type;
		this.mean_diameter = diam;
		this.mass = mass;
		this.eq_surf_gravity = grav;
		this.rotation_period = period;
		this.albedo = albedo;
		this.orbit = orbit;
	}
	
	public static String[] list() {
		String[] list = new String[values().length];
		for(int i=0; i<values().length; i++){
			list[i] = values()[i].toString();
		}
		return list;
	}

	public void showDetails(){
		System.out.println("Asteroid characteristics:");
		System.out.println("	-name: "+this.name);
		System.out.println("	-type: "+this.typeOf_Asteroid);
		System.out.println("	-mean diameter: "+this.mean_diameter+" m");
		System.out.println("	-total mass: "+this.mass+" kg");
		System.out.println("	-gravity on surface: "+this.eq_surf_gravity+" m/s^2");
		System.out.println("	-rotation_period; "+this.rotation_period+" s");
		System.out.println("	-albedo: "+this.albedo+"\n"); 		
	}
	
	/** Get functions **/
	public String getAsteroid() {
		return this.name;
	}
	public String get_Asteroid_type(){
		return this.typeOf_Asteroid;
	}
	public double getMean_diameter() {
		return this.mean_diameter;
	}
	public double getMass() {
		return this.mass;
	}
	public double getEq_surf_gravity() {
		return this.eq_surf_gravity;
	}
	public double getRotation_period() {
		return this.rotation_period;
	}
	public double getAlbedo() {
		return this.albedo;
	}
	public OrbitAsteroid getOrbit() {
		return this.orbit;
	}
	
	/** Set functions **/
	public void setName(String name){
		this.name = name;
	}
	public void setMean_diameter(double mean_diameter) {
		this.mean_diameter = mean_diameter;
	}
	public void setMass(double mass) {
		this.mass = mass;
	}
	public void setEq_surf_gravity(double eq_surf_gravity) {
		this.eq_surf_gravity = eq_surf_gravity;
	}
	public void setRotation_period(double rotation_period) {
		this.rotation_period = rotation_period;
	}
	public void setAlbedo(double albedo) {
		this.albedo = albedo;
	}	
}

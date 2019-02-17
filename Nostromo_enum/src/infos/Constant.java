package infos;

public final class Constant {
	
	/** Constant Doubles **/
	public static final double UA = 1.496e11; // m
	public static final double g0 = 9.81; // m.s^-2
	public static final double deg2rad= Math.PI/180; // ø
	public static final double mu_T = 3.986004418e14; // m^3/s^2
	public static final double alt_SOI_T = 925000000.0; 
	public static final double RT = 6371000.0; // m
	public static final double muS = 132712440018e9; // m^3/s^2
	public static final double HMPF = 0.00372; // kg.W-1  Hardware mass-power factor
	public static final double Xe_density = 1722.04; // kg.m-3
	public static final double TCD = 94.4; // kg.m-3	tank capability density
	public static final double HYDMF = 1.1; // kg/kg		Hydraulic Mass Factor 
	public static final double PPSM = 10; // kg				Propulsion Pointing System Mass
	
	/** Constant Integers **/
	public static final int s2y = 60*60*8766; // seconds to years, 8766 = days*hours to over-pass the decimals in years.
	public static final int s2m = 60*60*24*30; // seconds to months
	public static final int s2d = 60*60*24; // seconds to days
	public static final int s2h = 60*60; // seconds to hours
	public static final int s2min = 60; // seconds to minute

}
package trajectory;

import infos.Constant;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class Reach_Asteroid {	
	double aphelion; //m
	double semi_major_axis; //m
	double inclination; //rad
	double perihelion; //m
	double meanmotion; //deg per day
	double eccentricity;
	double mean_anomaly; //rad
	double ascending_node; //rad
	double arg_perihelion; //rad
	double emoid;
	double Isp;
	
	final double sma_Earth=Constant.UA;
	final double inc_Earth=1.578690*Constant.deg2rad;
	final double ecc_Earth=	0.01671123;
	final double meanmotion_Earth=360/((double) Constant.s2y/Constant.s2d);
	final double MA_Earth=357.51716*Constant.deg2rad;
	final double AN_Earth=Constant.deg2rad*348.73936;
	final double argP_Earth=Constant.deg2rad*114.20783;
	final double rP_Earth=sma_Earth*(1-ecc_Earth);
	final double rA_Earth=sma_Earth*(1+ecc_Earth);
	
	public Reach_Asteroid(double Isp) {
		// NEA (PHA-O) 3414843_2008_EV5, epoch: 2458000.5
		this.Isp=Isp;
		this.aphelion = 1.0383*Constant.UA; 
		this.semi_major_axis = 0.9583*Constant.UA;
		this.inclination = 7.4368*Constant.deg2rad;
		this.perihelion = 0.8783*Constant.UA;
		this.meanmotion =1.047233762;
		this.eccentricity = 0.0835;
		this.mean_anomaly = 213.55*Constant.deg2rad;
		this.ascending_node = 93.390*Constant.deg2rad;
		this.arg_perihelion = 236.72*Constant.deg2rad;
		this.emoid=0.0149*Constant.UA;
	}
	
	/**
	 * on considère que l'orbite de la Terre et de l'asteroide sont les mêmes
	 * @param dephasage (rad)
	 * @param duree (jours)
	 * @param poussee (N)
	 * @param masse (kg)
	 * @return (deltaV (m/s), deltaM (kg), duree(jours))
	 */
	
	public ArrayList<Double> rdv(double dephasage, double duree, double poussee, double masse) {
		
		long epoch2008=1228518000;
		long epochJ2000=946728000;
		long epochdate=System.currentTimeMillis()/1000;
		double meananomaly=this.mean_anomaly+(this.meanmotion/86400)*(Math.PI/180)*(epochdate-epoch2008);
		double MA_T=this.MA_Earth+(this.meanmotion_Earth/86400)*(Math.PI/180)*(epochdate-epochJ2000);
		//double dephasage=((meananomaly-MA_T) % (2* Math.PI));
		System.out.println(dephasage);
		// regarder la périodicité et les mouvements moyens pour savoir quand lancer
		
		ArrayList<Double> list_param = new ArrayList<Double>();
		double t1=duree/2; //jours
		double acc=poussee/masse; //(m/s^2);
		System.out.println(acc);
		double ac_calc=-Constant.UA*dephasage/(3*t1*86400*86400*(duree-t1));
		while (Math.abs(ac_calc)>acc) {
			t1=t1-1;
			if (t1<0) {
				System.out.println("Duree trop courte ou déphasage trop grand");
				System.out.println(Math.abs(ac_calc));
				t1=duree/2; //jours
				break;
			}
			ac_calc=-Constant.UA*dephasage/(3*t1*86400*86400*(duree-t1));
		}
		ac_calc=-Constant.UA*dephasage/(3*t1*86400*86400*(duree-t1));

		double deltaV=2*Math.abs(ac_calc)*t1*86400;
		double deltaM=masse*(1-Math.exp(-deltaV/(this.Isp*Constant.g0)));
		list_param.add(deltaV);
		list_param.add(deltaM);
		list_param.add(duree);

		return list_param;
	}
	
	
	/**
	 * on considère que l'orbite de la Terre et de l'asteroide sont les mêmes
	 * @param dephasage (rad)
	 * @param duree (jours)
	 * @param poussee (N)
	 * @param masse (kg)
	 * @return (deltaV (m/s), deltaM (kg), duree(jours))
	 */
	
	public ArrayList<Double> rdvHohmann(double masse) {
		double mu=Constant.muS;
		double rT=this.sma_Earth;
		double rA=this.semi_major_axis;
		double a=0.5*(rT+rA);
		ArrayList<Double> list_param = new ArrayList<Double>();
		double deltaV1=-Math.sqrt(2*mu/rT-mu/a)+Math.sqrt(mu/rT);
		double deltaV2=-Math.sqrt(mu/rA)+Math.sqrt(2*mu/rA-mu/a);
		System.out.println(deltaV1);
		System.out.println(deltaV2);
		double deltaV=deltaV1 + deltaV2;
		double deltaM=masse*(1-Math.exp(-deltaV/(this.Isp*Constant.g0)));
		double dureeH=Math.PI*Math.sqrt(Math.pow(a, 3)/mu);
		
		list_param.add(deltaV);
		list_param.add(deltaM);
		list_param.add(dureeH/86400);

		return list_param;
	}
	
	
	public ArrayList<Double> rdvFastHohmann(double duree,double masse) {
		double mu=Constant.muS;
		double rT=this.sma_Earth;
		double rA=this.semi_major_axis;
		double a=0.5*(rT+rA);
		ArrayList<Double> list_param = new ArrayList<Double>();
		double deltaV1=-Math.sqrt(2*mu/rT-mu/a)+Math.sqrt(mu/rT);
		double deltaV2=-Math.sqrt(mu/rA)+Math.sqrt(2*mu/rA-mu/a);

		double deltaV=deltaV1 + deltaV2;
		double deltaM=masse*(1-Math.exp(-deltaV/(this.Isp*Constant.g0)));
		double dureeH=Math.PI*Math.sqrt(Math.pow(a, 3)/mu);
		double duration=dureeH/86400;
		System.out.println("ATTEINDRE "+duree);
		while (Math.abs((duration-duree))>10) {
			a=a*1.1;
			double eps=-mu/(2*a);
			deltaV1=Math.abs(Math.sqrt(2*(eps+mu/rA))-Math.sqrt(2*(eps+mu/rT)));
			double e=1-rT/a;
			System.out.println("ECC "+e);

			double p=a*(1-e*e);
			double vR=Math.acos((1/e)*(p/rA-1));
			System.out.println("vR "+(1/e)*(p/rA-1));

			double alpha=Math.atan(e*Math.sin(vR)/(1+e*Math.cos(vR)));
			deltaV2=Math.sqrt(2*(eps+mu/rA)+mu/rA-2*(mu/rA)*2*(eps+mu/rA)*Math.cos(alpha));
			double E=Math.acos((e+Math.cos(vR))/(1+e*Math.cos(vR)));
			double M=E-e*Math.sin(E);
			duration=M*Math.sqrt(a*a*a/mu)/86400;
			System.out.println("M  "+M*180/3.14);
			System.out.println("DUR  "+duration);
			deltaV=deltaV1+deltaV2;
			deltaM=masse*(1-Math.exp(-deltaV/(this.Isp*Constant.g0)));

		}
		
		list_param.add(deltaV);
		list_param.add(deltaM);
		list_param.add(duration);

		return list_param;
	}

}

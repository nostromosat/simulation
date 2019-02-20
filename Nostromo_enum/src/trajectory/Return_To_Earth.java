package trajectory;

import java.text.ParseException;
import java.util.ArrayList;

import infos.Constant;

public class Return_To_Earth {
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
	
	public Return_To_Earth(double Isp) {
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
	
	public ArrayList<Double> rdv(double dephasage, double duree, double poussee, double masse_sys, double masse_minerai ) {
		
		long epoch2008=1228518000;
		long epochJ2000=946728000;
	//	long epochdate=date.getTime();
	//	double meananomaly=this.mean_anomaly+(this.meanmotion/86400)*(Math.PI/180)*(epochdate-epoch2008);
	//	double MA_T=this.MA_Earth+(this.meanmotion_Earth/86400)*(Math.PI/180)*(epochdate-epochJ2000);
		
		// regarder la périodicité et les mouvements moyens pour savoir quand lancer
		
		ArrayList<Double> list_param = new ArrayList<Double>();
		double t1=duree/2; //jours
		double acc=poussee/(masse_sys+masse_minerai); //(m/s^2);
		System.out.println(acc);
		double ac_calc=-Constant.UA*dephasage/(3*t1*86400*86400*(duree-t1));
		while (Math.abs(ac_calc)>acc) {
			t1=t1-1;
			if (t1<0) {
				System.out.println("Duree trop courte ou déphasage trop grand");
				System.out.println(Math.abs(ac_calc));
				break;
			}
			ac_calc=-Constant.UA*dephasage/(3*t1*86400*86400*(duree-t1));
		}
		double deltaV=2*Math.abs(ac_calc)*t1*86400;
		System.out.println("COUCOUCUCOUC  "+(masse_sys+masse_minerai));
		double deltaM=(masse_sys+masse_minerai)*(1-Math.exp(-deltaV/(this.Isp*Constant.g0)));
		System.out.println("CEST MOI   "+(1-Math.exp(-deltaV/(this.Isp*Constant.g0))));
		list_param.add(deltaV);
		list_param.add(deltaM);
		list_param.add(duree);

		return list_param;
	}
	
	public ArrayList<Double> rdvHohmann(double masse,double masse_minerai, double poussee) {
		double mu=Constant.muS;
		double rT=this.sma_Earth;
		double rA=this.semi_major_axis;
		double a=0.5*(rT+rA);
		ArrayList<Double> list_param = new ArrayList<Double>();
		double deltaV1=Math.sqrt(2*mu/rA-mu/a)-Math.sqrt(mu/rA);
		double deltaV2=Math.sqrt(mu/rT)-Math.sqrt(2*mu/rT-mu/a);
		//System.out.println(deltaV1);
		//System.out.println(deltaV2);
		double deltaV=deltaV1 + deltaV2;
		
		double n=rT/rA;
		double factor_lT=1/(Math.sqrt(2*(1+2*Math.sqrt(n)/(n+1)))-1);
		deltaV=factor_lT*deltaV;
		double deltaM=(masse+masse_minerai)*(1-Math.exp(-deltaV/(this.Isp*Constant.g0)));
		double dureeH=Math.PI*Math.sqrt(Math.pow(a, 3)/mu);
		double deltaV_inc=2*Math.sqrt(mu/rA)*(Math.sin(0.5*(Math.abs(this.inc_Earth-this.inclination))));
		//System.out.println("INCLINATION "+deltaV_inc);
		deltaV=deltaV+deltaV_inc;

		list_param.add(deltaV);
		list_param.add(deltaM);
		list_param.add(dureeH/86400);
		list_param.add(1.0);

		// Verif compatible low thrust
		double acc=poussee/(masse+masse_minerai);
		double duree_lT=deltaV/acc;
		//System.out.println("Duree low thrust  "+  (duree_lT/86400));
		if (dureeH<2*duree_lT) {
			//System.out.println("IMPOSSIBLE LOW THRUST");
			list_param.set(3, 0.0);

		}
		double synodic_period=((360/this.meanmotion)*(360/this.meanmotion_Earth))/(Math.abs((360/this.meanmotion)-(360/this.meanmotion_Earth)));
		//System.out.println("synodic period  "+ synodic_period/365 +" years");
		return list_param;
	}
	
	/**
	 * @param dephasage (rad)
	 * @param duree (jours)
	 * @param poussee (N)
	 * @param masse (kg)
	 * @return (deltaV (m/s), deltaM (kg), duree(jours), 1 ou 0 low thrust, angle dephasage nécéssaire )
	 */
	public ArrayList<Double> rdvFastHohmann(double duree, double masse, double masse_minerai, double poussee) {
		double mu=Constant.muS;
		double rT=this.sma_Earth;
		double rA=this.semi_major_axis;
		double a=0.5*(rT+rA);
		ArrayList<Double> list_param = new ArrayList<Double>();
		double deltaV1=Math.sqrt(2*mu/rA-mu/a)-Math.sqrt(mu/rA);
		double deltaV2=Math.sqrt(mu/rT)-Math.sqrt(2*mu/rT-mu/a);

		double deltaV=deltaV1 + deltaV2;
		double deltaM=(masse+masse_minerai)*(1-Math.exp(-deltaV/(this.Isp*Constant.g0)));
		double dureeH=Math.PI*Math.sqrt(Math.pow(a, 3)/mu);
		double duration=dureeH/86400;
		//System.out.println(duration);
		double M=0;
		//System.out.println("ATTEINDRE "+duree+ " jours");
		while (Math.abs((duration-duree))>5) {
			a=a*1.0001;
			deltaV1=(-Math.sqrt(mu/rA)+Math.sqrt(2*mu/rA-mu/a));

			double e=1-rA/a;
			double E=Math.acos((1/e)*(1-rT/a));
			double vR=Math.acos((Math.cos(E)-e)/(1-e*Math.cos(E)));
			double alpha=Math.atan(e*Math.sin(vR)/(1+e*Math.cos(vR)));
			deltaV2=Math.sqrt(2*mu/rT-mu/a+mu/rT-2*Math.sqrt((mu/rT)*(2*mu/rT-mu/a))*Math.cos(alpha));
			M=E-e*Math.sin(E);

			duration=Math.abs(M)*Math.sqrt(a*a*a/mu)/86400;
			//System.out.println("duree transfert  "+duration);

			if (duration<duree) {
				break;
			}
			
			deltaV=deltaV1+deltaV2;
			deltaM=(masse+masse_minerai)*(1-Math.exp(-deltaV/(this.Isp*Constant.g0)));

		}
		//System.out.println("Angle dephasage  "+(180/3.14)*Math.abs(M));
		//System.out.println("dephasage par an  "+(this.meanmotion-this.meanmotion_Earth)*365);
		
		double n=rT/rA;
		double factor_lT=1/(Math.sqrt(2*(1+2*Math.sqrt(n)/(n+1)))-1);
		deltaV=factor_lT*deltaV;
		deltaM=(masse+masse_minerai)*(1-Math.exp(-deltaV/(this.Isp*Constant.g0)));
		double deltaV_inc=Math.sqrt(mu/rA)*(2-2*Math.cos(0.5*Math.PI*(this.inc_Earth-this.inclination)));
		deltaV=deltaV+deltaV_inc;
		list_param.add(deltaV);
		list_param.add(deltaM);
		list_param.add(duration);
		list_param.add(1.0);
		double acc=poussee/(masse+masse_minerai);
		double duree_lT=deltaV/acc;
		//System.out.println("Duree low thrust  "+  (duree_lT/86400));
		if (dureeH<duree_lT) {
			//System.out.println("IMPOSSIBLE LOW THRUST");
			list_param.set(3, 0.0);
		}
		list_param.add((180/3.14)*Math.abs(M));
		return list_param;
	}
	
	
	public ArrayList<Double> rdvBiElliptic(double duree,double masse, double masse_minerai, double poussee) {
		double mu=Constant.muS;
		double rT=this.sma_Earth;
		double rA=this.semi_major_axis;
		double a=0.5*(rT+rA);
		ArrayList<Double> list_param = new ArrayList<Double>();
		double deltaV1=Math.sqrt(2*mu/rA-mu/a)-Math.sqrt(mu/rA);
		double deltaV2=Math.sqrt(mu/rT)-Math.sqrt(2*mu/rT-mu/a);
		double deltaV3=0;
		double deltaV=deltaV1 + deltaV2;
		double deltaM=(masse+masse_minerai)*(1-Math.exp(-deltaV/(this.Isp*Constant.g0)));
		double dureeH=Math.abs(Math.PI)*Math.sqrt(rT*rT*rT/mu)+Math.abs(Math.PI)*Math.sqrt(a*a*a/mu);

		double duration=dureeH/86400;
		double rb=rT;
		//System.out.println(duration);
		double a1=0;
		double a2=0;
		//System.out.println("ATTEINDRE "+duree+ " jours");
		//System.out.println(duration+ " jours");

		while (Math.abs((duration-duree))>5) {
			rb=rb*1.001;
			a1=0.5*(rb+rA); //rT
			a2=0.5*(rb+rT); //a
			deltaV3=Math.abs(Math.sqrt(mu/rT)-Math.sqrt(2*mu/rT-mu/a1));
			deltaV2=Math.abs(Math.sqrt(2*mu/rb-mu/a1)-Math.sqrt(2*mu/rb-mu/a2));
			deltaV1=Math.abs(Math.sqrt(mu/rA)-Math.sqrt(2*mu/rA-mu/a2));
			//System.out.println("dV1 "+deltaV1);
			//System.out.println("dV2 "+deltaV2);
			//System.out.println("dV3 "+deltaV3);


			duration=Math.abs(Math.PI)*Math.sqrt(a1*a1*a1/mu)/86400+Math.abs(Math.PI)*Math.sqrt(a2*a2*a2/mu)/86400;;
			//System.out.println("duree transfert  "+duration);

			if (duration>duree) {
				break;
			}
			
			deltaV=deltaV1+deltaV2+deltaV3;
			deltaM=masse*(1-Math.exp(-deltaV/(this.Isp*Constant.g0)));

		}


		double deltaV_inc=2*Math.sqrt(mu/rT)*(Math.sin(0.5*(Math.abs(this.inc_Earth-this.inclination))));
		//	System.out.println("INCLINATION "+deltaV_inc);
		double deltaV0=deltaV;
		deltaV=deltaV+deltaV_inc;
		deltaM=masse*(1-Math.exp(-deltaV/(this.Isp*Constant.g0)));

		//System.out.println("Angle dephasage  "+(180/3.14)*Math.abs(Math.PI-M));
		//System.out.println("dephasage par an  "+(this.meanmotion-this.meanmotion_Earth)*365);
		list_param.add(deltaV);
		list_param.add(deltaM);
		list_param.add(duration);
		list_param.add(1.0);
		double acc=poussee/masse;
		double duree_lT=deltaV0/acc;
		//System.out.println("Duree low thrust  "+  (duree_lT/86400));
		if (dureeH<duree_lT) {
			//System.out.println("IMPOSSIBLE LOW THRUST");
			list_param.set(3, 0.0);
		}
		return list_param;
	}
	

	

}

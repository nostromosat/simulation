package trajectory;

import infos.Constant;

import java.util.ArrayList;

public class Escape_Earth {
	
	
	double mass; //kg masse totale du cargo
	double poussee; //N
	//String lanceur; mettre en entr�e les caract�ristiques du lanceur masse, altitude au d�collage en fonction 
	// de LEO, GTO ou escape 
	double Isp; //s Isp et pouss�e d�pendent du moteur consid�r�

	public Escape_Earth(double mass, double poussee, double isp) {
		this.mass = mass;
		this.poussee = poussee;
		this.Isp = isp;
	}
	
	// Renvoie ArrayList delta V, delta masse, temps (jours)
	public ArrayList<Double> LEO_to_Escape(double alt_LEO) { // l'altitude LEO en m � laquelle le lanceur peut amener sans 
												//compter le rayon de la TERRE
		double v0=Math.sqrt(Constant.mu_T/(alt_LEO+Constant.RT));
		double m0=this.mass;
		double v=v0;
		double vnew=v0;
		double dV=0.0;
		double dV_global=0;
		double m=m0;
		double m_c=0;
		double a=alt_LEO+Constant.RT;
		double t=0.0;
		int i=0;
		ArrayList<Double> list_param= new ArrayList<Double>();
		//System.out.println(a);
		while (a<Constant.alt_SOI_T) {
			a=a/Math.pow((1-this.poussee*Constant.s2d/(m*v)),2);
			vnew=Math.sqrt(Constant.mu_T/a);
			dV=vnew-v;
			dV_global=dV_global+Math.abs(dV);
			m_c=m_c+m*(1-Math.exp(-Math.abs(dV)/(this.Isp*Constant.g0)));
			v=vnew;
			i=i+1;
			t=t+Constant.s2d;
			//System.out.println(dV);
		}
		/**System.out.println("Terminé");
		System.out.println("Temps (jour): ");
		System.out.println(t/86400);
		System.out.println(i);
		System.out.println("Masse: ");
		System.out.println(m);
		*/
		list_param.add(dV_global);
		list_param.add(m_c);
		list_param.add(t/Constant.s2d); //en jours
		return list_param;
		
		

	}

	public double temps_LEO_to_Escape(double alt_LEO) {
		double f1=1/Math.sqrt(alt_LEO+Constant.RT)-1/Math.sqrt(Constant.alt_SOI_T);
		double f2=Math.sqrt(Constant.mu_T)/(this.poussee/this.mass);
		double T=f1*f2/86400;
		return T;
	}
	
	public ArrayList<Double> GTO_to_Escape(double alt_GTO_perigee, double alt_GTO_apogee) {
		// Raisonnement iso�nerg�tique
		// Aire de l'ellipse GTO
		double A_GTO=Math.PI*(alt_GTO_perigee+Constant.RT)*(alt_GTO_apogee+Constant.RT);
		// Rayon orbite circulaire �quivalent
		double Req=Math.sqrt(A_GTO/Math.PI);
		ArrayList<Double> list_param= new ArrayList<Double>();

		double v0=Math.sqrt(Constant.mu_T/Req);
		double m0=this.mass;
		double v=v0;
		double vnew=v0;
		double dV=0.0;
		double dV_global=0;
		double m=m0;
		double m_c=0;
		double a=Req;
		double t=0.0;
		int i=0;
		System.out.println(a);
		while (a<Constant.alt_SOI_T) {
			a=a/Math.pow((1-this.poussee*Constant.s2d/(m*v)),2);
			vnew=Math.sqrt(Constant.mu_T/a);
			dV=vnew-v;
			dV_global=dV_global+dV;
			m_c=m_c+m*(1-Math.exp(-dV/(this.Isp*Constant.g0)));
			v=vnew;
			i=i+1;
			t=t+Constant.s2d;
			System.out.println(a);
		}
		System.out.println("Termine");
		System.out.println("Temps (jour): ");
		System.out.println(t/86400);
		System.out.println(i);
		System.out.println("Masse: ");
		System.out.println(m);
		list_param.add(dV_global);
		list_param.add(m0-m);
		list_param.add(t/86400);
		return list_param;
	}
	
	public ArrayList<Double> Escape() {
		ArrayList<Double> list_param = new ArrayList<Double>();
		list_param.add(0.0);
		list_param.add(0.0);
		list_param.add(10.0);
		return list_param;
	}
	

	
}

package trajectory;

import infos.Constant;

import java.util.ArrayList;

public class InterplanetaryToGEO {
	
	
	double mass; //kg masse totale du cargo
	double poussee; //N
	//String lanceur; mettre en entr�e les caract�ristiques du lanceur masse, altitude au d�collage en fonction 
	// de LEO, GTO ou escape 
	double Isp; //s Isp et pouss�e d�pendent du moteur consid�r�

	public InterplanetaryToGEO(double mass, double poussee, double isp) {
		this.mass = mass;
		this.poussee = poussee;
		this.Isp = isp;
	}
	
	// Renvoie ArrayList delta V, delta masse, temps (jours)
	public ArrayList<Double> toGEO() { // l'altitude LEO en m � laquelle le lanceur peut amener sans 
												//compter le rayon de la TERRE
		double v0=Math.sqrt(Constant.mu_T/Constant.alt_SOI_T);
		double m0=this.mass;
		double v=v0;
		double vnew=v0;
		double dV=0.0;
		double dV_global=0;
		double m=m0;
		double a=Constant.alt_SOI_T;
		double t=0.0;
		int i=0;
		ArrayList<Double> list_param= new ArrayList<Double>();
		//System.out.println(a);
		while (a>Constant.RT+36000000) {
			a=a/Math.pow((1+this.poussee*Constant.s2d/(m*v)),2);
			//System.out.println(a/1000);
			vnew=Math.sqrt(Constant.mu_T/a);
			dV=vnew-v;
			dV_global=dV_global+Math.abs(dV);
			m=m+m*(1-Math.exp(-dV/(this.Isp*Constant.g0)));
			v=vnew;
			i=i+1;
			t=t+Constant.s2d;
		}
		//System.out.println("Terminé");

		list_param.add(dV_global);
		list_param.add(m-m0);
		list_param.add(t/Constant.s2d); //en jours
		return list_param;
		
		

	}

	


	
}


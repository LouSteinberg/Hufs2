package hufs2;

import java.util.Random;

public class Normal extends Distribution {

	public Random fRandom = new Random();
	
	public double mean;
	public double stDev;

	public Normal(double mean, double stDev){
		this.mean = mean;
		this.stDev = stDev;
	}	
	public double draw( ) {
		return mean + fRandom.nextGaussian() * stDev;
	}
	public static void main(String [ ] args) {
		Normal dist= new Normal(10.0, 5.0);
		int ct = 1000;
		double sum = 0;
		double sumsq = 0;
		for (int j = 0; j<ct; j++) {
			double drawn = dist.draw( );
			sum += drawn;
			sumsq += drawn*drawn;
			System.out.format("%g\n",drawn);
		}
		double mean = sum/ct;
		double stDev = Math.sqrt((ct*sumsq-sum*sum)/((ct-1)*ct));
		System.out.println("ct: "+ct);
		System.out.println("mean: "+mean);
		System.out.println("stDev: "+stDev);
	}
}

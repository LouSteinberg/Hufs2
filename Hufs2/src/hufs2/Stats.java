package hufs2;

import java.util.ArrayList;

public class Stats {

	public static double mean(ArrayList<Double> data){
		double sum = 0.0;
		for (double d: data){
			sum += d;
		}
		return sum/data.size(); 
	}

	public static double stDev(ArrayList<Double> data) {
		double sum = 0.0;
		double sumsq = 0.0;
		for (double d: data){
			sum += d;
			sumsq += d * d;
		}
		int ct = data.size( );
		return Math.sqrt((ct*sumsq-sum*sum)/((ct-1)*ct));
	}
}

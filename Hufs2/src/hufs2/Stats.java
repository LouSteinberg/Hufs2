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
		if(data.size( ) < 2) {
			return 0.0;
		}
		double sum = 0.0;
		double sumsq = 0.0;
		for (double d: data){
			sum += d;
			sumsq += d * d;
		}
		int ct = data.size( );
		return Math.sqrt((ct*sumsq-sum*sum)/((ct-1)*ct));
	}
	public static void printMeanStDev(String label, ArrayList<Double> data) {
		System.out.println(label);
		System.out.println("  mean: "+Stats.mean(data)+ ", stDev: "+ Stats.stDev(data));
	}
	
	public static void main(String  [ ] args) {
		ArrayList<Double> a = new ArrayList<Double>( );
		a.add(2.0);
		a.add(3.5);
		a.add(4.0);
		printMeanStDev("a", a);
		// mean is 3.16667, stdev is 1.04083
	}
}

package Math;

import java.util.function.Function;

public class Integral{



	public static double integrate (Function<Double, Double> fn, 
			                        double minX, double maxX, int steps) {
		double dX = (maxX - minX) / steps;
		double x = minX;
		double sum = fn.apply(minX) / 2.0;
		for(int i = 0; i<steps-1; i++) {
			x += dX;
			//	System.out.println("i "+i+",  x "+x );
			sum += fn.apply(x);
		}
		x += dX;
		sum = (sum + fn.apply(x)/2.0)*dX;
		// System.out.println("sum = "+sum);
		return sum;
	}
	public static void main(String[ ] args) {
		integrate(x -> x*x, 1.0, 3.0, 5 );
	}
}

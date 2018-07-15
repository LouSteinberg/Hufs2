package hufs2;

import java.util.function.Function;

public class Distribution {
    public double mean;
    public double stDev;
    public double sup;
    public double sub;
    public Function<Double, Double> pdf;
    
    public double draw() {
            return mean;
    }
   
	// set mean and stDev to empirical values
	public void setEmpiricalMeanDev( ) {
		int numSamples = 10000;  // number of samples to draw
		double sum = 0.0;
		double sumsq = 0.0;
		int count;
		for (count = 0; count < numSamples; count++) {
			double drawn = draw( );
			sum += drawn;
			sumsq += drawn * drawn;
		}
		mean = sum / numSamples;
		stDev = Math.sqrt((numSamples*sumsq - sum*sum)/(numSamples*(numSamples-1)));
	}
	
	public static void main(String [ ] args) {
		
	}
}

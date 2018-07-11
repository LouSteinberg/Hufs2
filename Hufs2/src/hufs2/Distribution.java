package hufs2;

import java.util.function.Function;

public class Distribution {
    public double mean;
    public double stdev;
    public double sup;
    public double sub;
    public Function<Double, Double> pdf;
    
    public double draw() {
            return mean;
    }
    
}

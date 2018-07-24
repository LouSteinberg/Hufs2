package saved;

import hufs2.Distribution;
import hufs2.NormalDistribution;

// the distribution of A - B

public class DifferenceDistribution extends Distribution{
	Distribution a;
	Distribution b;
	
	public DifferenceDistribution(Distribution a, Distribution b) {
		this.a = a;
		this.b = b;
		setEmpiricalMeanDev( );
	}
	public double draw( ) {
		return a.draw() - b.draw();
	}
	public static void main(String [ ] args) {
		Distribution x = new NormalDistribution(50.0, 10.0);
		Distribution y = new NormalDistribution(4.0, 10.0);
		Distribution z = new DifferenceDistribution(x, y);
		System.out.println("mean: "+z.mean+", stDev: "+z.stDev);
	
	}
	
	
}

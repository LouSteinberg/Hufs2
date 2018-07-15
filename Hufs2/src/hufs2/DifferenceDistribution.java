package hufs2;
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
	
	
}

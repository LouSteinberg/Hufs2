package hufs2;

import java.util.ArrayList;
import java.util.function.BinaryOperator;
//import java.util.function.Function;

public class Design {
	Level level;
	double quality;
	Design parent; 
	ArrayList<Design> children = new ArrayList<Design>(); 
	Distribution childScoreDistribution;
	Distribution childErrorDistribution;
	double score;
	double error;
	
	// create a non-top-level Design object
	public Design(Design parent) {
		Design child = this;
		child.parent = parent;
		parent.children.add(child);
		child.level = parent.level.levelDown;
		child.score = parent.childScoreDistribution.draw( );
		child.error = parent.childErrorDistribution.draw( );
		initialize(child);
	}
	// create a top-level Design object
	public Design(Level level) {
		this.parent = null;
		this.level = level;
		this.score = level.topScoreDistribution.draw( );
		this.error = level.topErrorDistribution.draw( );
		initialize(this);
	}
	// initialize does the part of the initialization of a newly created Design object
	// that is common between top-level and non-top-level designs.
	public static void initialize(Design newDesign){
		newDesign.quality = newDesign.score - newDesign.error;
		//newDesign.score = newDesign.level.scoreFromQuality(newDesign.quality);

		double cSDMean = newDesign.level.cSDMeanB + newDesign.level.cSDMeanM * newDesign.score;
		double cSDStDev = newDesign.level.cSDStDev;
		newDesign.childScoreDistribution =  new NormalDistribution(cSDMean, cSDStDev);				

		double cEDMean = newDesign.level.cEDMeanB + newDesign.level.cEDMeanM * newDesign.score;
		double cEDStDev = newDesign.level.cEDStDev;
		newDesign.childErrorDistribution =  new NormalDistribution(cEDMean, cEDStDev);				
		
			}
	// utility of this design (with score this.score) starting at time tau with given U_0
	public double utility(double tau, BinaryOperator<Double> u0) {
		double doneTime = tau - this.level.genTime;
		return level.utility(score, doneTime, u0);
	}
}
package hufs2;

import java.util.function.BinaryOperator;

public class Level {
	Level levelUp;
	Level levelDown;
	int number;
	
	double cSDMeanM;
	double cSDMeanB;
	double cSDStDev;

	double cEDMeanM;
	double cEDMeanB;
	double cEDStDev;

	
	double genTime; // time to generate a child of design at this level
	
	Distribution topErrorDistribution; // only used on top level
	Distribution topScoreDistribution;   // only used on top level

	public Level( ){		
	}
	
	public static Level[ ] initializedLevels(int numLevels) {
		Level[ ] levels = new Level [numLevels];
		for (int l = 0; l < numLevels; l++){    // for each level number
			levels[l] = new Level( );
		}
		int topNumber = numLevels-1;  // index & number of top level.
		
		levels[topNumber].topErrorDistribution = Hufs.TOPERRORDISTRIBUTION;
		levels[topNumber].topScoreDistribution = Hufs.TOPSCOREDISTRIBUTION;
		
		levels[0].number = 0;
		
		for(int l = 1; l <= topNumber; l++) {   // for each level except bottom
			levels[l].levelDown = levels[l-1]; 
			levels[l].number = l;
			levels[l].genTime = 1.0;
			
			levels[l].cSDMeanM = Math.pow(10.0, l);
			levels[l].cSDMeanB = l* 10.0;
			levels[l].cSDStDev = levels[l].cSDMeanM / 2.0;
			
			levels[l].cEDMeanM = 0.0;
			levels[l].cEDMeanB = 0.0;
			levels[l].cEDStDev = levels[l].cSDMeanM / 2.0;			
		}
		for(int l = 0; l <= topNumber-1; l++) {   // for each level except top
			levels[l].levelUp = levels[l+1]; 
		}
			
		return levels;
	}
	public boolean isTopLevel( ) {
		return levelUp == null;
	}
	public boolean isBottomLevel( ) {
		return levelDown == null;
	}
	// utility of having a hypothetical design at this level with given score at given tau
	public double utility(double score, double tau, BinaryOperator<Double> u0) {
		if (tau <= 0) {
			return 0.0;
		} else if (isBottomLevel( )) {
			return  u0.apply(score, tau);
		} else {
			Distribution csd = childScoreDistribution(score);
			double sub = csd.sub;
			double sup = csd.sup;
			double doneTime = tau - genTime;
			double doneParentUtility = utility(score, doneTime, u0);
			double parentUtility = 
					Integral.integrate((s -> (csd.pdf.apply(s) * 
					                          Math.max(this.levelDown.utility(s,doneTime, u0),
					                                   doneParentUtility))),
					                   sub, sup, 100);
			return parentUtility;
		}		
	}
	
	public Distribution childScoreDistribution(double childScore) {
		double cSDMean = cSDMeanB + cSDMeanM * childScore;
		return new NormalDistribution(cSDMean, cSDStDev);
	}
	public Distribution childErrorDistribution(double childScore) {
		double cEDMean = cEDMeanB + cEDMeanM * childScore;
		return new NormalDistribution(cEDMean, cEDStDev);
	}
	
	public static void main(String [ ] args) {
		Level[ ] res = initializedLevels(3);
		for(int l = 0; l < res.length; l++) {
		}
	}
}
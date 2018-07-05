package hufs2;

public class Level {
	Level levelUp;
	Level levelDown;

	double cQDMeanM;
	double cQDMeanB;
	double cQDStDev;
	
	Distribution topQualityDistribution; // only used on top level
	Distribution errorDistribution = new Normal(0.0, 2.0);

	public Level( ){		
	}
	public static Level[ ] initializedLevels(int numLevels) {
		Level[ ] levels = new Level [numLevels];
		for (int l = 0; l < numLevels; l++){    // for each level number
			levels[l] = new Level( );
		}
		int topNumber = numLevels-1;  // index & number of top level.
		levels[topNumber].topQualityDistribution = new Normal(10.0, 1.0);
		for(int l = 1; l <= topNumber; l++) {   // for each level except 0
			levels[l].levelDown = levels[l-1]; 
		}
		for(int l = 0; l <= topNumber-1; l++) {   // for each level except top
			levels[l].levelUp = levels[l+1]; 
		}

		return levels;
	}
	public double scoreFromQuality(double quality) {
		return quality + errorDistribution.draw();
	}
	public boolean isTopLevel( ) {
		return levelUp == null;
	}
	public boolean isBottomLevel( ) {
		return levelDown == null;
	}
	public static void main(String [ ] args) {
		Level[ ] res = initializedLevels(3);
		for(int l = 0; l < res.length; l++) {
			System.out.println(l+" "+res[l].errorDistribution.stdev );
		}
	}
}
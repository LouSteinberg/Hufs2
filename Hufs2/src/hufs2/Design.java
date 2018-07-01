package hufs2;


public class Design {
      Level level;
      Double quality;
      Design parent; 
      Distribution childQualityDistribution;
      double score;
      
      public Design(Design parent) {
              this.parent = parent;
              this.level = parent.level.levelDown;
        	  this.quality = parent.childQualityDistribution.draw( );
      
      }
      public Design(double quality, Level level) {
    	  Design child = new Design(this);
    	  double CQDMean = child.level.CQDMeanB + child.level.CQDMeanM * child.quality;
    	  double CQDStDev = child.level.CQDStDev;
    	  child.childQualityDistribution = new Normal(CQDMean, CQDStDev);
    	  
    	  return child;
      }
      
}
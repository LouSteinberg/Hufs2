package hufs2;


public class Design {
      Level level;
      Double quality;
      Design parent; 
      Distribution childQualityDistribution;
      double score;

      // create a non-top-level Design object
      public Design(Design parent) {
    	  Design child = this;
    	  child.parent = parent;
    	  child.level = parent.level.levelDown;
    	  child.quality = parent.childQualityDistribution.draw( );
    	  initialize(child);
      }
      // create a top-level Design object
      public Design(Level level) {
    	  this.parent = null;
    	  this.level = level;
    	  this.quality = level.topQualityDistribution.draw( );
    	  initialize(this);
      }
      // initialize does the part of the initialization of a newly created Design object
      // that is common between top-level and non-top-level designs.
      public static void initialize(Design newDesign){
    	  newDesign.score = newDesign.level.scoreFromQuality(newDesign.quality);
      	  
    	  double cQDMean = newDesign.level.cQDMeanB + newDesign.level.cQDMeanM * newDesign.quality;
    	  double cQDStDev = newDesign.level.cQDStDev;
    	  newDesign.childQualityDistribution = new Normal(cQDMean, cQDStDev);
      }
      
}
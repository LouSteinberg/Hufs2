package hufs2;

import java.util.ArrayList;
import java.util.function.Function;


public class Design {
      Level level;
      Double quality;
      Design parent; 
      Distribution childQualityDistribution;
      double score;

      // create a non-top-level Design object
      public Design(Design parent, ArrayList<Design> allDesigns) {
    	  Design child = this;
    	  child.parent = parent;
    	  child.level = parent.level.levelDown;
    	  child.quality = parent.childQualityDistribution.draw( );
    	  initialize(child, allDesigns);
      }
      // create a top-level Design object
      public Design(Level level, ArrayList<Design> allDesigns) {
    	  this.parent = null;
    	  this.level = level;
    	  this.quality = level.topQualityDistribution.draw( );
    	  initialize(this, allDesigns);
      }
      // initialize does the part of the initialization of a newly created Design object
      // that is common between top-level and non-top-level designs.
      public static void initialize(Design newDesign, ArrayList<Design> allDesigns){
    	  allDesigns.add(newDesign);
    	  newDesign.score = newDesign.level.scoreFromQuality(newDesign.quality);
      	  
    	  double cQDMean = newDesign.level.cQDMeanB + newDesign.level.cQDMeanM * newDesign.quality;
    	  double cQDStDev = newDesign.level.cQDStDev;
    	  newDesign.childQualityDistribution = new Normal(cQDMean, cQDStDev);
      }
      public double utility(ArrayList<Design> allDesigns, double tau, Function<Double, Double> u0) {
    	  if (tau <= 0) {
    		  return 0;
    	  } else if (level.isBottomLevel()) {
    		  return  u0.apply(score);
    	  } else {
    		  Distribution csd = parent.childQualityDistribution;
    		  double doneTime = tau - level.genTime;
    		  
    	  
    	  }
    			  }
      
}
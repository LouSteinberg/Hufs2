package hufs2;

import java.util.ArrayList;
import java.util.function.BinaryOperator;
//import java.util.function.Function;


public class Design {
	
	public static int nextId = 0;  // id to be given to next Design generated
	
	Level level;
	double quality;
	Design parent; 
	ArrayList<Design> children = new ArrayList<Design>(); 
	Distribution childScoreDistribution;
	Distribution childErrorDistribution;
	double score;
	double error;
	int id;
	
	// create a non-top-level Design object
	public Design(Design parent, double tau) {
		Design child = this;
		child.parent = parent;
		parent.children.add(child);
		child.level = parent.level.levelDown;
		child.score = parent.childScoreDistribution.draw( );
		child.error = parent.childErrorDistribution.draw( );
		initialize(child, tau);
	}
	// create a top-level Design object
	public Design(Level level,double tau) {
		this.parent = null;
		this.level = level;
		this.score = level.topScoreDistribution.draw( );
		this.error = level.topErrorDistribution.draw( );
		initialize(this, tau);
	}
	// initialize does the part of the initialization of a newly created Design object
	// that is common between top-level and non-top-level designs.
	public static void initialize(Design newDesign, double tau){
		newDesign.id = nextId;
		nextId++;
		newDesign.quality = newDesign.score - newDesign.error;
		//newDesign.score = newDesign.level.scoreFromQuality(newDesign.quality);

		double cSDMean = newDesign.level.cSDMeanB + newDesign.level.cSDMeanM * newDesign.score;
		double cSDStDev = newDesign.level.cSDStDev;
		newDesign.childScoreDistribution =  new NormalDistribution(cSDMean, cSDStDev);				

		double cEDMean = newDesign.level.cEDMeanB + newDesign.level.cEDMeanM * newDesign.score;
		double cEDStDev = newDesign.level.cEDStDev;
		newDesign.childErrorDistribution =  new NormalDistribution(cEDMean, cEDStDev);				
		
		traceCreation(newDesign, tau);
	}
	public static void traceCreation(Design design, double tau) {
//		System.out.println("Design created.  id: "+design.id+", score: "+design.score+", utility at "+tau+" = "+utility);
		System.out.format("Design created.  id: %d, score: %f, utility at %f = %f",
				design.id, design.score, tau, design.utility(tau,Hufs.U0));
	}
	// utility of having this design (with score this.score) starting at time tau with given U_0
	public double utility(double tau, BinaryOperator<Double> u0) {
		double doneTime = tau - this.level.genTime;
		return level.utility(score, doneTime, u0);
	}
}
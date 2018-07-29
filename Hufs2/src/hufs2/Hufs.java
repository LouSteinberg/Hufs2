package hufs2;

import java.util.ArrayList;
import java.util.function.BinaryOperator;

public class Hufs {
	public static final int NUMLEVELS = 3; // number of levels
	public static final BinaryOperator<Double> U0 = (score, tau) -> tau>=0 ? score : 0.0;
//	public static final BinaryOperator<Double> U0 = (score, tau) -> slope(5.0, score, tau);
	public static final int KIDSPERLEVEL = 3;
	public static final double STARTTAU = KIDSPERLEVEL * (NUMLEVELS-1);
	public static final Distribution TOPSCOREDISTRIBUTION = new NormalDistribution(10.0, 2.0);
	public static final Distribution TOPERRORDISTRIBUTION = new NormalDistribution(0.0, 1.0);	
	public static final int TESTREPS = 1;
	public static final boolean TRACE = Hufs.TESTREPS < 5;
	
	public static void main(String [ ] args) {
//		testWaterfall(Hufs.TESTREPS);
		testHufs(Hufs.TESTREPS);
//		testSlope( );
	}
	public static double slope(double begin, double score, double tau) {
		double utility;
		if (tau > begin) {
			utility = score;
		} else if (tau <= 0.0) {
			utility = 0.0;
		} else {
			utility = score*tau/begin;
		}
		return utility;
	}
	public static void testSlope( ) {
		for (double x = -2.0; x<13; x+=1.0) {
			System.out.format("%f:  %f3.2%n",x, slope(10, 100, x));
		}
	}
	public static void testWaterfall(int repetitions) {
		System.out.println("testWaterfall:");
		Level [ ] levels = Level.initializedLevels(NUMLEVELS);
		ArrayList<Design> results = new ArrayList<Design>( );
		ArrayList<Double> scores = new ArrayList<Double>( );
		ArrayList<Double> utilities = new ArrayList<Double>( );
		for (int r = 0; r < repetitions; r++) {
			Design specs = new Design(levels[levels.length-1], STARTTAU);
			Design result = waterfall(specs, levels, STARTTAU);
			results.add(result);
			scores.add(result.score);
			utilities.add(Hufs.U0.apply(result.score,0.0));
		}
		System.out.println("scores:");
		Stats.printMeanStDev(scores);
		System.out.println("utilities:");
		Stats.printMeanStDev(utilities);
	}
	
	public static Design waterfall(Design specs, Level [ ] levels, double tau) {
		Design parent = specs;
		for (int levelNum = NUMLEVELS - 1; levelNum > 0; levelNum--) {
			for (int j = 0; j < KIDSPERLEVEL; j++) {
				Design child = new Design(parent, tau);
				tau -= parent.level.genTime;
				parent.children.add(child);
			}
			parent = bestByScore(parent.children);
		}
//		System.out.println("tau "+tau+", score "+parent.score+ ", U0 "+ U0.apply(parent.score, tau));
		return parent;
	}
	
	public static void testHufs(int repetitions) {
		System.out.println("testHufs:");
		Level [ ] levels = Level.initializedLevels(NUMLEVELS);
		ArrayList<Design> results = new ArrayList<Design>( );
		ArrayList<Double> scores = new ArrayList<Double>( );
		for (int r = 0; r < repetitions; r++) {
			System.out.println("x");
			Design specs = new Design(levels[levels.length-1], STARTTAU);
			Design result = hufs(specs, levels, STARTTAU);
			results.add(result);
			scores.add(result.score);
//			System.out.println(r);
		}
		Stats.printMeanStDev(scores);;
	}
	public static Design hufs(Design specs, Level [ ] levels, double tau) {
		ArrayList<Design> allDesigns = new ArrayList<Design>( );
		Design parent = specs;
		allDesigns.add(parent);
		while (! parent.level.isBottomLevel() && tau > 0.0) {
			Design child = new Design(parent, tau);
			tau -= parent.level.genTime;
			allDesigns.add(child);
			parent = bestByUtility(allDesigns, tau, U0);
		}
//		System.out.println("tau "+tau+", score "+parent.score+ ", U0 "+ U0.apply(parent.score, tau));
		return parent;}
	public static Design bestByUtility(ArrayList<Design> designs, double tau, BinaryOperator<Double> u0) {
		Design bestDesign = designs.get(0);
		double bestUtility = bestDesign.utility(tau, u0, true);
		for (int d = 1; d < designs.size( ); d++) {
			Design nextDesign = designs.get(d);
			double nextUtility = nextDesign.utility(tau, u0, true);
			if (nextUtility > bestUtility) {
				bestUtility = nextUtility;
				bestDesign = nextDesign;
			}
		}
		return bestDesign;
	}
	public static Design bestByScore(ArrayList<Design> designs) {
		Design bestDesign = designs.get(0);
		double bestscore = bestDesign.score;
		for (int d = 1; d < designs.size( ); d++) {
			Design nextDesign = designs.get(d);
			double nextscore = nextDesign.score;
			if (nextscore > bestscore) {
				bestscore = nextscore;
				bestDesign = nextDesign;
			}
		}
		return bestDesign;
	}
}

package hufs2;

import java.util.ArrayList;
import java.util.function.BinaryOperator;

public class Hufs {
	public static final int NUMLEVELS = 3; // number of levels
	public static final BinaryOperator<Double> U0 = (score, tau) -> tau>0 ? score : 0.0;
	public static final double STARTTAU = 9.0;
	public static final Distribution TOPSCOREDISTRIBUTION = new NormalDistribution(10.0, 2.0);
	public static final Distribution TOPERRORDISTRIBUTION = new NormalDistribution(0.0, 1.0);	
	
	public static void main(String [ ] args) {
		testWaterfall(1);
//		testHufs(1);
	}
	public static void testWaterfall(int repetitions) {
		System.out.println("testWaterfall:");
		Level [ ] levels = Level.initializedLevels(NUMLEVELS);
		ArrayList<Design> results = new ArrayList<Design>( );
		ArrayList<Double> scores = new ArrayList<Double>( );
		for (int r = 0; r < repetitions; r++) {
			Design specs = new Design(levels[levels.length-1], STARTTAU);
			Design result = waterfall(specs, levels, STARTTAU);
			results.add(result);
			scores.add(result.score);
		}
		Stats.printMeanStDev(scores);;
	}
	
	public static Design waterfall(Design specs, Level [ ] levels, double tau) {
		Design parent = specs;
		int kidsPerLevel = 3;
		for (int levelNum = NUMLEVELS - 1; levelNum > 0; levelNum--) {
			for (int j = 0; j < kidsPerLevel; j++) {
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
			Design specs = new Design(levels[levels.length-1], STARTTAU);
			Design result = hufs(specs, levels, STARTTAU);
			results.add(result);
			scores.add(result.score);
		}
		Stats.printMeanStDev(scores);;
	}
	public static Design hufs(Design specs, Level [ ] levels, double tau) {
		ArrayList<Design> allDesigns = new ArrayList<Design>( );
		Design parent = specs;
		allDesigns.add(parent);
		while (! parent.level.isBottomLevel()) {
			Design child = new Design(parent, tau);
			tau -= parent.level.genTime;
			allDesigns.add(child);
			parent = bestByUtility(allDesigns, tau, U0);
		}
//		System.out.println("tau "+tau+", score "+parent.score+ ", U0 "+ U0.apply(parent.score, tau));
		return parent;}
	public static Design bestByUtility(ArrayList<Design> designs, double tau, BinaryOperator<Double> u0) {
		Design bestDesign = designs.get(0);
		double bestUtility = bestDesign.utility(tau, u0);
		for (int d = 1; d < designs.size( ); d++) {
			Design nextDesign = designs.get(d);
			double nextUtility = nextDesign.utility(tau, u0);
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

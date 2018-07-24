package hufs2;

import java.util.ArrayList;
import java.util.function.BinaryOperator;

public class Hufs {
	public static final int NUMLEVELS = 3; // number of levels
	public static final BinaryOperator<Double> U0 = (score, tau) -> tau>0 ? score : 0.0;
	public static final double STARTTAU = 5.0;
	public static final Distribution TOPSCOREDISTRIBUTION = new NormalDistribution(10.0, 2.0);
	public static final Distribution TOPERRORDISTRIBUTION = new NormalDistribution(0.0, 1.0);	
	public static  void main(String [ ] args) {
		ArrayList<Design> allDesigns = new ArrayList<Design>( );
		Level [ ] levels = Level.initializedLevels(NUMLEVELS);
		int numTopLevel = NUMLEVELS - 1;  // index/level number of top level
		Design specs = new Design(levels[numTopLevel]);
		Design parent = specs;
		allDesigns.add(parent);
		double tau = STARTTAU;
		while (! parent.level.isBottomLevel()) {
			Design child = new Design(parent);
			tau -= parent.level.genTime;
			allDesigns.add(child);
			parent = findBest(allDesigns, tau, U0);
		}
		System.out.println("tau "+tau+", U0 "+ U0.apply(parent.score, tau));
	}
	public static Design findBest(ArrayList<Design> designs, double tau, BinaryOperator<Double> u0) {
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
}

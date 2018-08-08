package hufs2;

import java.util.ArrayList;
//import java.util.function.BinaryOperator;

public class AllWaterfall {

	public static void main(String[] args) {
//		test allHufsfall
		System.out.println("test allHufs:");
		Level [ ] levels = Level.initializedLevels(Hufs.NUMLEVELS);
		Design specs = new Design(levels[levels.length-1], Hufs.STARTTAU);
		ArrayList<Design> justSpecs = new ArrayList<Design>( );
		justSpecs.add(specs);
		ArrayList<Design> results = allHufs(justSpecs, Hufs.STARTTAU);
		for (Design d : results) {
			System.out.format("%s at %5.2f%n", d, d.tauCreated);
		}
	}
	
	public static ArrayList<Design> allHufs(ArrayList<Design> parentDesigns, double tau) {
		if (tau <= 0) {
			return new ArrayList<Design>( );
		} else {
			ArrayList<Design> resultDesigns = new ArrayList<Design>( );
			ArrayList<Design> newParentDesigns = new ArrayList<Design>(parentDesigns);	
			newParentDesigns.add(null);
			int last = newParentDesigns.size( )-1;
			for (Design dp : parentDesigns) {
				double doneTau = tau - dp.level.genTime; 
				if (doneTau >= 0 && ! dp.level.isBottomLevel()) {
					Design newChild = dp.generate(tau);
					resultDesigns.add(newChild);
					newParentDesigns.set(last, newChild);
					resultDesigns.addAll(allHufs(newParentDesigns, doneTau));
				}
			}
			return resultDesigns;
		}
	}
		
}

package hufs2;

import java.util.ArrayList;
//import java.util.function.BinaryOperator;

public class AllWaterfall {

	public static void main(String[] args) {
//		test allWaterfall
	}
	
	public static ArrayList<Design> allWaterfall(ArrayList<Design> parentDesigns, double tau) {
		if (tau <= 0) {
			return new ArrayList<Design>( );
		} else {
			ArrayList<Design> resultDesigns = new ArrayList<Design>( );
			ArrayList<Design> newParentDesigns = new ArrayList<Design>(parentDesigns);	
			newParentDesigns.add(null);
			int last = newParentDesigns.size( )-1;
			for (Design dp : parentDesigns) {
				double doneTau = tau - dp.level.genTime; 
				if (doneTau >= 0) {
					Design newChild = dp.generate(tau);
					resultDesigns.add(newChild);
					newParentDesigns.set(last, newChild);
					resultDesigns.addAll(allWaterfall(newParentDesigns, doneTau));
				}
			}
			return resultDesigns;
		}
	}
		
}

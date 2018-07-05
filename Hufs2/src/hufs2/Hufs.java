package hufs2;
import java.util.ArrayList;


public class Hufs {
	public static  void main(String [ ] args) {
		ArrayList<Design> allDesigns = new ArrayList<Design>( );
		int numLevels = 3;		// number of levels
		Level [ ] levels = Level.initializedLevels(numLevels);
		int numTopLevel = numLevels - 1;  // index/level number of top level
		// calculate utility of parent
		Design specs = new Design(levels[numTopLevel]);
		Design parent = specs;
		allDesigns.set(0, parent);
		while (! parent.level.isBottomLevel()) {
			Design child = new Design(parent);
			allDesigns.add(child);
			parent = findBest(allDesigns);
		}
	}
	public static Design findBest(ArrayList<Design> designs) {
		return designs.get(0);
	}
}

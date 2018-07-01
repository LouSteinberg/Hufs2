package hufs2;
import java.util.ArrayList;


public class Hufs {
	public static  void main(String [] args) {
		ArrayList<Design> allDesigns = new ArrayList<Design>( );
		// Level [ ] levels;
		// calculate utility of parent
		Design specs = new Design(null);
		Design parent = specs;
		allDesigns.set(0, parent);
		while (parent.level.levelDown != null) {
			Design child = parent.generate();
			allDesigns.add(child);
			parent = findBest(allDesigns);
		}
	}
	public static Design findBest(ArrayList<Design> designs) {
		return new Design(null);
	}
}

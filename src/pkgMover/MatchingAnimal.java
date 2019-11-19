package pkgMover;

public class MatchingAnimal extends Mover {

	String name; // name of species / animal
	String clue; // hint for user
	boolean isMatched = false; // has the animal been mathed up already
	
	
	public MatchingAnimal(int x, int y, int imageWidth, int imageHeight, String value, String name, String clue) {
		super(x, y, imageWidth, imageHeight, 0, 0, value);
		this.name = name; // name of species
		this.clue = clue; // hint for user to better chances
	}
 
	
	public String toString() {
		return name + ": " + super.getX() + ", " + super.getY();
	}
	
	/**
	 * @author Andrew Brenner
	 * @param name String used to compare whether selected animal is a match or not
	 * @return boolean false if names are not the same, true if names are the same
	 */
	public boolean isMatch(String name) {
		if(this.name.compareTo(name) != 0) {
			return false;
		} else { // names are the same
			isMatched = true;
			return true;
			
		}
		
	}
	
	
	
	
}
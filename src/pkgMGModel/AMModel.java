package pkgMGModel;

import java.util.*;
import javafx.scene.input.MouseEvent;
import pkgEnum.Game;
import pkgMover.DataNode;
import pkgMover.Mover;
import pkgMover.MatchingAnimal;
import javafx.scene.input.MouseEvent;
import pkgEnum.Game;

public class AMModel extends MinigameModel {
	
	//room on right side of screen for labels (don't allow movers to go here)
	final private int labelBuffer = 300; 
	
	//all possible MatchingAnimals to be chosen from
	private ArrayList<MatchingAnimal> animals = new ArrayList<MatchingAnimal>();
	
	//max number of matching animals to be chosen as the ones for the game
	final private int maxMAs = 7;
	
	public AMModel() {
		g = Game.ANIMALMATCHING;
		createAnimals();
		movers = chooseAnimals();
		System.out.println(movers);
	}

	
	@Override
	public void update(MouseEvent me) {
		//movers dont actually move for now as restricting them to a certain area is too hard xD
		for (Mover m : movers) {
			if (isCollision(m, me)) {
				MatchingAnimal ma = (MatchingAnimal) m;
				if (ma.isMatched && ma.isMatch(me.getButton().toString())) {
					score += 1;
				}				
			}
		}
	}
	
	/**
	 *Loads preset animals with their clues into <code>animals</code>. 
	 *<p>
	 *Possibly change to loading from a file (making sure that when a new one is added, that an associated image
	 *file in Mover/ exists.
	 *
	 *@author Ryan Peters, Andrew Brenner
	 */
	public void createAnimals() {
		animals.add(new MatchingAnimal(0, 0, 50, 50, "Snowy Grouper", new String[] {
				"I can grow to up to 70 pounds!",
				"I am very rare to find in the Delaware Bay!",
				"I have pearly-white spots!"})); 
		animals.add(new MatchingAnimal(0, 0, 50, 50, "White-Tailed Deer",new String[] {
				"While I live on land, I can swim up to 15 mph!",
				"I use my tail to communicate with others!",
				"I can grow to almost 4 ft tall!"}));
		animals.add(new MatchingAnimal(0, 0, 50, 50, "Mussel", new String[] {
				"I attach to rocks with little threads I make that I call my beard!",
				"I can survive above water by trapping water in with my shell!",
				"I have a shiny black shell!"})); 
		animals.add(new MatchingAnimal(0, 0, 50, 50, "Blue Crab", new String[] {
				"I eat small fish, snails, mussels, and plants!",
				"I have learned to use my hind legs to help me swim!",
				"I can grow up to 1 to 2 pounds!"})); 
		animals.add(new MatchingAnimal(0, 0, 50, 50, "Oyster", new String[] {
				"I feed on plankton by filtering water through my gills!",
				"We cluster together on hard surfaces underwater on what we call beds",
				"I can produce a beautiful pearl!"})); 
		animals.add(new MatchingAnimal(0, 0, 50, 50, "Horseshoe Crab", new String[]{
				"I have existed for 220 million years. I'm almost a dinosaur!",
				"I eat mollusks and crustaceans on the ocean floor!",
				"I must shed (molt) my shell in order to grow!"}));
		animals.add(new MatchingAnimal(0, 0, 50, 50, "Black Sea Bass", new String[] {
				"In the fall, I migrate off-shore into coastal waters and the ocean",
				"I can grow up to 2 feet and 9 pounds!",
				"I sometimes have a long streamer coming off the top of my tail!"}));
		animals.add(new MatchingAnimal(0, 0, 50, 50, "Muskrat", new String[] {
				"I am pretty small. I only weigh up to 4 pounds!",
				"My tail is long and skinny!",
				"I build my home by piling plants like cattails on top of tree stumps!"}));
		animals.add(new MatchingAnimal(0, 0, 50, 50, "Beaver", new String[] {
				"My tail is flat and wide!",
				"I usually grow up to be between 35 and 60 pounds",
				"I build a dam as my home made of mud, logs, and sticks!"}));
	}
	
	/**
	 * Randomly choose <code>maxMAs</code> number of MatchingAnimals from <code>animals</code> attribute so each
	 * Matching Game is unique. Uses a HashSet to prevent duplicates, spawning a random number to index <code>animals</code>, and 
	 * attempts to add this <code>tempMA</code> until the size reaches <code>maxMAs</code>
	 * <p>
	 * Method is to be only called once during construction of this class
	 * 
	 * @author Ryan Peters
	 * @return	ArrayList<MatchingAnimal>	list of size <code>maxMAs</code> to be used for the game
	 */
	private ArrayList<Mover> chooseAnimals() {
		Set<MatchingAnimal> tempMA = new HashSet<MatchingAnimal>();
		while (tempMA.size() < maxMAs) {
			tempMA.add(animals.get(r.nextInt(animals.size())));
		}
		return new ArrayList<Mover>(tempMA);
		
	}
	
	/**
	 * Determines whether the MouseEvent's location is currently within an ellipse created from the mover's imageWidth and 
	 * imageHeight attributes. If it is, return <code>True</code>, else <code>false</code>
	 * 
	 * @author	Ryan Peters
	 * @param m		Mover to test if the mouse is over it in terms of the view/image
	 * @param me	MouseEvent from <code>View</code> of the current mouse position on screen 
	 * @return
	 */
	public boolean isCollision(Mover m, MouseEvent me) {
		double xDist = (Math.pow(m.getX() - me.getX(), 2) / Math.pow(m.getImageWidth() / 2, 2));
		double yDist = (Math.pow(m.getY() - me.getY(), 2) / Math.pow(m.getImageHeight() / 2, 2));
		double dist = xDist + yDist;
		return dist < 1;
	}
	
	
	class MatchingAnimal extends Mover {
		
		String[] clues; // hints for user (more hints = more education :D)
		boolean isMatched = false; // has the animal been mathed up already
		
		
		public MatchingAnimal(int x, int y, int imageWidth, int imageHeight, String value, String[] clues) {
			super(x, y, imageWidth, imageHeight, 0, 0, value);
			this.clues = clues; 
		}
	 
		
		public String toString() {
			return super.getValue() + ": " + super.getX() + ", " + super.getY();
		}
		
		/**
		 * @author Andrew Brenner
		 * @param name String used to compare whether selected animal is a match or not
		 * @return boolean false if names are not the same, true if names are the same
		 */
		public boolean isMatch(String value) {
			isMatched = super.getValue().compareTo(value) != 0;
			return isMatched;			
		}
		
		public String[] getClues() {
			return clues;
		}
		
		@Override
		public void move() {
			
		}
	}	
}

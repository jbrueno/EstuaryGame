
package pkgMGModel;

import java.util.*;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import pkgEnum.Game;
import pkgEnum.GameState;
import pkgMover.DataNode;
import pkgMover.Mover;
import pkgMover.MatchingAnimal;
import javafx.scene.input.MouseEvent;
import pkgEnum.Game;

public class AMModel extends MinigameModel {
	
	//all possible MatchingAnimals to be chosen from
	private ArrayList<MatchingAnimal> animals = new ArrayList<MatchingAnimal>();
	
	//max number of matching animals to be chosen as the ones for a game
	final private int MAX_MATCH_ANIMALS = 7;
	final private int MAX_BQMOVERS = 3; //bq = bonus quiz
	
	//button tracking for dragging animals during matching
	private String btnSourceID = "";
	
	private int POINTS_MATCHING = 100;
	final private int POINTS_BQ = 300;
	private boolean guessed = false;
	private String correctMoverID;
	
	//flag used to determine if all MA's have been matched
	boolean flag = false;
	
	public AMModel() {
		g = Game.ANIMALMATCHING;
		createAnimals();
		movers = reduceUntil(new ArrayList<Mover>(animals), MAX_MATCH_ANIMALS);
		System.out.println(movers);
		gs = GameState.INPROGRESS;
	}

	
	@Override
	public void update(MouseEvent me) {
		switch (gs) {
			case INPROGRESS: 
				if (me.getEventType() == MouseEvent.DRAG_DETECTED || me.getEventType() == MouseEvent.MOUSE_DRAGGED) {
					try {
						btnSourceID = ((Button) me.getSource()).getId();
						System.out.println("SOURCE SET TO: " + btnSourceID);
					} catch (ClassCastException e) {}
				}
				if (me.getEventType() == MouseEvent.MOUSE_ENTERED_TARGET) {
					System.out.println("DRAG DROPPED");
					flag = true;
					for (Mover m : movers) {
						MatchingAnimal ma = (MatchingAnimal) m;
						
						if (isCollision(ma, me)) {
							System.out.println("COLLISION between " + m.getValue() + " and " + btnSourceID );
							if (!ma.isMatched && ma.isMatch()) {
								System.out.println("MATCHED");
								score += POINTS_MATCHING;
								System.out.println("Score = " + score);
							} 
						}
						
						System.out.println(ma + " " + ma.isMatched);
						if (!ma.isMatched) {
							flag = false;
						}
						
					}
					
					if (flag) {
						gs = GameState.BONUS;
						movers = reduceUntil(movers, MAX_BQMOVERS);
						correctMoverID = movers.get(0).getValue();
					}					
				}	
				break;
			case BONUS:
				if (me.getEventType() == MouseEvent.MOUSE_CLICKED || me.getEventType() == MouseEvent.MOUSE_PRESSED) {
					try {
						btnSourceID = ((Button) me.getSource()).getId();
					} catch (ClassCastException e) {}
					
					if (btnSourceID.equals(correctMoverID)) {
						score += POINTS_BQ;
					} 
					
					guessed = true;
				}
				
				
		default:
			break;		
		}
	}
	
	/**
	 *Loads preset animals with their clues into <code>animals</code>. 
	 *<p>
	 *Possibly change to loading from a file (making sure that when a new one is added, that an associated image
	 *file in Mover/ exists.
	 *
	 *@author Ryan Peters
	 */
	public void createAnimals() {
		animals.add(new MatchingAnimal(300, 100, 200, 130, "SnowyGrouper", new String[] {
				"I can grow to up to 70 pounds!",
				"I am very rare to find in the Delaware Bay!",
				"I have pearly-white spots!",
				"I am a Snowy Grouper!"})); 
		animals.add(new MatchingAnimal(770, 460, 400, 400, "WhiteTailedDeer",new String[] {
				"While I live on land, I can swim up to 15 mph!",
				"I use my tail to communicate with others!",
				"I can grow to almost 4 ft tall!",
				"I am a White-Tailed Deer"}));
		animals.add(new MatchingAnimal(460, 150, 100, 80, "Mussel", new String[] {
				"I attach to rocks with little threads I make that I call my beard!",
				"I can survive above water by trapping water in with my shell!",
				"I have a shiny black shell!",
				"I am a Mussel!"})); 
		animals.add(new MatchingAnimal(150, 630, 100, 95, "BlueCrab", new String[] {
				"I eat small fish, snails, mussels, and plants!",
				"I have learned to use my hind legs to help me swim!",
				"I can grow up to 1 to 2 pounds!",
				"I am a Blue Crab!"})); 
		animals.add(new MatchingAnimal(200, 540, 90, 90, "Oyster", new String[] {
				"I feed on plankton by filtering water through my gills!",
				"We cluster together on hard surfaces underwater on what we call beds",
				"I can produce a beautiful pearl!",
				"I am an oyster!"})); 
		animals.add(new MatchingAnimal(450, 400, 160, 130, "HorseshoeCrab", new String[]{
				"I have existed for 220 million years. I'm almost a dinosaur!",
				"I eat mollusks and crustaceans on the ocean floor!",
				"I must shed (molt) my shell in order to grow!",
				"I am a Horseshoe Crab!"}));
		animals.add(new MatchingAnimal(150, 300, 200, 100, "BlackSeaBass", new String[] {
				"In the fall, I migrate off-shore into coastal waters and the ocean",
				"I can grow up to 2 feet and 9 pounds!",
				"I sometimes have a long streamer coming off the top of my tail!",
				"I am a Black Sea Bass!"}));
		animals.add(new MatchingAnimal(570, 540, 108, 81, "Muskrat", new String[] {
				"I am pretty small. I only weigh up to 4 pounds!",
				"My tail is long and skinny!",
				"I build my home by piling plants like cattails on top of tree stumps!",
				"I am a Muskrat!"}));
		animals.add(new MatchingAnimal(700, 150, 210, 160, "Beaver", new String[] {
				"My tail is flat and wide!",
				"I usually grow up to be between 35 and 60 pounds",
				"I build a dam as my home made of mud, logs, and sticks!",
				"I am a Beaver!"}));
	}
	
	/**
	 * Randomly choose <code>size</code> number of Movers from <code>arr</code>, returning an <code>ArrayList</code> 
	 * with no duplicates.
	 * 
	 * @author Ryan Peters
	 * @param	ArrayList<Mover> 	Original list of Movers to be reduced from
	 * @param	int size			Number of Movers to be in the returned list
	 * @return	ArrayList<Mover>	list of size <code>size</code> randomly chosen from <code>arr</code>
	 */
	private ArrayList<Mover> reduceUntil(ArrayList<Mover> arr, int size) {
		HashSet<Mover> newArr = new HashSet<Mover>();
		while (newArr.size() < size) {
			newArr.add(arr.get(r.nextInt(arr.size())));
		}
		return new ArrayList<Mover>(newArr);
	}
	
	
	
	public class MatchingAnimal extends Mover {
		
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
		 * Compares the ID of the button from which the drag originated to the endpoint of the drag. This will only be called
		 * if the endpoint of the drag collides with a mover so it avoids NullPointerException in <code>super.getValue()</code>
		 * <p>
		 * The buttons in Animal Matching are loaded to have the ID of the value of the mover they represent so we can use this 
		 * ID to check for a match from the button with name/clues and the image (mover)
		 * 
		 * @author Ryan Peters
		 * @return boolean  whether the source of the drag matches the endpoint of the drag
		 */
		public boolean isMatch() {
			isMatched = btnSourceID.equals(super.getValue());
			return isMatched;			
		}
		
		public String[] getClues() {
			return clues;
		}
		
		public boolean getIsMatched() {
			return isMatched;
		}
	}	
}
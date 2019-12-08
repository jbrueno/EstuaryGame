package pkgMGModel;
 
import java.util.Random;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import pkgEnum.Game;
import pkgEnum.GameState;
import pkgMover.Mover;

public class WSModel extends MinigameModel{
	private static final long serialVersionUID = 18L;
	// WS_COLLECTTUTORIAL
	boolean collectSet=false;
	String btnSourceId="";
	// WS_COLLECT
	Mover Bottle;
	final int bottleImageWidth = 268;
	final int bottleImageHeight = 100;
	final int bottleX = backgroundWidth/2;
	
	final int maxHeight = bottleImageHeight;
	final int maxDepth = backgroundHeight-bottleImageHeight-100;
	
	int waterLevel = backgroundHeight/2;
	final private int COLLECT_SCORING_RANGE = 91; //half the height of the gradient guide
	final private int CORRECT_LEVEL = 395; //center of the gradient guide
	final private int MAX_COLLECT_POINTS = 200;
	int deepLevel=backgroundHeight*4/5;
	boolean filled = false;
	
	
	// WS_PH
	boolean labSet = false;
	boolean pHSet = false;
	Mover pHStrip;
	boolean gotStrip=false;
	final int pHStripWidth = 30;
	final int pHStripHeight = 100;
	
	final int pHMax=9;
	final int pHMin=5;
	int pH;
	double pHGuess=7;
	boolean isDipped = false;
	boolean guessSubmit = false;
	boolean clicked=false;
	
	Mover testTube;
	final int testTubeWaterWidth = 120;
	final int testTubeImageWidth = 500;
	final int testTubeImageHeight = 500;
	final int testTubeX = backgroundWidth / 3;
	final int testTubeY = backgroundHeight * 5/8 ;
	final int testTubeLeftSide = testTubeX - (testTubeWaterWidth/2); // x-coord
	final int testTubeRightSide = testTubeX + (testTubeWaterWidth/2); // x-coord
	final int testTubeWaterLevel = testTubeImageHeight*2/5; // y-coord
	final int testTubeBottom = testTubeY+testTubeImageHeight/2;
	
	
	/**
	 * Constructor - WSModel
	 * Game g is updated to current game being played - Enum WATERSAMPLING
	 * Gamestate gs is updated so that WS begins with a tutorial
	 * addObjects is called, Gamestate gs is passed
	 * 		Instances of objects related to WS tutorial are created
	 */
	public WSModel() {
		g = Game.WATERSAMPLING;	
		gs = GameState.WS_COLLECTTUTORIAL;
		addObjects(gs);
	}
	
	
	/**
	 * Instances of objects are created depending on the current Gamestate gs
	 * 		sets variable pH 
	 * @param gs the current stage (GameState) of the WS Minigame, 4 Enums
	 */
	public void addObjects(GameState gs) {
		
		switch (gs) {
		case WS_COLLECTTUTORIAL:
			Bottle = new Bottle(bottleX, maxHeight, 0, 15, "Bottle");
			movers.add(Bottle);
			break;
		case WS_COLLECT :
			break;
		case WS_PHTUTORIAL:
			setPH();
			pHStrip = new pHStrip(0, 0, 0, 0, "pHStrip");
			testTube = new testTube(testTubeX, testTubeY, 0, 0, "testtube");
			movers.add(testTube);
			break;
		case WS_PH : 
			setPH();
			break;
		default:
			break;
		}
	}
	
	
	/**
	 * Sets up environment when needed, updates x & y coordinates of objects
	 * Examines MouseEvent to determine the appropriate changes that need to occur from user input
	 * @param me latest MouseEvent to occur
	 */
	@Override
	public void update(MouseEvent me) {		
		buttonSelected(me);

		switch (gs) {
		case START :
			gs = GameState.WS_COLLECTTUTORIAL;
		case WS_COLLECTTUTORIAL:
			Bottle.move(bottleX, maxHeight, bottleX, maxDepth);
			
			if(!filled && Bottle.getY()> waterLevel && me.getEventType() == MouseEvent.MOUSE_PRESSED && btnSourceId=="Fill") {
				System.out.println("FILLING BOTTLE");
				fillBottle();
			} else if (filled && me.getEventType() == MouseEvent.MOUSE_PRESSED && btnSourceId=="Play"){
				movers.remove(Bottle);
				gs=GameState.WS_COLLECT;
			} // WS_COLLECTTUTORIAL is finished, moving onto next GameState
			
			break;
		case WS_COLLECT :
			if(!collectSet) {
				filled=false;
				Bottle.setValue("Bottle");
				movers.remove(Bottle);
				movers.add(Bottle);
				collectSet=true;
			}
			Bottle.move(bottleX, maxHeight, bottleX, maxDepth);
			
			if(!filled && Bottle.getY()> waterLevel && me.getEventType() == MouseEvent.MOUSE_PRESSED && btnSourceId=="Fill") {
				System.out.println("FILLING BOTTLE");
				fillBottle();
			} else if(filled && me.getEventType() == MouseEvent.MOUSE_PRESSED && btnSourceId=="Lab"){
				movers.remove(Bottle);
				gs=GameState.WS_PHTUTORIAL;
			} // Stage complete, moving to next tutorial
			
			break;
		case WS_PHTUTORIAL :
			if(!labSet) { // if lab is not set up, add objects for this stage
				addObjects(gs);
				labSet = true;
			}
			
			if(me.getEventType()==MouseEvent.MOUSE_PRESSED && btnSourceId=="phStripBox") {
				if(!gotStrip) {
					movers.add(pHStrip);
					movers.add(testTube);
					gotStrip=true;
				}
			}
			pHStrip.move(me.getX(),me.getY());
			dipStrip(); 
			
			if(gotStrip && me.getEventType()==MouseEvent.MOUSE_PRESSED && btnSourceId=="Play") {
				gs=GameState.WS_PH;
			} // Finished tutorial, moving on to next stage
			break;
		case WS_PH :
			
			if(!labSet) { // if lab is not set up
				addObjects(gs);
				labSet = true;
			}
			
			if(!pHSet) {
				setPH();
				pHStrip.setValue("pHStrip");
				movers.remove(pHStrip);
				gotStrip=false;
				pHSet=true;
			}
			
			if(me.getEventType()==MouseEvent.MOUSE_PRESSED && btnSourceId=="phStripBox") {
				if(!gotStrip) {
					movers.add(pHStrip);
					movers.add(testTube);
					gotStrip=true;
				}
			}
			pHStrip.move(me.getX(),me.getY());
			dipStrip(); 
			if(!guessSubmit) {
				
				checkGuess(me);
				System.out.println(pHGuess);
				
			 if (me.getEventType()==MouseEvent.MOUSE_PRESSED && btnSourceId=="Submit") {	
				calculatePHScore();
				guessSubmit=true;
			 }
			}
			
			break;

		default :
			break;
		}// end of switch
		
	}	
		
	
		
	/**
	 * @author AG
	 * 
	 * fills bottle and calculates appropriate score added if not in tutorial
	 */
	public void fillBottle() {
		filled=true;
		Bottle.setValue("fullBottle");
		
		if (gs!=GameState.WS_COLLECTTUTORIAL){
			score += calculateCollectSore();
		}
	}
	
	
	/**
	 * Randomly sets pH within a range for user to guess
	 * adds this value to Mover object pHStrip 
	 */
	public void setPH() {
		Random random = new Random();
		pH=random.nextInt((pHMax - pHMin) + 1) + pHMin;
		
		for (Mover m : movers) {
			if(m instanceof pHStrip) {
			pHStrip ma = (pHStrip) m;
			ma.setpH(pH);
			}
		}
	}
	
	
	/**
	 * Calculates the Collecting Water Score by mapping the Bottle's current height within the gradient image on the background.
	 * The closer the bottle is to the middle, the more points you get, maximum being <code>MAX_COLLECT_POINTS</code>.
	 * 
	 * @author Ryan Peters
	 * @returns	score to be added to user's total
	 */
	private int calculateCollectSore() {
		if (Bottle.getY() < waterLevel) {return 0;}
		int cScore = (int) (MAX_COLLECT_POINTS -  (2 * Math.abs(CORRECT_LEVEL - Bottle.getY())));
		return (cScore < 0) ? 0 : cScore;
	}

	
	/**
	 * @author Abrenner
	 * logic for determining if pHStrip is within bounds of testtube and has been dipped in water
	 * changes boolean isDipped to true upon meeting criteria
	 * Updates the value of Mover object pHStrip to the appropriate pH
	 */
	public void dipStrip() {
		// setting up logic for dipping pHStrip within testTube bounds
		if(pHStrip.getX() >= testTubeLeftSide &&
			pHStrip.getX() <= testTubeRightSide &&
			pHStrip.getY() >= testTubeWaterLevel &&
			 pHStrip.getY() <= testTubeBottom) {
						
					isDipped = true;
					changeColor(pH);
		}
	}
	
	
	/**
	 * sets the value of Mover object pHStrip to the given pH to determine the appropriate image to use
	 * @param ph actual pH of the water
	 */
	public void changeColor(int ph) {
		pHStrip.setValue("pHStrip"+ph);
	}
	
	
	/**
	 * Determines if a button has been selected, called in update(MouseEvent me)
	 * @param me MouseEvent from user input
	 */
	public void buttonSelected(MouseEvent me) {

		if (me.getEventType()==MouseEvent.MOUSE_PRESSED) {
			try {
				btnSourceId = ((Button) me.getSource()).getId();
			} catch (ClassCastException e) {}
		}
	}
	
	
	/**
	 * Determines which button has been pressed to increment or decrement user's guess
	 * @param me MouseEvent from user input
	 */
	public void checkGuess(MouseEvent me) {
		if (me.getEventType()==MouseEvent.MOUSE_PRESSED) {
			try {
				btnSourceId = ((Button) me.getSource()).getId();
				
				if (btnSourceId=="plus") {
					pHGuess+=0.5;
				} else if (btnSourceId=="minus") {
					pHGuess-=0.5;
				}				
			} catch (ClassCastException e) {}
		}
	}
	
	
	/**
	 * Determines if user's guess was correct or not
	 * If correct 500 points are added to the total score
	 * If incorrect, amount of points added to score is dependent on how close user's guess was
	 */
	void calculatePHScore(){
		if(pHGuess == pH) {
			score+=500;
		} else {
			score=(int)( (Math.abs(pH-pHGuess))*100) / (int)(Math.abs(pH-pHGuess) );
			}
		}
	
	
	
	
	
	// Movers related to WS
	class Bottle extends Mover {
		private static final long serialVersionUID = 26L;
		public Bottle(int x, int y, int xIncr, int yIncr, String value) {
			super(x, y, bottleImageWidth, bottleImageHeight, xIncr, yIncr, value);
		}
 	}
	 
	public class pHStrip extends Mover{
		private static final long serialVersionUID = 25L;
		double pH;

		public pHStrip(int x, int y, int xIncr, int yIncr, String value) {
			super(x, y, pHStripWidth, pHStripHeight, xIncr, yIncr, value);
		}
		
		/**
		 * sets attribute pH of pHStrip object to given double
		 * @param pH actual pH of the water
		 */
		void setpH(double pH) {
			this.pH=pH;
		}
		
		/**
		 * Returns the pH attribute of pHStrip object
		 * @return pH
		 */
		public double getpH() {
			return pH;
		}
	}
	
	
	public class testTube extends Mover{
		private static final long serialVersionUID = 24L;
		public testTube(int x, int y, int xIncr, int yIncr, String value) {
			super(x, y, testTubeImageWidth, testTubeImageHeight, xIncr, yIncr, value);
		}
	}

	
}
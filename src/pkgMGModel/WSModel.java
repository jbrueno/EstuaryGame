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
	int bottleXIncr = 15;
	final int BOTTLE_IMAGE_WIDTH = 268;
	final int BOTTLE_IMAGE_HEIGHT = 100;
	final int BOTTLE_X = backgroundWidth/2;
	
	final int MAX_HEIGHT = BOTTLE_IMAGE_HEIGHT;
	final int MAX_DEPTH = backgroundHeight-BOTTLE_IMAGE_HEIGHT-100;
	
	final int WATER_LEVEL = backgroundHeight/2;
	final private int CORRECT_LEVEL = 395; //center of the gradient guide
	final private int MAX_COLLECT_POINTS = 200;
	boolean filled = false;
	
	
	// WS_PH
	boolean pHTutorialSet = false;
	boolean pHSet = false;
	Mover pHStrip;
	boolean gotStrip=false;
	final int PH_STRIP_WIDTH = 30;
	final int PH_STRIP_HEIGHT = 100;
	
	final int PH_MAX=9;
	final int PH_MIN=5;
	int pH;
	double pHGuess=7;
	boolean isDipped = false;
	boolean guessSubmit = false;
	
	Mover testTube;
	final int TEST_TUBE_WATER_WIDTH = 120;
	final int TEST_TUBE_IMAGE_WIDTH = 500;
	final int TEST_TUBE_IMAGE_HEIGHT = 500;
	final int TEST_TUBE_X = backgroundWidth / 3;
	final int TEST_TUBE_Y = backgroundHeight * 5/8 ;
	final int TEST_TUBE_LEFT_SIDE = TEST_TUBE_X - (TEST_TUBE_WATER_WIDTH/2); // x-coord
	final int TEST_TUBE_RIGHT_SIDE = TEST_TUBE_X + (TEST_TUBE_WATER_WIDTH/2); // x-coord
	final int TEST_TUBE_WATER_LEVEL = TEST_TUBE_IMAGE_HEIGHT*2/5; // y-coord
	final int TEST_TUBE_BOTTOM = TEST_TUBE_Y+TEST_TUBE_IMAGE_HEIGHT/2;
	
	
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
	 * Adds appropriate objects dependent on the current gamestate. When called during update, ensures that objects are only added once
	 * and certain functions ( see <CODE>setPH()<CODE> ) are not called multiple times.
	 * 
	 * @author AG
	 * @param gs current gamestate

	 */
	public void addObjects(GameState gs) {
		
		switch (gs) {
		case WS_COLLECTTUTORIAL:
			Bottle = new Bottle(BOTTLE_X, MAX_HEIGHT, 0, bottleXIncr, "Bottle");
			movers.add(Bottle);
			break;
		case WS_COLLECT :
			break;
		case WS_PHTUTORIAL:
			setPH();
			pHStrip = new pHStrip(0, 0, 0, 0, "pHStrip");
			testTube = new testTube(TEST_TUBE_X, TEST_TUBE_Y, 0, 0, "testtube");
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
			Bottle.move(BOTTLE_X, MAX_HEIGHT, BOTTLE_X, MAX_DEPTH);
			
			if(!filled && Bottle.getY() > WATER_LEVEL && me.getEventType() == MouseEvent.MOUSE_PRESSED && btnSourceId.equals("Fill")) {
				System.out.println("FILLING BOTTLE");
				fillBottle();
			} else if(filled && me.getEventType() == MouseEvent.MOUSE_PRESSED && btnSourceId.equals("Play")){
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

			Bottle.move(BOTTLE_X, MAX_HEIGHT, BOTTLE_X, MAX_DEPTH);
			
			if(!filled && Bottle.getY()> WATER_LEVEL && me.getEventType() == MouseEvent.MOUSE_PRESSED && btnSourceId.equals("Fill")) {
				System.out.println("FILLING BOTTLE");
				fillBottle();

			} else if(filled && me.getEventType() == MouseEvent.MOUSE_PRESSED && btnSourceId=="Lab"){

				movers.remove(Bottle);
				gs=GameState.WS_PHTUTORIAL;
			} // Stage complete, moving to next tutorial
			
			break;
		case WS_PHTUTORIAL :

			if(!pHTutorialSet) {
				addObjects(gs);
				pHTutorialSet = true;
			}
			
			if(me.getEventType()==MouseEvent.MOUSE_PRESSED && btnSourceId.equals("phStripBox")) {
				if(!gotStrip) {
					movers.add(pHStrip);
					movers.add(testTube);
					gotStrip=true;
				}
			}
			pHStrip.move(me.getX(),me.getY());
			dipStrip(); 
			
			if(gotStrip && me.getEventType()==MouseEvent.MOUSE_PRESSED && btnSourceId.equals("Play")) {
				gs=GameState.WS_PH;
			} // Finished tutorial, moving on to next stage
			break;
		case WS_PH :
			
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
				
				if (me.getEventType()==MouseEvent.MOUSE_PRESSED && btnSourceId.equals("Submit")) {	
					calculatePHScore();
					guessSubmit=true;
				}
			}
		
			break;

		default :
			break;
		}
		
	}	

	
		
	/**

	 * Checks if bottle has been filled, changes value of bottle object so it is drawn as full
	 * Does not update score if called during tutorial.
	 * @author AG
	 */
	public void fillBottle() {
		filled=true;
		Bottle.setValue("fullBottle");
		
		if (gs!=GameState.WS_COLLECTTUTORIAL){
			score += calculateCollectSore();
		}
	}
	

	/**
	 * Sets pH attribute of WSModel to random value between 7 and 9. 
	 * This is mapped to the pH value of the pHStrip as well.
	 * 
	 * @author AG
	 */
	public void setPH() {
		Random random = new Random();
		pH=random.nextInt((PH_MAX - PH_MIN) + 1) + PH_MIN;
		
		for (Mover m : movers) {
			if(m instanceof pHStrip) {
			pHStrip ma = (pHStrip) m;
			ma.setStripPH(pH);
			}
		}
	}

	public int getPH() {
		return pH;
	}
	
	
	/**
	 * Calculates the Collecting Water Score by mapping the Bottle's current height within the gradient image on the background.
	 * The closer the bottle is to the middle, the more points you get, maximum being <code>MAX_COLLECT_POINTS</code>.
	 * 
	 * @author Ryan Peters
	 * @returns	score to be added to user's total
	 */
	private int calculateCollectSore() {
		if (Bottle.getY() < WATER_LEVEL) {return 0;}
		int cScore = (int) (MAX_COLLECT_POINTS -  (2 * Math.abs(CORRECT_LEVEL - Bottle.getY())));
		return (cScore < 0) ? 0 : cScore;
	}

	
	/**
	 * logic for determining if pHStrip is within bounds of testtube and has been dipped in water
	 * changes boolean isDipped to true upon meeting criteria
   * Updates the value of Mover object pHStrip to the appropriate pH
	 * @author Abrenner
	 * 
	 * 
	 */
	public void dipStrip() {
		if(pHStrip.getX() >= TEST_TUBE_LEFT_SIDE &&
			pHStrip.getX() <= TEST_TUBE_RIGHT_SIDE &&
			pHStrip.getY() >= TEST_TUBE_WATER_LEVEL &&
			 pHStrip.getY() <= TEST_TUBE_BOTTOM) {
						
					isDipped = true;
					changeColor(pH);
		}
	}
	

	/**
	 * Changes value of pHStrip to match pH, allowing correct color strip to be drawn
	 * 
	 * @param ph integer representing pH of water
	 */
	public void changeColor(int ph) {
		pHStrip.setValue("pHStrip"+ph);
	}
	

	/**
	 * If a button is pressed, sets btnSourceId value to the sourceID of the mouseevent
	 * 
	 * @author AG
	 * @param me mouseevent 
	 *
	 */
	public void buttonSelected(MouseEvent me) {
		if (me.getEventType()==MouseEvent.MOUSE_PRESSED) {
			try {
				btnSourceId = ((Button) me.getSource()).getId();
			} catch (ClassCastException e) {}
		}
	}
	

	/**
	 * If increase or decrease buttons pressed in order to set player's pH guess,
	 * pHGuess increased or decreased appropriate amount. This allows for score to be determined without 
	 * requiring input from WSView.
	 * 
	 * @author AG
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
	 * Sets score based on how close player's guess is to actual value of PH
	 * 
	 * @author AG
	 */
	
	private void calculatePHScore(){
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
			super(x, y, BOTTLE_IMAGE_WIDTH, BOTTLE_IMAGE_HEIGHT, xIncr, yIncr, value);
		}
 	}
	 
	public class pHStrip extends Mover{
		private static final long serialVersionUID = 25L;
		double pH;

		public pHStrip(int x, int y, int xIncr, int yIncr, String value) {
			super(x, y, PH_STRIP_WIDTH, PH_STRIP_HEIGHT, xIncr, yIncr, value);
		}
		

		/**
		 * sets attribute pH of pHStrip object to given double
		 * @param pH actual pH of the water 
		 */
		public void setStripPH(double pH) {
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
			super(x, y, TEST_TUBE_IMAGE_WIDTH, TEST_TUBE_IMAGE_HEIGHT, xIncr, yIncr, value);
		}
	}

	
}
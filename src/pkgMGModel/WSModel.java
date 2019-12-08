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
	final int bottleImageWidth = 268;
	final int bottleImageHeight = 100;
	final int bottleX = backgroundWidth/2;
	
	final int maxHeight = bottleImageHeight;
	final int maxDepth = backgroundHeight-bottleImageHeight-100;
	
	int waterLevel = backgroundHeight/2;
	final private int CORRECT_LEVEL = 395; //center of the gradient guide
	final private int MAX_COLLECT_POINTS = 200;
	boolean filled = false;
	
	
	// WS_PH
	boolean pHTutorialSet = false;
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
	
	public WSModel() {
		g = Game.WATERSAMPLING;	
		gs = GameState.WS_COLLECTTUTORIAL;
		addObjects(gs);
	}
	
	//public Mover(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
	public void addObjects(GameState gs) {
		
		switch (gs) {
		case WS_COLLECTTUTORIAL:
			Bottle = new Bottle(bottleX, maxHeight, 0, bottleXIncr, "Bottle");
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
	
	@Override
	public void update(MouseEvent me) {		
		buttonSelected(me);

		switch (gs) {
		case START :
			gs = GameState.WS_COLLECTTUTORIAL;
		case WS_COLLECTTUTORIAL:
			//System.out.println(btnSourceId);
			Bottle.move(bottleX, maxHeight, bottleX, maxDepth);
			
			if(!filled && Bottle.getY()> waterLevel && me.getEventType() == MouseEvent.MOUSE_PRESSED && btnSourceId.equals("Fill")) {
				System.out.println("FILLING BOTTLE");
				fillBottle();
			}
			if(filled && me.getEventType() == MouseEvent.MOUSE_PRESSED && btnSourceId.equals("Play")){
				movers.remove(Bottle);
				gs=GameState.WS_COLLECT;
			}
			
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
			
			if(!filled && Bottle.getY()> waterLevel && me.getEventType() == MouseEvent.MOUSE_PRESSED && btnSourceId.equals("Fill")) {
				System.out.println("FILLING BOTTLE");
				fillBottle();
			}
			
			if(filled && me.getEventType() == MouseEvent.MOUSE_PRESSED && btnSourceId.equals("Lab")){
				movers.remove(Bottle);
				gs=GameState.WS_PHTUTORIAL;
			}
			
			break;
		case WS_PHTUTORIAL :
			if(!pHTutorialSet) { // if lab is not set up
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
			}
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
	 * 
	 * @author AG
	 * 
	 * @return boolean true if bottle is full
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
		pH=random.nextInt((pHMax - pHMin) + 1) + pHMin;
		
		for (Mover m : movers) {
			if(m instanceof pHStrip) {
			pHStrip ma = (pHStrip) m;
			ma.setStripPH(pH);
			}
		}
	}
	
	
	/**
	 * Calculates the Collecting Water Score by mapping the Bottle's current height within the gradient image on the background.
	 * The closer the bottle is to the middle, the more points you get, maximum being <code>MAX_COLLECT_POINTS</code>.
	 * 
	 * @author Ryan Peters
	 * @returns	score
	 */
	private int calculateCollectSore() {
		if (Bottle.getY() < waterLevel) {return 0;}
		int cScore = (int) (MAX_COLLECT_POINTS -  (2 * Math.abs(CORRECT_LEVEL - Bottle.getY())));
		return (cScore < 0) ? 0 : cScore;
	}

	
	/**
	 * logic for determining if pHStrip is within bounds of testtube and has been dipped in water
	 * changes boolean isDipped to true upon meeting criteria
	 * 
	 * @author Abrenner
	 * 
	 */
	public void dipStrip() {
		// setting up logic for dipping pHStrip within testTube bounds
		if(pHStrip.getX() >= testTubeLeftSide &&
			pHStrip.getX() <= testTubeRightSide &&
			pHStrip.getY() >= testTubeWaterLevel &&
			 pHStrip.getY() <= testTubeBottom) {
						
					isDipped = true;
					changeColor(pH);
		} // end if
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
		
		void setStripPH(double pH) {
			this.pH=pH;
		}
		
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
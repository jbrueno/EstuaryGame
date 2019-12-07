package pkgMGModel;

import java.util.Random;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pkgEnum.Game;
import pkgEnum.GameState;
import pkgMGModel.AMModel.MatchingAnimal;
import pkgMGModel.WSModel.pHStrip;
import pkgMover.Mover;

public class WSModel extends MinigameModel{
	// WS_COLLECTTUTORIAL
	boolean collectSet=false;
	String btnSourceId="";
	// WS_COLLECT
	Mover Bottle;
	final int bottleImageWidth = 268;
	final int bottleImageHeight = 100;
	final int bottleX = backgroundWidth/2;
	
	final int maxHeight = bottleImageHeight;
	final int maxDepth = backgroundHeight-bottleImageHeight/2-50;
	
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
	final int testTubeImageWidth = 500;
	final int testTubeSideFromBorder = 185;
	final int testTubeImageHeight = 500;
	final int testTubeX = backgroundWidth / 3;
	final int testTubeY = backgroundHeight - 315;
	final int testTubeLeftSide = 365; // x-coord
	final int testTubeRightSide = 485; // x-coord
	final int testTubeWaterLevel = 425; // y-coord
	final int testTubeBottom = 680;
	
	MouseEvent prevME = null;
	private final int MAX_PH_SCORE = 500;
	
	public WSModel() {
		g = Game.WATERSAMPLING;	
		gs= GameState.WS_COLLECTTUTORIAL;
		addObjects(gs);
	}
	
	//public Mover(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
	public void addObjects(GameState gs) {
		
		switch (gs) {
		case WS_COLLECTTUTORIAL:
			Bottle = new Bottle(bottleX, maxHeight, 0, 15, "Bottle");
			movers.add(Bottle);
			break;
		case WS_COLLECT :
			Bottle = new Bottle(bottleX, maxHeight, 0, 15, "Bottle");
			movers.add(Bottle);
			break;
		case WS_PHTUTORIAL:
			setPH();
			pHStrip = new pHStrip(0, 0, 0, 0, "pHStrip");
			testTube = new testTube(testTubeX, testTubeY, 0, 0, "testtube");
			movers.add(testTube);
			break;
		case WS_PH : 
			setPH();
			pHStrip = new pHStrip(0, 0, 0, 0, "pHStrip");
			testTube = new testTube(testTubeX, testTubeY, 0, 0, "testtube");
			movers.add(testTube);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void update(MouseEvent me) {		
		if(me != null) {//get source id every tick
			buttonSelected(me);
		}
		
		System.out.println(me.getEventType());

		switch (gs) {
		case START :
			gs = GameState.WS_COLLECTTUTORIAL;
		case WS_COLLECTTUTORIAL:
			Bottle.move(bottleX, maxHeight, bottleX, maxDepth);
			if (me.getEventType() == MouseEvent.MOUSE_PRESSED) {
				if (!filled && Bottle.getY() > waterLevel && btnSourceId.equals("Fill")) {
					fillBottle();
				}
				if (filled && btnSourceId.equals("Play")) {
					movers.remove(Bottle);
					gs = GameState.WS_COLLECT;
				}
			}
			
			break;
		case WS_COLLECT :
			if(!collectSet) {
				filled=false;
				Bottle.setValue("Bottle");
				//movers.remove(Bottle);
				movers.add(Bottle);
				collectSet=true;
			}
			//double startx, double starty, double endx, double endy)
			Bottle.move(bottleX, maxHeight, bottleX, maxDepth);
			
			if (me.getEventType() == MouseEvent.MOUSE_PRESSED) {
				if (!filled && Bottle.getY() > waterLevel && btnSourceId.equals("Fill")) {
					fillBottle();
				}
				if (filled && btnSourceId.equals("Lab")) {
					movers.remove(Bottle);
					gs = GameState.WS_PHTUTORIAL;
				}
			}
			
			
			break;
		case WS_PHTUTORIAL :
			if(!labSet) { // if lab is not set up
				addObjects(gs);
				labSet = true;
			}
			
			if (me.getEventType()==MouseEvent.MOUSE_PRESSED) {
				if (btnSourceId.equals("phStripBox")) {
					if(!gotStrip) {
						movers.add(pHStrip);
						movers.add(testTube);
						gotStrip=true;
					}
				}
				
				if(gotStrip && btnSourceId.equals("Play")) {
					gs=GameState.WS_PH;
				}
			}			
			
			pHStrip.move(me.getX(),me.getY());
			dipStrip();
			

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
			
			pHStrip.move(me.getX(),me.getY());
			dipStrip();
			
			if (me.getEventType() == MouseEvent.MOUSE_PRESSED) {
				
				if (btnSourceId.equals("phStripBox")) {
					if(!gotStrip) {
						movers.add(pHStrip);
						movers.add(testTube);
						gotStrip=true;
					}
				}
				
				try { //if btnSourceId is a valid double, then it was either up or down arrow
					Double.parseDouble(btnSourceId);
					checkGuess(me);
				} catch (NumberFormatException e) {}
				
				if (btnSourceId.equals("Submit")) {
					calculatePHScore();
					guessSubmit=true;
				}
				
			}
			
			prevME = me;
			break;

		default :
			break;
		}// end of switch
		
	}	
		
	
		
	/**
	 * Checks if bottle has been filled, adds new full bottle object to datanode list if so
	 * 
	 * @author AG
	 * 
	 * @return boolean true if bottle is full
	 */
	
	public void fillBottle() {
		filled=true;
		Bottle.setValue("fullBottle");
		if (Bottle.getYIncr() > 0) {
			Bottle.setYIncr(-Bottle.getYIncr());
		}
		if (gs!=GameState.WS_COLLECTTUTORIAL){
			score += calculateCollectSore();
		}
	}
	
	public void setPH() {
		pH = r.nextInt((pHMax - pHMin) + 1) + pHMin;
		
		for (Mover m : movers) {
			if(m instanceof pHStrip) {
				pHStrip ma = (pHStrip) m;
				ma.setpH(pH);
			}
		}
	}
	
	
	/*
	 * Calculates the Collecting Water Score by mapping the Bottle's current height within the gradient image on the background.
	 * The closer the bottle is to the middle, the more points you get, maximum being <code>MAX_COLLECT_POINTS</code>.
	 * 
	 * @author Ryan Peters
	 * @returns	score
	 */
	private int calculateCollectSore() {
		if (Bottle.getY() < waterLevel) {return 0;}
		return (int) (MAX_COLLECT_POINTS - Math.abs(CORRECT_LEVEL - Bottle.getTranslatedY()));
	}

	
	/**
	 * @author Abrenner
	 * logic for determining if pHStrip is within bounds of testtube and has been dipped in water
	 * changes boolean isDipped to true upon meeting criteria
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
	
	public void changeColor(int ph) {
		pHStrip.setValue("pHStrip"+ph);
		//System.out.println("ACTUAL PH:" + pH);
	}
	
	public void buttonSelected(MouseEvent me) {

		if (me.getEventType()==MouseEvent.MOUSE_PRESSED) {
			try {
				btnSourceId = ((Button) me.getSource()).getId();
			} catch (ClassCastException e) {}
		}
	}
	
	public void checkGuess(MouseEvent me) {
		System.out.println("IM HERE");
		if (me.getEventType()==MouseEvent.MOUSE_PRESSED) {
			try {
				btnSourceId = ((Button) me.getSource()).getId();
				
				pHGuess += Double.parseDouble(btnSourceId);
				
				System.out.println("pHGuess is " + pHGuess);
				
			} catch (ClassCastException e) {}
		}
	}
	
	void calculatePHScore(){
		System.out.println("PH:"+pH);
		System.out.println("GUESS:"+pHGuess);
		if(pHGuess == pH) {
			score += MAX_PH_SCORE;
		} else {
			score = (int) (MAX_PH_SCORE - (((Math.abs(pH - pHGuess) % 0.5) * 2))); //every 0.5 off, get 50 pts off
			}
		}
	
	// Movers related to WS
	class Bottle extends Mover {
		public Bottle(int x, int y, int xIncr, int yIncr, String value) {
			super(x, y, bottleImageWidth, bottleImageHeight, xIncr, yIncr, value);
		}
 	}
	 
	public class pHStrip extends Mover{
		double pH;

		public pHStrip(int x, int y, int xIncr, int yIncr, String value) {
			super(x, y, pHStripWidth, pHStripHeight, xIncr, yIncr, value);
		}
		
		void setpH(double pH) {
			this.pH=pH;
		}
		
		public double getpH() {
			return pH;
		}
	}
	
	
	public class testTube extends Mover{
		public testTube(int x, int y, int xIncr, int yIncr, String value) {
			super(x, y, testTubeImageWidth, testTubeImageHeight, xIncr, yIncr, value);
		}
	}

	
}
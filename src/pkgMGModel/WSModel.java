package pkgMGModel;

import java.util.Random;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pkgEnum.Game;
import pkgEnum.GameState;
import pkgMover.Mover;

public class WSModel extends MinigameModel{
	 
	// WS_COLLECT
	Mover Bottle;
	final int bottleImageWidth = 75;
	final int bottleImageHeight = 75;
	final int bottleX = backgroundWidth/2;
	
	final int maxHeight = bottleImageHeight;
	final int maxDepth = backgroundHeight-bottleImageHeight-100;
	
	int waterLevel = backgroundHeight/2;
	int shallowLevel=backgroundHeight*3/5;
	int correctLevel=backgroundHeight*7/10;
	int deepLevel=backgroundHeight*4/5;
	boolean filled = false;
	
	// WS_PH
	
	Mover pHStrip;
	final int pHStripWidth = 30;
	final int pHStripHeight = 100;
	final int phX =50;
	final int phY= 50;
	
	final int pHMax=9;
	final int pHMin=5;
	int pH;
	boolean isDipped = false;
	
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
	// left = 365x
	// right = 485x
	// water = 425y
	// bottom = 680y
	boolean labSet = false;
	
	public WSModel() {
		g = Game.WATERSAMPLING;

	//	gs = GameState.WS_COLLECT; 

		gs = GameState.WS_COLLECT; /////
		//gs = GameState.WS_PH; 
		addObjects(gs);
	}
	
	//public Mover(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
	public void addObjects(GameState gs) {
		
		switch (gs) {
		case WS_COLLECT :
			Bottle = new Bottle(bottleX, maxHeight, 0, 15, "Bottle");
			movers.add(Bottle);
			break;
		case WS_PH : 
			setPH();
			pHStrip = new pHStrip(phX, phY, 0, 0, "pHStrip");
			testTube = new testTube(testTubeX, testTubeY, 0, 0, "testtube");
			movers.add(pHStrip);
			movers.add(testTube);
			break;
		default:
			break;
		}
		
		
		
	}
	
	@Override
	public void update(MouseEvent me) {		
		// Switch statement to differentiate between GameStates {START, WS_COLLECT, WS_PH, WS_TEMP, FINISHED}
		switch (gs) {
		case START :
			gs = GameState.WS_COLLECT;
		case WS_COLLECT :
		
			//double startx, double starty, double endx, double endy)
			Bottle.move(bottleX, maxHeight, bottleX, maxDepth);
		
			if(Bottle.getY()> waterLevel && me.getEventType() == MouseEvent.MOUSE_PRESSED) {
				fillBottle();
			}
			if(filled && Bottle.getY()< waterLevel && me.getEventType() == MouseEvent.MOUSE_PRESSED) {
				movers.remove(Bottle);
				gs=GameState.WS_PH;
			}
			break;
		
		
		case WS_PH :
			if(!labSet) { // if lab is not set up
				addObjects(gs);
				labSet = true;
			}
			

			pHStrip.move(me.getX(),me.getY());
			System.out.println(pHStrip.getX() + ", " + pHStrip.getY());


			dipStrip(); 

			break;
		
		
			
		case WS_TEMP :
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
		if(Bottle.getY()>=shallowLevel && Bottle.getY()<correctLevel) {
			score+=50;
		} else if (Bottle.getY()>=correctLevel && Bottle.getY()<deepLevel) {
			score+=100;
		}
	}
	
	public void setPH() {
		Random rand = new Random();
		pH=rand.nextInt((pHMax - pHMin) + 1) + pHMin;
	}
	
	/**
	 * @author Abrenner
	 * logic for determining if pHStrip is within bounds of testtube and has been dipped in water
	 * changes boolean isDipped to true upon meeting criteria
	 */
	public void dipStrip() {
		//System.out.println("pHStrip X: " + movers.get(0).getY());
		
		// setting up logic for dipping pHStrip within testTube bounds
		if(pHStrip.getX() >= testTubeLeftSide &&
			pHStrip.getX() <= testTubeRightSide &&
			pHStrip.getY() >= testTubeWaterLevel &&
			 pHStrip.getY() <= testTubeBottom) {
						
			
			
				isDipped = true;
				changeColor(pH);
				System.out.println("Strip is Dipped!!");
		}
		
	}
	
	public void changeColor(int ph) {
		pHStrip.setValue("pHStrip"+ph);
		
	}
	
	
	// Movers related to WS
	class Bottle extends Mover {
		public Bottle(int x, int y, int xIncr, int yIncr, String value) {
			super(x, y, bottleImageWidth, bottleImageHeight, xIncr, yIncr, value);
		}
 	}
	 
	public class pHStrip extends Mover{

		public pHStrip(int x, int y, int xIncr, int yIncr, String value) {
			super(x, y, pHStripWidth, pHStripHeight, xIncr, yIncr, value);
		}
		
		public void setColor(String color) {
			
		}
	}
	
	
	public class testTube extends Mover{
		public testTube(int x, int y, int xIncr, int yIncr, String value) {
			super(x, y, testTubeImageWidth, testTubeImageHeight, xIncr, yIncr, value);
		}
	}
	
	
}
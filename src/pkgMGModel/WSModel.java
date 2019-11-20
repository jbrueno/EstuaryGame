package pkgMGModel;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
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
	final int pHStripWidth = 50;
	final int pHStripHeight = 100;
	int pH;
	
	Mover testTube;
	final int testTubeImageWidth = 200;
	final int testTubeImageHeight = 500;
	boolean labSet = false;
	
	public WSModel() {
		g = Game.WATERSAMPLING;
		gs = GameState.WS_COLLECT; /////
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
			pHStrip = new pHStrip(200, 400, 50, 100, 0, 0, "PHStrip");
			testTube = new testTube(300, 300, 0, 0, "testtube");
			movers.add(pHStrip);
			movers.add(testTube);
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

			System.out.println(movers);
			
			
			
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
			score+=1;
		} else if (Bottle.getY()>=correctLevel && Bottle.getY()<deepLevel) {
			score+=5;
		}
	}
	//
	class Bottle extends Mover {
		public Bottle(int x, int y, int xIncr, int yIncr, String value) {
			super(x, y, bottleImageWidth, bottleImageHeight, xIncr, yIncr, value);
		}
 	}
	 
	public class pHStrip extends Mover{

		public pHStrip(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
			super(x, y, pHStripWidth, pHStripHeight, xIncr, yIncr, value);
		}
		
		boolean isDipped = false; // has pHStripp been dipped & tested yet?
	}
	
	
	public class testTube extends Mover{
		public testTube(int x, int y, int xIncr, int yIncr, String value) {
			super(x, y, testTubeImageWidth, testTubeImageHeight, xIncr, yIncr, value);
		}
	}
	
	
}
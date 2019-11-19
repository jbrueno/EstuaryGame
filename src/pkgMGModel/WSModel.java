package pkgMGModel;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import pkgEnum.Game;
import pkgEnum.GameState;
import pkgMover.Mover;

public class WSModel extends MinigameModel{
	
	Mover Bottle;
	Mover PHStrip;
	final int bottleImageWidth = 75;
	final int bottleImageHeight = 75;
	final int bottleX = backgroundWidth/2;
	
	final int maxHeight = bottleImageHeight;
	final int maxDepth = backgroundHeight-bottleImageHeight-100;
	
	int waterLevel = backgroundHeight/2;
	int shallowLevel=400;
	int correctLevel=500;
	int deepLevel=600;
	boolean filled = false;
	
	int pH;
	
	public WSModel() {
		g = Game.WATERSAMPLING;
		addObjects();
	}
	
	//public Mover(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
	public void addObjects() {
		Bottle = new Bottle(bottleX, maxHeight, 0, 15, "Bottle");
		movers.add(Bottle);
		PHStrip = new PHStrip(0,0,0,0,0,0,"PHStrip");
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
		
		
		
		case WS_PH :
			 System.out.println("WS_PH !!");
		
		
		// **************** //
			
		case WS_TEMP :
		
		
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
	
	class Bottle extends Mover {
		public Bottle(int x, double y, int xIncr, int yIncr, String value) {
			super(x, y, bottleImageWidth, bottleImageHeight, xIncr, yIncr, value);
		}
 	}
	 
	public class PHStrip extends Mover{

		public PHStrip(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
			super(x, y, imageWidth, imageHeight, xIncr, yIncr, value);
		}
	}
}
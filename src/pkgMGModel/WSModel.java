package pkgMGModel;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import pkgEnum.Game;
import pkgEnum.GameState;
import pkgMover.Mover;

public class WSModel extends MinigameModel{
	
	Mover Bottle;
	final int bottleImageWidth = 100;
	final int bottleImageHeight = 100;
	
	final int maxHeight = bottleImageHeight;
	final int maxDepth = backgroundHeight-bottleImageHeight;
	
	int waterLevel = backgroundHeight/2;
	boolean filled = false;
	
	public WSModel() {
		g = Game.WATERSAMPLING;
		addObjects();
	}
	
	//public Mover(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
	public void addObjects() {
		Bottle = new Bottle(backgroundWidth/2, maxHeight, 0, 15, "Bottle");
		movers.add(Bottle);
	}
	
	@Override
	public void update(MouseEvent me) {
		Bottle.move(me.getX(), me.getY());
		
		if(Bottle.getY()> waterLevel && me.getEventType() == me.MOUSE_CLICKED) {
			fillBottle();
		}
		
		if(filled) {
			movers.remove(Bottle);
			gs=GameState.WS_PH;
		}
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
	}
	
	class Bottle extends Mover {
		public Bottle(int x, double d, int xIncr, int yIncr, String value) {
			super(x, d, bottleImageWidth, bottleImageHeight, xIncr, yIncr, value);
		}
 	}
	 
	public class PHStrip extends Mover{

		public PHStrip(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
			super(x, y, imageWidth, imageHeight, xIncr, yIncr, value);
		}
	}
}

package pkgMGModel;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import pkgEnum.Game;
import pkgEnum.GameState;
import pkgMover.Mover;

public class WSModel extends MinigameModel{
	
	Mover Bottle;
	Mover testtube;
	Mover PHStrip;
	int bottleImageWidth = 75;
	int bottleImageHeight = 75;
	int tubeImageWidth = 250;
	int tubeImageHeight = 250;
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
		gs = GameState.WS_COLLECT;
		addObjects();
	}
	
	//public Mover(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
	public void addObjects() {
		Bottle = new Bottle(bottleX, maxHeight, bottleImageWidth, bottleImageHeight, 0, 15, "Bottle");
		movers.add(Bottle);
		testtube= new Bottle(0,0,0,0,0,0,"testtube");
		PHStrip = new PHStrip(0,0,0,0,0,0,"PHStrip");
	}
	
	@Override
	public void update(MouseEvent me) {
		
		//ws_collect
		//double startx, double starty, double endx, double endy)
		System.out.println(gs);
		if (gs==GameState.WS_COLLECT){
			Bottle.move(bottleX, maxHeight, bottleX, maxDepth);
			if(Bottle.getY()> waterLevel && me.getEventType() == MouseEvent.MOUSE_PRESSED) {
				fillBottle();
				bottleImageWidth=500;
				bottleImageHeight=500;
			}
			
			if(filled && Bottle.getY()< waterLevel && me.getEventType() == MouseEvent.MOUSE_PRESSED) {
			
				gs=GameState.WS_PH;
			}
		}
		
		if(gs==GameState.WS_PH) {
			Bottle.setValue("testtube");
			Bottle.setX(backgroundWidth/3);
			Bottle.setY(backgroundHeight*2/3);
		}
		
		if (gs==GameState.WS_TEMP) {
			//do something
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
		if(Bottle.getY()>=shallowLevel && Bottle.getY()<correctLevel) {
			score+=1;
		} else if (Bottle.getY()>=correctLevel && Bottle.getY()<deepLevel) {
			score+=5;
		}
	}
	
	class Bottle extends Mover {
		public Bottle(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
			super(x, y, imageWidth, imageHeight, xIncr, yIncr, value);
		}
 	}
	 
	public class PHStrip extends Mover{

		public PHStrip(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
			super(x, y, imageWidth, imageHeight, xIncr, yIncr, value);
		}
	}
}
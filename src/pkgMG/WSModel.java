package pkgMG;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import pkgEnum.Game;
import pkgMover.Bottle;
import pkgMover.Mover;

public class WSModel extends MinigameModel{
	
	Mover Bottle;
	Mover fullBottle;
	boolean fill = false;
	
	public WSModel() {
		g = Game.WATERSAMPLING;
		addObjects();
	}
	
	//public Mover(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
	public void addObjects() {
		Bottle= new Bottle(600,50,200,200,0,10,"Bottle");
		fullBottle=new Bottle(600, 350, 200, 200, 0, -8, "fullBottle");
		dns.add(Bottle);
	}
	
	@Override
	public void update(MouseEvent me) {
		Bottle.move();
		if(Bottle.getY() == 450) {
			fill=true;
		}
		
		if(checkFill()) {
		fullBottle.move();
		}
	}
	
	public boolean checkFill() {
		if (fill==true) {
			if(!dns.contains(fullBottle)) {
				dns.remove(Bottle);
				dns.add(fullBottle);
			}
			return true;
		}
		return false;
	}
}

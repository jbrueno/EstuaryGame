package pkgMG;


import java.util.ArrayList;
import java.util.Random;

import javafx.scene.input.MouseEvent;
import pkgEnum.Game;
import pkgEnum.GameState;
import pkgMover.Food;
import pkgMover.Mover;
import pkgMover.Seaweed;
import pkgMover.Terrapin;
import pkgMover.Trash;

public class SCModel extends MinigameModel{
	
	Terrapin terry;
	ArrayList<Mover> items =  new ArrayList<Mover>();
	private final double waterThreshold = 100;
	final long startNanoTime = System.nanoTime();
	GameState gameState;
	 
	/**Constructor that will be given information on the Terrapins
	 *  starting location, the movers and food currently onscreen
	 *  and the water Y threshold Terry needs to breather
	 *  @author HM
	 * 
	 */
	public SCModel(){
		g = Game.SIDESCROLLER;
		terry = new Terrapin(backgroundWidth/2, backgroundHeight/2, 2, 0);
		
		gameState = GameState.INPROGRESS;
		score = 0;
	}

	@Override
	public void update(MouseEvent me) {
		if (terry.getY() >= waterThreshold) {
			terry.breathe();
		} else {
			terry.airAmount = terry.airAmount - 0.5;
			System.out.println("Terrapin air level " + terry.airAmount);
		}
		
		for (Mover m : items) {
			m.setX(m.getX() + m.getxIncr());
			
			if (terry.getX() >= m.getX() - m.getImageWidth() && terry.getX() <= m.getX() + m.getImageWidth()) {
				if (m instanceof Seaweed || m instanceof Food || m instanceof Trash) {
					score += m.getScoreChange();
					//terry.changeXIncr(m.getSpeedChange());
				}
				
			}
			
			if (m.getX() < 0) {
				items.remove(m);
			}
		}
		
	}

	
	public void addNewMover() {
		if (movers.size() < 3) {
			int newMover = new Random().nextInt(10);
			if (newMover < 4)  {
				Seaweed s = new Seaweed(backgroundWidth);
				movers.add(s);
			} else if (newMover < 8) {
				Food f = new Food(backgroundWidth);
				movers.add(f);
			} else {
				Trash t = new Trash(backgroundWidth);
				movers.add(t);
			}
		}
	}

	@Override
	public void handleBorderCollision(Mover m) {
		// TODO Auto-generated method stub
		
	}
}


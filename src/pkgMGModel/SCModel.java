package pkgMGModel;


import java.util.ArrayList;
import java.util.Random;

import javafx.scene.input.MouseEvent;
import pkgEnum.Game;
import pkgEnum.GameState;
import pkgMover.Food;
import pkgMover.Mover;
import pkgMover.SCMover;
import pkgMover.Seaweed;
import pkgMover.Terrapin;
import pkgMover.Trash;

public class SCModel extends MinigameModel{
	
	Terrapin terry;
	ArrayList<SCMover> items =  new ArrayList<SCMover>();
	private final double waterThreshold = 2 * backgroundHeight/3;
	final long startNanoTime = System.nanoTime();
	GameState gameState;
	int seaweedY = 100;
	int foodY = 50;
	 
	/**Constructor that will be given information on the Terrapins
	 *  starting location, the movers and food currently onscreen
	 *  and the water Y threshold Terry needs to breath
	 *  @author HM
	 * 
	 */
	public SCModel(){
		g = Game.SIDESCROLLER;
		terry = new Terrapin(10, backgroundHeight/2, 10, 0);
		
		gameState = GameState.INPROGRESS;
		score = 0;
		
		Seaweed s = new Seaweed(backgroundWidth/2, backgroundHeight - seaweedY, 100, 100, -10, 0, "Seaweed");
		Seaweed s2 = new Seaweed(backgroundWidth/4, backgroundHeight - seaweedY, 100, 100, -10, 0, "Seaweed");
		Food f = new Food(backgroundWidth, backgroundWidth - foodY, 50, 50, -10, 0, "Food");

		items.add(s);
		items.add(s2);
		items.add(f);
		
		movers.removeAll(getMovers());
		
		for (SCMover sc : items) {
			movers.add(sc);
		}
		movers.add(terry);
	}

	
	/**
	 * Updates the game every tick.
	 * 
	 * First looks at terrapin's position to see if it needs to breathe or lose air
	 * Next iterates through every mover in the items array:
	 * 	- checking if the xLoc is still onscreen, and removing it otherwise
	 * 	- checking if there is a collision with terry and updating the score
	 * 
	 * Finally if the size of items is <=3, a need item is randomly added
	 * 
	 */
	@Override
	public void update(MouseEvent me) {
		
		
		if (terry.getY() >= waterThreshold) {
			terry.breathe();
		} else {
			terry.airAmount = terry.airAmount - 0.5;
			System.out.println("Terrapin air level " + terry.airAmount);
		}
		
		

		for (SCMover m : items) {
			if (m.getX() < 0) {
				items.remove(0);
			} else {
				m.setX(m.getX() + m.getxIncr());
				if (terry.getX() >= m.getX() - m.getImageWidth() && terry.getX() <= m.getX() + m.getImageWidth()) {
					score += m.getScoreChange();
					terry.changeXIncr(m.getSpeedChange());
					if (!(m instanceof Seaweed)) {
						items.remove(m);
					}
				} 
			}
		}
		
		if (items.size() <= 3) {
			addNewMover();
			movers.removeAll(getMovers());
			movers.addAll(items);
			movers.add(terry);
		}
	}
	
	public boolean terryCollision(Mover m) {
		boolean collision = false;
		if (terry.getX() >= m.getX() - m.getImageWidth() && terry.getX() <= m.getX() + m.getImageWidth()
			&& terry.getY() >= m.getY() - m.getImageHeight() && terry.getY() <= m.getY() + m.getImageWidth()) {
			collision = true;
		}
		return collision;
			
	}

	
	public void addNewMover() {
		int newMover = new Random().nextInt(10);
		if (newMover < 4)  {
			Seaweed s = new Seaweed(backgroundWidth, backgroundHeight - seaweedY);
			System.out.println("seaweed added");
			items.add(s);
		} else if (newMover < 8) {
			int y = new Random().nextInt((int) waterThreshold);
			Food f = new Food(backgroundWidth, backgroundHeight - y);
			System.out.println("food added");
			items.add(f);
		} else {
			int y = new Random().nextInt((int) waterThreshold);
			Trash t = new Trash(backgroundWidth, backgroundHeight - y);
			System.out.println("trashed added");
			items.add(t);
		}
	}
	

	@Override
	public void handleBorderCollision(Mover m) {
		// TODO Auto-generated method stub
		
	}
}
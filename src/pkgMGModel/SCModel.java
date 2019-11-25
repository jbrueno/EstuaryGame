package pkgMGModel;


import java.util.ArrayList;
import java.util.Iterator;
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
	private final double waterThreshold = 150;
	final long startNanoTime = System.nanoTime();
	int seaweedY = 150;
	double breathLostOnTick = 0.5;
	int currentItemSpeed = -10;
	final int itemYSpeed = 0;
	boolean timerSet = false;
	 
	/**Constructor that will be given information on the Terrapins
	 *  starting location, the movers and food currently onscreen
	 *  and the water Y threshold Terry needs to breath
	 *  @author HM
	 * 
	 */
	public SCModel(){
		g = Game.SIDESCROLLER;
		terry = new Terrapin(100, backgroundHeight/2, 0, 10);
		time = 600;
		
		gs = GameState.INPROGRESS;
		score = 0;
		
		Seaweed s = new Seaweed(backgroundWidth/2, backgroundHeight - seaweedY, 100, 100, currentItemSpeed, itemYSpeed, "Seaweed");
		Seaweed s2 = new Seaweed(backgroundWidth/4, backgroundHeight - seaweedY, 100, 100, currentItemSpeed, itemYSpeed, "Seaweed");
		Food f = new Food(backgroundWidth, backgroundHeight/2, 50, 50, currentItemSpeed, itemYSpeed, "Food");

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
		if (! timerSet) {
			setUpTimer();
			timerSet = true;
		}
		
		
		if (terry.getY() <= waterThreshold) {
			terry.breathe();
		} else {
			if (terry.getAirAmount() > 0) {
				terry.holdBreath();
			} else {
				gs = GameState.FINISHED;
			}
		}
		
		try {
			terry.move(me.getX(), me.getY());
		} catch (NullPointerException e) {
			System.out.println("caught null pointer exception");
		}
 		
		

		Iterator<SCMover> itemsIt = items.iterator();
		boolean collisionOccured = false;
		while (itemsIt.hasNext()) {
			SCMover m = (SCMover) itemsIt.next();
			if (m.getX() < 0) {
				itemsIt.remove();
			} else {
				m.move();
				if (m.collison(terry)) {
					collisionOccured = true;
					System.out.println("collision with " + m);
					changeCurrentSpeed(m);
					score = m.changeScore(score);
					if (!(m instanceof Seaweed)) {
						itemsIt.remove();
					}
					System.out.println("score is now: " + score);
				} 
			}

		}
		if (collisionOccured) {
			changeSpeeds();
		}
		if (items.size() <= 3) {
			addNewMover();
			movers.removeAll(getMovers());
			movers.addAll(items);
			movers.add(terry);
		}
		
		
		
		System.out.println(items);
	}
	
	public boolean terryCollision(Mover m) {
		boolean collision = false;
		if (terry.getX() >= m.getX() - m.getImageWidth() 
			&& terry.getX() <= m.getX() + m.getImageWidth()
			&& terry.getY() >= m.getY() - m.getImageHeight() 
			&& terry.getY() <= m.getY() + m.getImageWidth()) {
			collision = true;
		}
		return collision;
			
	}

	
	public void addNewMover() {
		int newMover = new Random().nextInt(10);
		if (newMover < 4)  {
			Seaweed s = new Seaweed(backgroundWidth, backgroundHeight - seaweedY, currentItemSpeed);
			System.out.println("seaweed added");
			items.add(s);
		} else if (newMover < 8) {
			int y =  new Random().nextInt((int) (backgroundHeight - waterThreshold));
			Food f = new Food(backgroundWidth, backgroundHeight - y, currentItemSpeed);
			System.out.println("food added");
			items.add(f);
		} else {
			int y = new Random().nextInt((int) (backgroundHeight - waterThreshold));
			Trash t = new Trash(backgroundWidth, backgroundHeight - y, currentItemSpeed);
			System.out.println("trashed added");
			items.add(t);
		}
	}
	

	@Override
	public void handleBorderCollision(Mover m) {
		// TODO Auto-generated method stub
		
	}
	
	private void changeSpeeds() {
		Iterator<SCMover> itemIt = items.iterator();
		while (itemIt.hasNext()) {
			SCMover m = (SCMover) itemIt.next();
			m.changeSpeed(currentItemSpeed);
		}
	}
	
	public void changeCurrentSpeed(SCMover m) {
		currentItemSpeed += m.getCollisionSpeedChange();
	}
}
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
import pkgMover.Trash;
import pkgMover.Terrapin;

public class SCModel extends MinigameModel{
	
	Terrapin terry;
	ArrayList<SCMover> items =  new ArrayList<SCMover>();
	private final double waterThreshold = 150;
	final long startNanoTime = System.nanoTime();
	int seaweedY = 50;
	double breathLostOnTick = 0.5;
	int currentItemSpeed = -25;
	final int itemYSpeed = 0;
	boolean timerSet = false;
	int seaweedHeight = 100;
	int tutorialStep = 0;
	 
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
		
		gs = GameState.TUTORIAL;
		score = 0;
		
		Food f = new Food(backgroundWidth, backgroundHeight/2, seaweedHeight/2, seaweedHeight/2, currentItemSpeed, itemYSpeed, "Food");
		items.add(f);
		
		//movers.removeAll(getMovers());
		for (Mover sc : items) {
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
		switch(gs) {
			case INPROGRESS:
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
		
				terry.move(me.getX(), me.getY());
				
 		
				Iterator<SCMover> itemsIt = items.iterator();
				boolean collisionOccured = false;
				while (itemsIt.hasNext()) {
					SCMover m = (SCMover) itemsIt.next();
					if (m.getX() < 0) {
						itemsIt.remove();
					} else {
						m.move();
						if (isCollision(m, terry)) {
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
				break;
				
			case TUTORIAL:
				switch(tutorialStep) {
				case 0:
					if (items.size() == 0) {
						items.add(new Trash(backgroundWidth, backgroundHeight/2, currentItemSpeed));
						tutorialStep++;
					} else {
						for (Mover m : items) {
							m.move();
						}
						terry.move(me.getX(), me.getY());
					}
					break;
				case 1:
					if (items.size() == 0) {
						items.add(new Seaweed(backgroundWidth, backgroundHeight - seaweedY, currentItemSpeed, seaweedHeight));
						tutorialStep++;
					} else {
						for (Mover m : items) {
							m.move();
						}
						terry.move(me.getX(), me.getY());
					}
					break;
				case 2:
					if (items.size() == 0) {
						tutorialStep++;
					} else {
						for (Mover m : items) {
							m.move();
						}
						terry.move(me.getX(), me.getY());
					}
					break;
				case 3:
					if (terry.getY() <= waterThreshold) {
						terry.breathe();
						tutorialStep++;
					} else {
						terry.setAirAmount(50);
					}
				case 4:
					gs = GameState.INPROGRESS;
					break;
				}
		}
				
		
	}
	

	
	public void addNewMover() {
		int newMover = r.nextInt(10);
		if (newMover < 4)  {
			Seaweed s = new Seaweed(backgroundWidth, backgroundHeight - seaweedY, currentItemSpeed, seaweedHeight);
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
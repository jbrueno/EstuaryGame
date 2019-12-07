package pkgMGModel;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javafx.scene.control.Button;
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
	private static final long serialVersionUID = 17L;
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
	boolean tutorialPlay = false;
	boolean set = false;
	 
	/**Constructor that will be given information on the Terrapins
	 *  starting location, the movers and food currently onscreen
	 *  and the water Y threshold Terry needs to breath
	 *  @author HM
	 * 
	 */
	public SCModel(){
		g = Game.SIDESCROLLER;
		terry = new Terrapin(200, backgroundHeight/2, 0, 10);
		time = 600;
		
		gs = GameState.SC_TUTORIAL_FOOD;
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
						if (isCollision(terry, m)) {
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
				
			case SC_TUTORIAL_FOOD:
				if (me.getEventType() == MouseEvent.MOUSE_CLICKED){
					tutorialPlay = true;
					System.out.println(me.getEventType());
				} 
				
				System.out.println(tutorialPlay);
				
				if (tutorialPlay) {
					if (isCollision(terry, items.get(0))) {
						movers.remove(items.get(0));
						items.remove(0);
						tutorialPlay = false;
						gs = GameState.SC_TUTORIAL_TRASH;
					} else if (items.get(0).getX() < 0) {
						movers.removeAll(items);
						items.clear();
						items.add(new Food(backgroundWidth, backgroundHeight/2, currentItemSpeed));
						movers.addAll(items);
					} else {
						for (Mover m : items) {
							m.move();
						}
						terry.move(me.getX(), me.getY());
					}
				}
				break;
			case SC_TUTORIAL_TRASH:
				System.out.println(me.getSource());
				if (me.getEventType() == MouseEvent.MOUSE_CLICKED){
					tutorialPlay = true;
				}
				

				if (tutorialPlay) {
					if (!set) {
						items.add(new Trash(backgroundWidth, backgroundHeight/2, currentItemSpeed));
						movers.addAll(items);
						set = true;
					} else {

						for (Mover m : items) {
							if (isCollision(terry, m)) {
								movers.remove(m);
								items.remove(m);
								Trash t = new Trash(backgroundWidth, backgroundHeight/2, currentItemSpeed);
								movers.add(t);
								items.add(t);
							} else if (m.getX() < 0) {
								movers.removeAll(items);
								items.clear();
								tutorialPlay = false;
								gs = GameState.SC_TUTORIAL_SEAWEED;
								set = false;
							} else {
								m.move();
								terry.move(me.getX(), me.getY());
							}
						}
					}
				}
				break;
			case SC_TUTORIAL_SEAWEED:
				System.out.println(me.getSource());
				if (me.getEventType() == MouseEvent.MOUSE_CLICKED){
					tutorialPlay = true;
				} 

				if (tutorialPlay) {
					if (!set) {
						items.removeAll(items);
						items.add(new Seaweed(backgroundWidth, backgroundHeight - seaweedY, currentItemSpeed));
						movers.addAll(items);
						set = true;
					}
					
					if (items.get(0).getX() < 0) {
						movers.removeAll(items);
						items.clear();
						tutorialPlay = false;
						gs = GameState.SC_TUTORIAL_BREATH;
						set = false;
					} else {
						for (Mover m : items) {
							m.move();
						}
						terry.move(me.getX(), me.getY());
					}
				}
				break;
			case SC_TUTORIAL_BREATH:
				if (me.getEventType() == MouseEvent.MOUSE_CLICKED){
					tutorialPlay = true;
				}

				if (tutorialPlay) {
					terry.move(me.getX(), me.getY());
					if (terry.getY() < waterThreshold) {
						terry.breathe();
						gs = GameState.TUTORIAL;
					} else {
						terry.setAirAmount(50);
					}
				}
				break;
			case TUTORIAL:
				if (items.size() <= 3) {
					addNewMover();
					movers.removeAll(getMovers());
					movers.addAll(items);
					movers.add(terry);
					for (Mover m : items) {
						m.move();
					}
				}
				if (me.getEventType() == MouseEvent.MOUSE_CLICKED){
					gs = GameState.INPROGRESS;
				}
				break;
		default:
			break;
		}

	}

	
	public void addNewMover() {
		int newMover = r.nextInt(10);
		if (newMover < 2)  {
			Seaweed s = new Seaweed(backgroundWidth, backgroundHeight - seaweedY, currentItemSpeed);
			System.out.println("seaweed added");
			items.add(s);
		} else if (newMover < 7) {
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
	
	public boolean okClicked(MouseEvent me) {
		boolean clicked = false;
		if (me.getX() < backgroundWidth/2 + 20 && me.getX() > backgroundWidth/2 - 20) {
			System.out.println("in width");
			if (me.getY() < backgroundHeight/2 + 10 && me.getY() > backgroundHeight/2 - 10) {
				System.out.println("in height");
				if (me.getEventType() == MouseEvent.MOUSE_CLICKED) {
					System.out.println("mouse clicked");
					clicked = true;
				}
			}
		} 
		
		System.out.println("ok clicked: " + clicked);
		return clicked;
	}
	
	private void changeSpeeds() {
		Iterator<SCMover> itemIt = items.iterator();
		while (itemIt.hasNext()) {
			SCMover m = (SCMover) itemIt.next();
			m.changeSpeed(currentItemSpeed);
		}
	}
	
	public void changeCurrentSpeed(SCMover m) {
		currentItemSpeed = currentItemSpeed + m.getCollisionSpeedChange();
	}
}
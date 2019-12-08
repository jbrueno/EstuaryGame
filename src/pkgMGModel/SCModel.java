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
	int terrapinX = 200;
	int terrapinYIncr = 10;
	int terrapinXIncr = 0;
	int halfBackgroundHeight = backgroundHeight/2;
	int foodWidthHeight = 50;
	int gameTime = 600;
	int startScore = 0;
	int airDeathThreshold = 0;
	public int randSeaweedThreshold = 3;
	public int randFoodThreshold = 7;
	int maxMoversOnScreen = 3;
	 
	/**Constructor that will be given information on the Terrapins
	 *  starting location, the movers and food currently onscreen
	 *  and the water Y threshold Terry needs to breath
	 *  @author HM
	 * 
	 */
	public SCModel(){
		g = Game.SIDESCROLLER;
		terry = new Terrapin(terrapinX, halfBackgroundHeight, terrapinXIncr, terrapinYIncr);
		time = gameTime;
		
		gs = GameState.SC_TUTORIAL_FOOD;
		score = startScore;
		
		Food f = new Food(backgroundWidth, halfBackgroundHeight, foodWidthHeight, foodWidthHeight, currentItemSpeed, itemYSpeed, "Food");
		getItems().add(f);
		
		//movers.removeAll(getMovers());
		for (Mover sc : getItems()) {
			movers.add(sc);
		}
		movers.add(terry);
	}

	
	/**
	 * Updates the game every tick depending on the gameState. 
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
					if (terry.getAirAmount() > airDeathThreshold) {
						terry.holdBreath();
					} else {
						gs = GameState.FINISHED;
					}
				}
		
				terry.move(me.getX(), me.getY());
				
				
				Iterator<SCMover> itemsIt = getItems().iterator();
				boolean collisionOccured = false;
				while (itemsIt.hasNext()) {
					SCMover m = (SCMover) itemsIt.next();
					if (m.getX() < 0) {
						itemsIt.remove();
					} else {
						m.move();
						if (isCollision(m, terry)) {
							collisionOccured = true;
							changeCurrentSpeed(m);
							score = m.changeScore(score);
							if (!(m instanceof Seaweed)) {
								itemsIt.remove();
							}
						} 
					}

				}
				
				if (collisionOccured) {
					changeSpeeds();
				}
				
				if (getItems().size() <= maxMoversOnScreen) {
					addNewItem();
					movers.removeAll(getMovers());
					movers.addAll(getItems());
					movers.add(terry);
				}
				break;
				
			case SC_TUTORIAL_FOOD:
				if (me.getEventType() == MouseEvent.MOUSE_CLICKED){
					tutorialPlay = true;
				} 
				
				if (tutorialPlay) {
					if (isCollision(terry, getItems().get(0))) {
						movers.remove(getItems().get(0));
						getItems().remove(0);
						tutorialPlay = false;
						gs = GameState.SC_TUTORIAL_TRASH;
					} else if (getItems().get(0).getX() < 0) {
						movers.removeAll(getItems());
						getItems().clear();
						getItems().add(new Food(backgroundWidth, backgroundHeight/2, currentItemSpeed));
						movers.addAll(getItems());
					} else {
						for (Mover m : getItems()) {
							m.move();
						}
						terry.move(me.getX(), me.getY());
					}
				}
				break;
			case SC_TUTORIAL_TRASH:
				if (me.getEventType() == MouseEvent.MOUSE_CLICKED){
					tutorialPlay = true;
				}

				if (tutorialPlay) {
					if (!set) {
						getItems().add(new Trash(backgroundWidth, backgroundHeight/2, currentItemSpeed));
						movers.addAll(getItems());
						set = true;
					} else {
						for (Mover m : getItems()) {
							if (isCollision(terry, m)) {
								movers.remove(m);
								getItems().remove(m);
								Trash t = new Trash(backgroundWidth, backgroundHeight/2, currentItemSpeed);
								movers.add(t);
								getItems().add(t);
							} else if (m.getX() < 0) {
								movers.removeAll(getItems());
								getItems().clear();
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
				if (me.getEventType() == MouseEvent.MOUSE_CLICKED){
					tutorialPlay = true;
				} 

				if (tutorialPlay) {
					if (!set) {
						getItems().removeAll(getItems());
						getItems().add(new Seaweed(backgroundWidth, backgroundHeight - seaweedY, currentItemSpeed));
						movers.addAll(getItems());
						set = true;
					}
					
					if (getItems().get(0).getX() < 0) {
						movers.removeAll(getItems());
						getItems().clear();
						tutorialPlay = false;
						gs = GameState.SC_TUTORIAL_BREATH;
						set = false;
					} else {
						for (Mover m : getItems()) {
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
				if (getItems().size() <= 3) {
					addNewItem();
					movers.removeAll(getMovers());
					movers.addAll(getItems());
					movers.add(terry);
					for (Mover m : getItems()) {
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

	
	public void addNewItem() {
		int newMover = r.nextInt(10);
		if (newMover < randSeaweedThreshold)  {
			Seaweed s = new Seaweed(backgroundWidth, backgroundHeight - seaweedY, currentItemSpeed);
			System.out.println("seaweed added");
			getItems().add(s);
		} else if (newMover < randFoodThreshold) {
			int y =  new Random().nextInt((int) (backgroundHeight - waterThreshold));
			Food f = new Food(backgroundWidth, backgroundHeight - y, currentItemSpeed);
			System.out.println("food added");
			getItems().add(f);
		} else {
			int y = new Random().nextInt((int) (backgroundHeight - waterThreshold));
			Trash t = new Trash(backgroundWidth, backgroundHeight - y, currentItemSpeed);
			System.out.println("trashed added");
			getItems().add(t);
		}
		
		System.out.println("items size " + items.size());
	}

	private void changeSpeeds() {
		Iterator<SCMover> itemIt = getItems().iterator();
		while (itemIt.hasNext()) {
			SCMover m = (SCMover) itemIt.next();
			m.changeSpeed(currentItemSpeed);
		}
	}
	
	public void changeCurrentSpeed(SCMover m) {
		currentItemSpeed = currentItemSpeed + m.getCollisionSpeedChange();
	}


	public ArrayList<SCMover> getItems() {
		return items;
	}
}
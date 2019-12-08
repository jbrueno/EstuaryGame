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
	private static final double WATERTRESHOLD = 150;
	final long startNanoTime = System.nanoTime();
	private static final int SEAWEEDY = 50;
	private int currentItemSpeed = -25;
	private static final int ITEMYSPEED = 0;
	boolean timerSet = false;
	int tutorialStep = 0;
	boolean tutorialPlay = false;
	boolean set = false;
	int terrapinX = 200;
	final static int TERRAPINYINCR = 10;
	final static int TERRAPINXINCR = 0;
	int halfBackgroundHeight = backgroundHeight/2;
	final static int FOODWIDTHHEIGHT = 50;
	int gameTime = 600;
	final static int STARTSCORE = 0;
	int airDeathThreshold = 0;
	public int randSeaweedThreshold = 3;
	public int randFoodThreshold = 7;
	int maxMoversOnScreen = 3;
	 
	/**Constructor that will be given information on the Terrapins
	 *  starting location, the movers and food currently onscreen
	 *  and the water Y threshold Terry needs to breath
	 *  
	 *  @author HM
	 * 
	 */
	public SCModel(){
		g = Game.SIDESCROLLER;
		terry = new Terrapin(terrapinX, halfBackgroundHeight, TERRAPINYINCR, TERRAPINYINCR);
		time = gameTime;
		
		gs = GameState.SC_TUTORIAL_FOOD;
		score = STARTSCORE;
		
		Food f = new Food(backgroundWidth, halfBackgroundHeight, FOODWIDTHHEIGHT, FOODWIDTHHEIGHT, getCurrentItemSpeed(), ITEMYSPEED, "Food");
		getItems().add(f);
		
		//movers.removeAll(getMovers());
		for (Mover sc : getItems()) {
			movers.add(sc);
		}
		movers.add(terry);
	}

	
	/**
	 * Updates the game as a function of the gameState
	 * 
	 * First goes through the tutorial GameStates, 
	 * 		SC_TUTORIAL_FOOD:
	 * 			teaches the player consequences of interacting with food
	 * 			player must collide with the on-screen food to continue to next state
	 * 		SC_TUTORIAL_TRASH:
	 * 			teaches the player the consequences of interacting with trash
	 * 			player must avoid collision with the on-screen trash to continue
	 * 		SC_TUTORIAL_SEAWEED:
	 * 			teaches the player how to recognize seaweed
	 * 			player can collide or not with the seaweed, does not affect gameplay
	 * 		SC_TUTORIAL_BREATH:
	 * 			teaches the player mechanisms for handling air level
	 * 			player must direct terrapin above the water threshold in order to continue to next gameState
	 * 		TUTORIAL:
	 * 			sets up INPROGRESS gameplay, waits for players click to begin
	 * 		INPROGRESS:
	 * 			- checks if timer set up, and begins counting
	 *			- assess air level of Terrapin, breathes if above water, changes gameState to finished if air
	 *				falls below 0
	 *			- moves terrapin based on ME input
	 *			- checks Terry collision with all of the items, removing, adding more items and changing score as needed
	 *
	 * @param MouseEvent me clicks through tutorial steps, changes the direction of the terrapin 
	 */
	@Override
	public void update(MouseEvent me) {
		switch(gs) {
			case INPROGRESS:
				if (! timerSet) {
					setUpTimer();
					timerSet = true;
				}
		
				if (terry.getY() <= WATERTRESHOLD) {
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
						getItems().remove(0);
						getItems().add(new Food(backgroundWidth, halfBackgroundHeight, getCurrentItemSpeed()));
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
						getItems().add(new Trash(backgroundWidth, halfBackgroundHeight, getCurrentItemSpeed()));
						movers.addAll(getItems());
						set = true;
					} else {
						for (Mover m : getItems()) {
							if (isCollision(terry, m)) {
								movers.removeAll(getItems());
								getItems().clear();
								Trash t = new Trash(backgroundWidth, backgroundHeight/2, getCurrentItemSpeed());
								movers.add(t);
								getItems().add(t);
								break;
							} else if (m.getX() < 0) {
								movers.removeAll(getItems());
								getItems().clear();
								tutorialPlay = false;
								gs = GameState.SC_TUTORIAL_SEAWEED;
								set = false;
								break;
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
						getItems().add(new Seaweed(backgroundWidth, backgroundHeight - SEAWEEDY, getCurrentItemSpeed()));
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
					if (terry.getY() < WATERTRESHOLD) {
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

	/**
	 * Randomly adds a new SCMover to the screen, starting at the end of the screen
	 * 
	 */
	public void addNewItem() {
		int newMover = r.nextInt(10);
		if (newMover < randSeaweedThreshold)  {
			Seaweed s = new Seaweed(backgroundWidth, backgroundHeight - SEAWEEDY, getCurrentItemSpeed());
			getItems().add(s);
		} else if (newMover < randFoodThreshold) {
			int y =  new Random().nextInt((int) (backgroundHeight - WATERTRESHOLD));
			Food f = new Food(backgroundWidth, backgroundHeight - y, getCurrentItemSpeed());
			getItems().add(f);
		} else {
			int y = new Random().nextInt((int) (backgroundHeight - WATERTRESHOLD));
			Trash t = new Trash(backgroundWidth, backgroundHeight - y, getCurrentItemSpeed());
			getItems().add(t);
		}
		
		
	}

	/**
	 * Changes speed of all the SCMovers in items to match the currentItemSpeed, so that
	 * all of the items are moving at a consistent rate  
	 */
	public void changeSpeeds() {
		Iterator<SCMover> itemIt = getItems().iterator();
		while (itemIt.hasNext()) {
			SCMover m = (SCMover) itemIt.next();
			m.changeSpeed(getCurrentItemSpeed());
		}
	}
	
	/**
	 * Changes the currentItemSpeed based on collision with different types of SCMovers.
	 * Collisions with food increase the absolute value of the speed (more-negative), while 
	 * collisions with trash and seaweed decrease the absolute value of the speed
	 *  
	 * @param m SCMover that is the collider causing the speed change
	 */
	public void changeCurrentSpeed(SCMover m) {
		setCurrentItemSpeed(getCurrentItemSpeed() + m.getCollisionSpeedChange());

	}


	public ArrayList<SCMover> getItems() {
		return items;
	}


	public int getCurrentItemSpeed() {
		return currentItemSpeed;
	}


	public void setCurrentItemSpeed(int currentItemSpeed) {
		this.currentItemSpeed = currentItemSpeed;
	}
	
	public Terrapin getTerry() {
		return terry;
	}
}
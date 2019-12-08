package pkgMGModel;

import javafx.scene.input.MouseEvent;
import pkgEnum.Game;
import pkgEnum.GameState;
import pkgMover.Mover;

public class HSCModel extends MinigameModel {
	private static final long serialVersionUID = 13L;
	private int points = 50;
	private boolean timerSet = false;
	private final int MAX_HSC = 10;
	private final int MAX_SPEED = 10;

	/**
	 * Sets Game <code>g</code> to HSCCOUNT, the GameState to TUTORIAL, calls
	 * <code>createHSCrabs()</code>, and initializes <code>time</code> to 30 seconds
	 */
	public HSCModel() { 
		g = Game.HSCCOUNT;
		gs = GameState.TUTORIAL;
		createHSCrabs();
		time = 300;
	}

	/**
	 * Adds <code>maxHSC</code> horshoecrabs to <code>movers</code>
	 * <code>movers</code> located in the abstract class <code>MinigameModel</code>
	 * with random x,y starting location and random xIncr,yIncr within constricted
	 * ranges
	 * 
	 * @author Ryan Peters
	 */
	private void createHSCrabs() {
		for (int i = 0; i < MAX_HSC; i++) {
			movers.add(new HSC(r.nextInt(backgroundWidth), 
					r.nextInt(backgroundHeight),
					(r.nextInt() % MAX_SPEED) + 1,
					(r.nextInt() % MAX_SPEED) + 1));
		}
	}

	/**
	 * Checks if a mover has gone out of view(isOffScreen) and if so the mover is
	 * given a random x and y coordinate, entering the screen from the opposite
	 * side. If the mover was tagged upon exiting the view, the tagged attribute is
	 * reset so it can be tagged again
	 * 
	 * @author jbrueno
	 * @param m the mover to be checked
	 */
	private void spawnHSCrabs(Mover m) {
		int iWidth = m.getImageWidth();
		int iHeight = m.getImageHeight() + 90;
		int buffer = r.nextInt(10);

		if (isOffScreen(m)) {
			((HSC) m).unTag();
			if (m.getX() < -iWidth) {
				m.setX(backgroundWidth + buffer);
				m.setY(r.nextInt(backgroundHeight));
			} else if (m.getX() > backgroundWidth) {
				m.setX(buffer - (iWidth + buffer));
				m.setY(r.nextInt(backgroundHeight));
			} else if (m.getY() < -iHeight) { 
				m.setX(r.nextInt(backgroundWidth));
				m.setY(backgroundHeight + buffer);
			} else if (m.getY() > backgroundHeight) {
				m.setX(r.nextInt(backgroundWidth));
				m.setY(buffer - iHeight);
			}
		}
	}

	/**
	 * Checks a mouse click/press generated during <code>TUTORIAL</code> to see if the HSC image
	 * in the tutorial was clicked
	 * 
	 * @param me the MouseEvent generated during the GameState TUTORIAL
	 * @return true if the HSC image was clicked, false otherwise
	 */
	private boolean tutorialHSCrabClicked(MouseEvent me) {
		if (me.getEventType() == MouseEvent.MOUSE_CLICKED || me.getEventType() == MouseEvent.MOUSE_PRESSED) {
			if (me.getX() <= (backgroundWidth / 2 + HSC.hscWidth) && me.getX() > backgroundWidth / 2
					&& me.getY() >= backgroundHeight / 2 && me.getY() <= (backgroundHeight / 2 + HSC.hscHeight)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Based on the current GameState, update the game accordingly:
	 * GameState.TUTORIAL: once the tutorial HSC has been clicked, change GameState to TUTORIAL2
	 * GameState.TUTORIAL2: once the mouse is clicked, change the GameState to INPROGRESS
	 * GameState.INPROGRESS: First sets up the timer and sets <code>timerSet</code> to true, and checks each mover with 
	 * 						 </code>spawnHSCrabs(m)</code>.  Then checks the MouseEvent to detect if a HSC has been clicked
	 * 						 and if so tags the HSC and increments </code>score</code>.  Finally the </ode>move()</code> 
	 * 						 is called on the movers to update the position of the HSCrabs
	 */
	@Override
	public void update(MouseEvent me) {

		switch (gs) {
		case TUTORIAL:
			if (tutorialHSCrabClicked(me)) {
				gs = GameState.TUTORIAL2;
			}
			break;
		case TUTORIAL2:
			if (me.getEventType() == MouseEvent.MOUSE_CLICKED || me.getEventType() == MouseEvent.MOUSE_PRESSED) {
				gs = GameState.INPROGRESS;
			}
			break;
		case INPROGRESS:
			if (!timerSet) {
				setUpTimer();
				timerSet = true;
			}
			for (Mover m : movers) {
				spawnHSCrabs(m);
				if (me.getEventType() == MouseEvent.MOUSE_CLICKED || me.getEventType() == MouseEvent.MOUSE_PRESSED) {
					if (isCollision(m, me) && !((HSC) m).getTagged()) {
						((HSC) m).tag();
						score += points;
					}
				}
				m.move();
			}
			break;
		}
	}

	/**
	 * Returns the boolean value of <code>timerSet</code>
	 * @return timerSet
	 */
	public boolean getTimerSet() {
		return timerSet;
	}
	
	/**
	 * Set the boolean <code>timerSet</code> to the given value
	 * @param timerSet boolean value to set
	 */
	public void setTimerSet(boolean timerSet) {
		this.timerSet = timerSet;
	}
	
	public class HSC extends Mover {
		private static final long serialVersionUID = 22L;
		boolean tagged;
		static final int hscWidth = 200;
		static final int hscHeight = 136;
		final int DEFAULT_SPEED_INCREASE = 2;

		/**
		 * Creates a HSC using the Mover superclass constructor and sets <code>tagged</code> to false
		 * 
		 * @param x x-coordinate of HSC
		 * @param y y-coordinate of HSC
		 * @param xIncr speed for x movement
		 * @param yIncr speed for y movement
		 */
		public HSC(int x, int y, int xIncr, int yIncr) {
			super(x, y, 200, 136, xIncr, yIncr, "HSC");
			tagged = false;
		}

		/**
		 * Returns the boolean </code>tagged</code> attribute of the HSC
		 * @return the boolean <code>tagged</code>
		 */
		public boolean getTagged() {
			return tagged;
		}

		/**
		 * Sets the boolean <code>tagged</code> to true and the value of the HSC to "HSCTagged"
		 */
		public void tag() {
			this.tagged = true;
			super.setValue("HSCTagged");
			increaseSpeed();
		}

		/**
		 * Sets the boolean tagged to false and the value of the HSC to "HSC"
		 */
		public void unTag() {
			this.tagged = false;
			super.setValue("HSC");
		}

		/**
		 *  First checks the xIncr of the HSC mover to check if it is positive or negative
		 *  and increases/decreases its xIncr speed DEFAULT_SPEED_INCREASEaccordingly.  
		 *  Then checks the yIncr for a positive/negative value and increases/decreases that 
		 *  value by DEFAULT_SPEED_INCREASE as well.
		 */
		private void increaseSpeed() {
			if (super.getXIncr() >= 0) {
				super.setXIncr(
						super.getXIncr() + r.nextInt((int) (1 + super.getXIncr() / 2.0)) + DEFAULT_SPEED_INCREASE);
			} else {
				super.setXIncr(super.getXIncr() - r.nextInt((int) Math.abs(-1 + super.getXIncr() / 2.0))
						- DEFAULT_SPEED_INCREASE);
			}

			if (super.getYIncr() >= 0) {
				super.setYIncr(
						super.getYIncr() + r.nextInt((int) (1 + super.getYIncr() / 2.0)) + DEFAULT_SPEED_INCREASE);
			} else {
				super.setYIncr(super.getYIncr() - r.nextInt((int) Math.abs(-1 + super.getYIncr() / 2.0))
						- DEFAULT_SPEED_INCREASE);
			}
		}
	}

}

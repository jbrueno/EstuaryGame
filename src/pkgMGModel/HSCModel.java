package pkgMGModel;

import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.input.MouseEvent;
import pkgEnum.Game;
import pkgEnum.GameState;
import pkgMover.DataNode;
import pkgMover.Mover;

public class HSCModel extends MinigameModel {
	Mover Crosshairs;
	int numTagged;
	int tagWidth = 155;
	int tagHeight = 85;
	int tagDepth = 195;
	int points = 50;
	boolean timerSet = false;
	final int maxHSC = 10;
	int maxSpeed = 10;

	public HSCModel() {
		g = Game.HSCCOUNT;
		gs = GameState.INPROGRESS;
		createHSCrabs();
		time = 300;
	}

	/**
	 * Adds 20 horshoecrabs (10 male/10 female) to the DataNode list
	 * <code>dns</code> located in the abstract class <code>MinigameModel</code>
	 * with random x,y starting location and random xIncr,yIncr within constricted
	 * ranges
	 * 
	 * @author Ryan Peters
	 * 
	 */
	private void createHSCrabs() {
		for (int i = 0; i < maxHSC; i++) {
			movers.add(new HSC(r.nextInt(backgroundWidth), r.nextInt(backgroundHeight), (r.nextInt() % maxSpeed) + 1,
					(r.nextInt() % maxSpeed) + 1));
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
			m.setValue("HSC");
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
	 * For each horseshoe crab, move() based on xIncr,yIncr
	 * 
	 * @author Ryan Peters @
	 */

	// TODO streamline collision function
	@Override
	public void update(MouseEvent me) {
		if(!timerSet) {
			setUpTimer();
			timerSet = true;
		}
		
		for (Mover m : movers) {
			spawnHSCrabs(m);
			if (me.getEventType() == MouseEvent.MOUSE_CLICKED && isCollision(m, me) && !((HSC) m).getTagged()) {
				((HSC) m).tag();
				score += points;
				numTagged++;
			}
			m.move();
		}

	}

	

	// TODO combine HSCs, add boolean value tagged
	public class HSC extends Mover {
		boolean tagged;
		static final int hscWidth = 200;
		static final int hscHeight = 136;

		public HSC(int x, int y, int xIncr, int yIncr) {
			super(x, y, 200, 136, xIncr, yIncr, "HSC");
			tagged = false;
		}

		public boolean getTagged() {
			return tagged;
		}

		public void tag() {
			this.tagged = true;
			super.setValue("HSCTagged");
		}
	}

	public class Crosshairs extends Mover {

		public Crosshairs(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
			super(x, y, imageWidth, imageHeight, xIncr, yIncr, value);
		}
	}

}


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
		for (int i = 0; i < 20; i++) {
			movers.add(new HSC(r.nextInt(backgroundWidth), r.nextInt(backgroundHeight), (r.nextInt() % 7) + 1,
					(r.nextInt() % 7) + 1));
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
			((HSC) m).setTagged(false);
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
	 * Checks to see if the me was a click and if it is within the bounds of a HSC.
	 * Is it is, then it sets tagged and counted to true. Also, if the HSC is tagged
	 * and needs to be counted, increments numTagged.
	 * 
	 * @param me MouseEvent
	 * @param m  mover being checked
	 */
	private void checkTagged(MouseEvent me, Mover m) {
		double mouseEventX = me.getX();
		double mouseEventY = me.getY();
		double moverX = m.getX();
		double moverY = m.getY();

		if ((mouseEventX <= moverX + tagWidth && mouseEventX >= moverX)
				&& (mouseEventY <= (moverY + tagDepth) && mouseEventY >= moverY + tagHeight)
				&& me.getEventType() == MouseEvent.MOUSE_CLICKED && !((HSC) m).getTagged()) {

			m.setValue("HSCTagged");
			((HSC) m).setTagged(true);
			numTagged++;
			score += points;
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
			checkTagged(me, m);
			m.move();
		}

	}

	/*private void setUpTimer() {

		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				--time;
				System.out.println("time remaining: " + time);
				if(time == 0) {
					timer.cancel();
					System.out.println("times up");
					gs = gs.FINISHED;
				}

			}

		}, 100, 100);

	}*/

	// TODO combine HSCs, add boolean value tagged
	public class HSC extends Mover {
		boolean tagged;
		static final int hscWidth = 200;
		static final int hscHeight = 136;

		public HSC(int x, int y, double xIncr, double yIncr) {
			super(x, y, hscWidth, hscHeight, xIncr, yIncr, "HSC");
			tagged = false;
		}

		public boolean getTagged() {
			return tagged;
		}

		public void setTagged(boolean tagged) {
			this.tagged = tagged;
		}
	}

	public class Crosshairs extends Mover {

		public Crosshairs(int x, double y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
			super(x, y, imageWidth, imageHeight, xIncr, yIncr, value);
		}
	}

}

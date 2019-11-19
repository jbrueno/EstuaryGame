package pkgMGModel;

import javafx.scene.input.MouseEvent;
import pkgEnum.Game;
import pkgMover.DataNode;
//import pkgMover.FemaleHSC;
//import pkgMover.MaleHSC;
import pkgMover.Mover;

public class HSCModel extends MinigameModel {
	Mover Crosshairs;
	int numTagged;

	public HSCModel() {
		g = Game.HSCCOUNT;
		createHSCrabs();
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
	 * side. If the mover was tagged upon exiting the view, the tagged and counted
	 * attributes are reset so it can be counted again
	 * 
	 * @author jbrueno
	 * @param m the mover to be checked
	 */
	private void spawnHSCrabs(Mover m) {
		int iWidth = m.getImageWidth();
		int iHeight = m.getImageHeight();

		if (isOffScreen(m)) {
			((HSC) m).setTagged(false);
			((HSC) m).setCounted(false);
			m.setValue("HSC");
			if (m.getX() < -iWidth) {
				m.setX(backgroundWidth + r.nextInt(10));
				m.setY(r.nextInt(backgroundHeight));
			} else if (m.getX() > backgroundWidth) {
				m.setX(r.nextInt(10) - (iWidth + 15));
				m.setY(r.nextInt(backgroundHeight));
			} else if (m.getY() < -iHeight - 90) {
				m.setX(r.nextInt(backgroundWidth));
				m.setY(backgroundHeight + r.nextInt(10));
			} else if (m.getY() > backgroundHeight) {
				m.setX(r.nextInt(backgroundWidth));
				m.setY(r.nextInt(10) - (iHeight + 90));
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
	private void checkClick(MouseEvent me, Mover m) {
		if ((me.getX() <= (m.getX() + 165) && me.getX() >= m.getX())
				&& (me.getY() <= (m.getY() + 200) && me.getY() >= m.getY() + 90)
				&& me.getEventType() == MouseEvent.MOUSE_CLICKED && !((HSC) m).getTagged()) {
			m.setValue("HSCTagged");
			((HSC) m).setTagged(true);
			((HSC) m).setCounted(true);
		}

		if (((HSC) m).getTagged() && ((HSC) m).getCounted()) { // counts the tagged crabs
			numTagged++;
			((HSC) m).setCounted(false); // reset counted so crab does not count more than once
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
		for (Mover m : movers) {
			checkClick(me, m);
			m.move();
			spawnHSCrabs(m);
		}

	}

	// TODO combine HSCs, add boolean value tagged
	public class HSC extends Mover {
		boolean tagged;
		boolean counted;

		public HSC(int x, int y, int xIncr, int yIncr) {
			super(x, y, 200, 136, xIncr, yIncr, "HSC");
			tagged = false;
		}

		public boolean getTagged() {
			return tagged;
		}

		public void setTagged(boolean tagged) {
			this.tagged = tagged;
		}

		public boolean getCounted() {
			return counted;
		}

		public void setCounted(boolean counted) {
			this.counted = counted;
		}
	}

	public class Crosshairs extends Mover {

		public Crosshairs(int x, double y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
			super(x, y, imageWidth, imageHeight, xIncr, yIncr, value);
		}
	}

}

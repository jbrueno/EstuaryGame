package pkgMGModel;

import java.util.ArrayList;
import java.util.Random;

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
			movers.add(
					new HSC(r.nextInt(backgroundWidth), r.nextInt(backgroundHeight), r.nextInt() % 5, r.nextInt() % 5));
		}
	}

	/**
	 * Checks if a mover has gone out of view and if so the mover is given a random
	 * x and y coordinate, entering the screen from the opposite side. If the mover
	 * was tagged upon exiting the view, the tagged and counted attributes are reset
	 * so it can be counted again
	 * 
	 * @author jbrueno
	 * @param m the mover to be checked
	 */
	private void spawnHSCrabs(Mover m) {
		if (m.getX() < -250) {
			m.setValue("HSC");
			((HSC) m).setTagged(false);
			((HSC) m).setCounted(false);
			m.setX(randomDouble(backgroundWidth, backgroundWidth + 50));
			m.setY(randomDouble(0, backgroundHeight));
		} else if (m.getX() > backgroundWidth + 20) {
			m.setValue("HSC");
			((HSC) m).setTagged(false);
			((HSC) m).setCounted(false);
			m.setX(randomDouble(-250, -200));
			m.setY(randomDouble(0, backgroundHeight));
		} else if (m.getY() < -200) {
			m.setValue("HSC");
			((HSC) m).setTagged(false);
			((HSC) m).setCounted(false);
			m.setX(randomDouble(0, backgroundWidth));
			m.setY(randomDouble(backgroundHeight, backgroundHeight + 50));
		} else if (m.getY() > backgroundHeight) {
			m.setValue("HSC");
			((HSC) m).setTagged(false);
			((HSC) m).setCounted(false);
			m.setX(randomDouble(0, backgroundWidth));
			m.setY(randomDouble(-250, -200));
		}
	}

	/**
	 * @author jbrueno
	 * @param min lower bound for random double
	 * @param max upper bound for random double
	 * @return a random double within the given min and max
	 */
	private double randomDouble(double min, double max) {
		Random rxy = new Random();
		return min + (max - min) * rxy.nextDouble();
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
			m.move();
			spawnHSCrabs(m);
		}

		for (Mover m : movers) {
			if ((me.getX() <= (m.getX() + 165) && me.getX() >= m.getX())
					&& (me.getY() <= (m.getY() + 200) && me.getY() >= m.getY() + 90)
					&& me.getEventType() == MouseEvent.MOUSE_CLICKED && !((HSC) m).getTagged()) {
				m.setValue("HSCTagged");
				((HSC) m).setTagged(true);
				((HSC) m).setCounted(true);
			}

			if (((HSC) m).getTagged() && ((HSC) m).getCounted()) { // counts the tagged crabs
				numTagged++;
				((HSC) m).setCounted(false);
			}
		}

		score = numTagged; // score = numTagged for now to show tagged crab count in console
		System.out.println("HSC's tagged(score): " + score);
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

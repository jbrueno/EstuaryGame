package pkgMGModel;

import javafx.scene.input.MouseEvent;
import pkgEnum.Game;
import pkgEnum.GameState;
import pkgMover.DataNode;
//import pkgMover.FemaleHSC;
//import pkgMover.MaleHSC;
import pkgMover.Mover;

public class HSCModel extends MinigameModel{
	Mover Crosshairs;
	
	public HSCModel() {
		g = Game.HSCCOUNT;
		gs = GameState.INPROGRESS;
		createHSCrabs();
	}
	
	/**
	 * Adds 20 horshoecrabs (10 male/10 female) to the DataNode list <code>dns</code> located in the abstract
	 * class <code>MinigameModel</code> with random x,y starting location and random xIncr,yIncr within constricted ranges
	 * 
	 * @author Ryan Peters
	 * 
	 */
	private void createHSCrabs() {
		
		for (int i = 0; i < 20; i++) {
			movers.add(new HSC(r.nextInt(backgroundWidth), r.nextInt(backgroundHeight),
					r.nextInt() % 5, r.nextInt() % 5));
		}
	}

	
	/**
	 * For each horseshoe crab, move() based on xIncr,yIncr
	 * 
	 * @author Ryan Peters
	 * @
	 */
	
	//TODO streamline collision function
	@Override
	public void update(MouseEvent me) {
		for (Mover m: movers) {
			m.move();
		}
		
		for(Mover m: movers) {
			if ((me.getX() <= m.getX()+50  && me.getX() >= m.getX()-50) &&
				(me.getY() <= m.getY()+50 && me.getY() >= m.getY()-50) &&
				me.getEventType()==me.MOUSE_CLICKED){
					m.setValue("HSCTagged");
					score += 1; // temporary score system, testing label updates
					System.out.println("tagged! " + score);
			}
		}
	}
	
	
	
	//TODO combine HSCs, add boolean value tagged
	public class HSC extends Mover{
		boolean tagged;
		
		public HSC(int x, int y, int xIncr, int yIncr) {
			super(x, y, 200, 136, xIncr, yIncr, "HSC");
			tagged=false;
		}
		
		public boolean getTagged() {
			return tagged;
		}
	}
	
	public class Crosshairs extends Mover{

		public Crosshairs(int x, double y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
			super(x, y, imageWidth, imageHeight, xIncr, yIncr, value);
		}
	}

}
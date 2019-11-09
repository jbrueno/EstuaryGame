package pkgMG;

<<<<<<< HEAD
import javafx.scene.input.MouseEvent;
import pkgEnum.Game;
import pkgMover.DataNode;
//import pkgMover.FemaleHSC;
//import pkgMover.MaleHSC;
import pkgMover.Mover;

public class HSCModel extends MinigameModel{
	
	public HSCModel() {
		g = Game.HSCCOUNT;
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
		for (int i = 0; i < 10; i++) {
			movers.add(new MaleHSC(r.nextInt(backgroundWidth), r.nextInt(backgroundHeight),
					r.nextInt() % 5, r.nextInt() % 5));
		}
		
		for (int i = 0; i < 10; i++) {
			movers.add(new FemaleHSC(r.nextInt(backgroundWidth), r.nextInt(backgroundHeight),
					r.nextInt() % 5, r.nextInt() % 5));
		}
	}

	
	/**
	 * For each horseshoe crab, move() based on xIncr,yIncr
	 * 
	 * @author Ryan Peters
	 * @
	 */
	@Override
	public void update(MouseEvent me) {
		for (Mover m: movers) {
			m.move();
		}
		System.out.println(movers); 
		//later handle click from mouseevent; maybe have crabs scatter from the light slowly
	}
	
<<<<<<< HEAD
=======
import java.util.ArrayList;
>>>>>>> branch 'master' of https://github.com/CISC275-Fall2019/cisc275f19-project-cisc275f19-team-11-0
=======
	//TODO combine HSCs, add boolean value tagged
	public class FemaleHSC extends Mover{
		public FemaleHSC(int x, int y, int xIncr, int yIncr) {
			super(x, y, 200, 136, xIncr, yIncr, "FemaleHSC");
		}
	}
	
	
	public class MaleHSC extends Mover{
		public MaleHSC(int x, int y, int xIncr, int yIncr) {
			super(x, y, 200, 136, xIncr, yIncr, "MaleHSC");
		}
	}
	
>>>>>>> branch 'master' of https://github.com/CISC275-Fall2019/cisc275f19-project-cisc275f19-team-11-0

import javafx.scene.input.MouseEvent;
import pkgEnum.Direction;
import pkgEnum.GameType;
import pkgMover.Mover;

public class HSCModel extends MinigameModel{
	private final GameType gameType = GameType.HCCOUNT;
	ArrayList<Mover> crabs;

	public HSCModel() {
		System.out.println("HSCmodel constructor");
		this.createCrabs();
	}
	
	public void update() {
		for (Mover c : crabs) {
			c.move();
			}	
		System.out.println(crabs);
	}
	
	@Override
	void update(MouseEvent me) {
		// TODO Auto-generated method stub
	}

	@Override
	void handleBorderCollision(Mover m) {
		// TODO Auto-generated method stub
	}
	
	void createCrabs() {
		System.out.println("call create");
		
		crabs= new ArrayList<Mover>();
		
		System.out.println("made crablist");
		
		HSC c1= new HSC("female", 50, 100, Direction.EAST);
		HSC c2= new HSC("male", 0, 0, Direction.SOUTHEAST);
		HSC c3= new HSC("female", 50, 200, Direction.SOUTH);
		
		System.out.println("made crabs");
		
		crabs.add(c1);
		crabs.add(c2);
		crabs.add(c3);
		
		System.out.println("added crabs");
		System.out.println(crabs);
	}
	
	public class HSC extends Mover{
		public String sex;
		
		public HSC(String sex, int x, int y, Direction d) {
			super("crab");
			this.sex=sex;
			this.x = x;
			this.y = y;
			this.d = d;
			xIncr=10;
			yIncr=10;
		}
		
		public String toString() {
			return species + " " + sex + " x: " + x + ", y: " + y;
			
		}
	}
}



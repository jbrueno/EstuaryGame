package pkgMG;

import java.util.ArrayList;

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
			this.sex=sex;
			species = "crab";
			this.x = x;
			this.y = y;
			this.d = d;
			xIncr=10;
			yIncr=10;
		}
		
		public String toString() {
			return species + " " + sex + " " + x + " " + y;
			
		}
	}
}

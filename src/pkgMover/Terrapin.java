package pkgMover;

public class Terrapin extends Mover {
	
	public double airAmount;
	
	public Terrapin(int x, int y, int xIncr, int yIncr) {
		super(x, y, 200, 200, xIncr, yIncr,"Terrapin");
		this.airAmount = 100;
	}
	
	/**
	 * breathe() resets the air that is held by the Terrapin to full. Is
	 * called whenever the Terrapin is above a certain height
	 * 
	 *  @author HM
	 */
	public void breathe() {
		airAmount = 100;
		System.out.println("breath taken");
	}
}
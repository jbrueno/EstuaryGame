package pkgMover;

public class Terrapin extends Mover {
	
	public double airAmount;
	
	public Terrapin() {
		super("Terrapin");
		this.airAmount = 100;
		this.xIncr = 0;
		this.yIncr = 10;
	}
	
	/**
	 * breathe resets the air that is held by the Terrapin to full. Is
	 * called whenever the Terrapin is above a certain height
	 * 
	 *  @author HM
	 */
	public void breathe() {
		this.airAmount = 100;
		System.out.println("breath taken");
	}

}

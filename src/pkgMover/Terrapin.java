<<<<<<< HEAD
package pkgMover;

public class Terrapin {

}
=======
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
>>>>>>> branch 'master' of https://github.com/CISC275-Fall2019/cisc275f19-project-cisc275f19-team-11-0

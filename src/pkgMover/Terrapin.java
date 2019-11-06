package pkgMover;

public class Terrapin {
	
	double airAmount;
	private String species;
	
	public Terrapin() {
		this.species = "Terrapin";
		this.airAmount = 100;
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

package pkgMover;

public class Terrapin extends Mover {
	
	double airAmount;
	int maxDist = 100;
	int maxSpeed = 1;
	

	public Terrapin(int x, int y, int xIncr, int yIncr) {
		super(x, y, 200, 200, xIncr, yIncr,"Terrapin");
		setImageWidthAndHeight(150, 150);
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
	
	public void holdBreath() {
		airAmount = airAmount - 0.5;
		System.out.println("Terrapin breath level: " + airAmount);
	}
	
	/**
	 * change xIncr
	 */
	public void changeXIncr(int xChange) {
		setXIncr(this.getxIncr() + xChange);
	}
	
	public void move(int targetX, int targetY) {
		double xDist = targetX - getX();
		if (xDist > maxDist) {
			xDist = maxDist;
		} else if (getX() < (-1 * maxDist)){
			xDist = (-1 * maxDist);
		}
		
		
		setXIncr((((xDist + maxDist) / (maxDist * 2)) * (maxSpeed - (-1* maxSpeed)) + (-1 * maxSpeed)));
		
		double yDist = targetY - getY();
		if (yDist > maxDist) {
			yDist = maxDist;
		} else if (yDist < (-1 * maxDist)){
			yDist = (-1 * maxDist);
		}
		
		
		setYIncr((((yDist + maxDist) / (maxDist * 2)) * (maxSpeed - (-1* maxSpeed)) + (-1 * maxSpeed)));
		
	}
	
	public double getAirAmount() {
		return airAmount;
	}
	
}
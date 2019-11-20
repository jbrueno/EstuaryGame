package pkgMover;

public class Terrapin extends Mover {
	
	double airAmount;
	int maxDist = 100;
	int maxSpeed = 1;
	

	public Terrapin(int x, int y, int xIncr, int yIncr) {
		super(x, y, 200, 200, xIncr, yIncr,"Terrapin");
		setImageWidthAndHeight(150, 150);
		this.airAmount = 100;
		this.setXIncr(0);
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
	
	@Override
	public void move(double targetX, double targetY) {
		if (getX() > 25) {
			setXIncr(0);
		} else if (targetX < getX() && getX() > 0) {
			setXIncr(-1 * getxIncr());
		} else if (targetX > getX() && getX() < 0) {
			setXIncr(-1 * getxIncr());
		}
		
		if (targetY < getY() - 10) {
			if (getYIncr() > 0) {
				setYIncr(-1 * getYIncr());
			}
		} else if (targetY > getY() - 10) {
			if (getYIncr() < 0) {
				setYIncr(-1 * getYIncr());
			}
		}
		
		setY(getY() + getYIncr());
		setX(getX() + getxIncr());
	}

	
	public double getAirAmount() {
		return airAmount;
	}
	
}
package pkgMover;

public class Terrapin extends Mover {
	private static final long serialVersionUID = 29L;
		double airAmount;

		public Terrapin(int x, int y, int xIncr, int yIncr) {
			super(x, y, 120, 60, xIncr, yIncr,"Terrapin");
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
		
		/**
		 * called whenever the terrapin is under water, on each tick it reduces the air level of the terrapin
		 *  
		 * @author HM
		 */
		public void holdBreath() {
			airAmount = airAmount - 0.5;
			System.out.println("Terrapin breath level: " + airAmount);
		}
		

		/**
		 * Overriden terrapin move function that takes in the x and y coordinates of the mouse pointer.
		 * The x is not considered because the terrapin's xIncr is always 0.
		 * 
		 * 
		 * @author HM
		 */
		@Override
		public void move(double targetX, double targetY) {
			System.out.println(getY());
			System.out.println(targetY > getY());
			System.out.println(getYIncr() < 0);
			if (targetY < getY() && getYIncr() > 0) {
				setYIncr(-1 * getYIncr());
			} else if (targetY > getY() && getYIncr() < 0) {
				setYIncr(-1 * getYIncr());
			}
			
			System.out.println("X INCREASE: " + getXIncr());
			
			setY(getY() + getYIncr());
			setXIncr(0);
			setX(0);
		}

		/**
		 * returns the Terrapin's current air level
		 * 
		 * @return double that represents the current air level
		 */
		public double getAirAmount() {
			return airAmount;
		}
		
		/**
		 * sets the air amount of the terrapin
		 * 
		 * @param air desired air level
		 */
		public void setAirAmount(int air) {
			airAmount = air;
		}

		
	}
package pkgMover;

public class Terrapin extends Mover {
		private static final long serialVersionUID = 29L;
		double airAmount;
		private double lungCapacity = 100;

		public Terrapin(int x, int y, int xIncr, int yIncr) {
			super(x, y, 120, 60, xIncr, yIncr,"Terrapin");
			this.setXIncr(0);
			airAmount = lungCapacity;
			
		}
		
		/**
		 * breathe() resets the air that is held by the Terrapin to full. Is
		 * called whenever the Terrapin is above a certain height
		 * 
		 *  @author HM
		 */
		public void breathe() {
			airAmount = lungCapacity;
		}
		
		/**
		 * called whenever the terrapin is under water, on each tick it reduces the air level of the terrapin
		 *  
		 * @author HM
		 */
		public void holdBreath() {
			airAmount = airAmount - 0.5;
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
			if (targetY < getY() && getYIncr() > 0) {
				setYIncr(-1 * getYIncr());
			} else if (targetY > getY() && getYIncr() < 0) {
				setYIncr(-1 * getYIncr());
			}
			
			setY(getY() + getYIncr());
			setXIncr(0);
			setX(200);
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
package pkgMover;

import pkgEnum.Direction;

public abstract class Mover {
	private double y;
	private double x;
	private int imageWidth;
	private int imageHeight;
	private double xIncr;
	private double yIncr;
	private Direction d;
	private String species;
	
	
	public double getY() {
		return y;
	}

	public double getX() {
		return x;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public double getxIncr() {
		return xIncr;
	}

	public double getyIncr() {
		return yIncr;
	}
	
	public Direction getD() {
		return d;
	}
	
	public String getSpecies() {
		return this.species;
	}
	
	//
	public String toString() {
		return species + " " + "x: " + x + ", y: " + y;
	}
	
	
	
	/**
	 * Moves the mover towards the given x & y coordinates on the background.
	 * 
	 * (assuming this is called every update, and therefore only operates
	 * once, and if the location is different than the given coordinate)
	 * 
	 * 
	 * @author HM
	 * 
	 * @param new_x the double value of the desired x location
	 * @param new_y the double value of the desired y location
	 */
	public void moveTowards(double new_x, double new_y) {
		// checks if current location is to the left of the new location, minus what the next increase in movement would be
		if (x < new_x - xIncr) {
			// if the xIncr is currently moving to the left (negative), it makes it positive
			if (xIncr <= 0) {
				xIncr *= -1;
			}
			//add the new increase
			x += xIncr;
		// checks if the current location is to the right of the new location, 	
		} else if(x > new_x + xIncr) {
			//if the xIncr is currently going to move the mover to the right (positive), it makes
			// the xIncr negative
			if (xIncr >= 0) {
				xIncr *= -1;
			}
			//add the new increase
			x += xIncr;
		}
		// if the new_x is neither > x or < x, then the mover is already at the location 
	}
	
	

}

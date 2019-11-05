package pkgMover;

import pkgEnum.Direction;

public  class Mover {
	public double y;
	public double x;
	public int imageWidth;
	public int imageHeight;
	public double xIncr;
	public double yIncr;
	public Direction d;
	public String species;
	
	
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
	 * Moves to the x and y locations
	 * 
	 * @author HM
	 * 
	 * @param new_x the double value of the desired x location
	 * @param new_y the double value of the desired y location
	 */
	public void setXY(double newx, double newy) {
		this.x = newx;
		this.y = newy;
	}
	

}

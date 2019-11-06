package pkgMover;

import pkgEnum.Direction;

public class Mover {
	public double y;
	public double x;
	public int imageWidth;
	public int imageHeight;
	public double xIncr;
	public double yIncr;
	public Direction d;
	public String species;
	
	public Mover(String s) {
		this.species = s;
	}
	
	
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
	 * moves object in a straight line in the direction that it is facing
	 * 
	 * @author AG
	 * 
	 */
	public void move() {
		switch(d) {
			case NORTH: y-=yIncr;
					break;
			case NORTHEAST: x+=xIncr;	
					y-=yIncr;
					break;
					
			case EAST: x+=xIncr;
					break;
					
			case SOUTHEAST: x+=xIncr;
					y+=yIncr;
					break;
					
			case SOUTH: y+=yIncr;
					break;
					
			case SOUTHWEST: x-=xIncr;
					y+=yIncr;
					break;
					
			case WEST: x-=xIncr;
					break;
					
			case NORTHWEST: x-=xIncr;
					y-=yIncr;
					break;
			}
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

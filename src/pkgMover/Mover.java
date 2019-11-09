package pkgMover;

import pkgEnum.Direction;

<<<<<<< HEAD
<<<<<<< HEAD
public abstract class Mover extends DataNode{
=======
public abstract class Mover/* extends DataNode*/{
>>>>>>> branch 'master' of https://github.com/CISC275-Fall2019/cisc275f19-project-cisc275f19-team-11-0
	private double y;
	private double x;
	private int imageWidth;
	private int imageHeight;
	private double xIncr;
	private double yIncr;
	private String value;
	
	 
	/**
	 * Mover constructor that takes in an x,y for position; imageWidth/Height for collision detection;
	 * and xIncr,yIncr for speed control. <code>value</code> is passed to constructor for <code>DataNode</code>
	 * to be set as the <code>value</code> attribute.
	 * 
	 * @author Ryan Peters
	 * 
	 * @param x				x-coordinate
	 * @param y				y-coordinate
	 * @param imageWidth	corresponding image width; relative x-boundary to consider
	 * @param imageHeight	corresponding iamge height; relative y-boundary to consider
	 * @param xIncr			speed for x movement
	 * @param yIncr			speed for y movement
	 * @param value			String relating to the subclass's name for image lookup later in MinigameViews
	 */
	public Mover(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
		this.value=value;
		this.x = x;
		this.y = y;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		this.xIncr = xIncr;
		this.yIncr = yIncr;
=======
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
>>>>>>> branch 'master' of https://github.com/CISC275-Fall2019/cisc275f19-project-cisc275f19-team-11-0
	}
	
	
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
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
	
	public void setXIncr(double d) {
		this.xIncr = d;
	}

	public double getYIncr() {
		return yIncr;
	}
	
	public void setYIncr(double d) {
		this.yIncr = d;
	}
	
	
	/**
	 * Updates x,y by their relative speeds for background movers who always move (ie aren't user-controlled)
	 * 
	 * @author Ryan Peters
	 */
	public void move() {
		x += xIncr;
		y += yIncr;
	}
	
	//
	public String toString() {
		return species + " " + "x: " + x + ", y: " + y;
	}
	
	
	/**
<<<<<<< HEAD
	 * Sets x,y to the parameters.
	 * Used for having an object "stick" to the mouse.
=======
	 * moves object in a straight line in the direction that it is facing
>>>>>>> branch 'master' of https://github.com/CISC275-Fall2019/cisc275f19-project-cisc275f19-team-11-0
	 * 
<<<<<<< HEAD
	 * @author Ryan Peters
	 * @param x	x-coordinate for the Mover to go to
	 * @param y	y-coordinate for the Mover to go to
=======
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
>>>>>>> branch 'master' of https://github.com/CISC275-Fall2019/cisc275f19-project-cisc275f19-team-11-0
	 */
<<<<<<< HEAD
	public void move(int x, int y) {
		this.x = x;
		this.y = y;
=======
	public void setXY(double newx, double newy) {
		this.x = newx;
		this.y = newy;
>>>>>>> branch 'master' of https://github.com/CISC275-Fall2019/cisc275f19-project-cisc275f19-team-11-0
	}
	
<<<<<<< HEAD
<<<<<<< HEAD
=======
	public String getValue() {
		return value;
	}
	
>>>>>>> branch 'master' of https://github.com/CISC275-Fall2019/cisc275f19-project-cisc275f19-team-11-0
	public String toString() {
		return getValue() + ": " + x + " " + y; 
	}
	
	
	
	
=======
>>>>>>> branch 'master' of https://github.com/CISC275-Fall2019/cisc275f19-project-cisc275f19-team-11-0

}

package pkgMover;

import pkgEnum.Direction;

public abstract class Mover/* extends DataNode*/{
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
	
	/**
	 * Sets x,y to the parameters.
	 * Used for having an object "stick" to the mouse.
	 * 
	 * @author Ryan Peters
	 * @param x	x-coordinate for the Mover to go to
	 * @param y	y-coordinate for the Mover to go to
	 */
	public void move(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public String getValue() {
		return value;
	}
	
	public String toString() {
		return getValue() + ": " + x + " " + y; 
	}
	
	
	
	

}

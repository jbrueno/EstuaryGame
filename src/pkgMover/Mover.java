package pkgMover;

import java.util.Objects;

public abstract class Mover {
	private double y;
	private double x;
	private int imageWidth;
	private int imageHeight;
	private double xIncr;
	private double yIncr;
	private String value;
	protected int scoreChange;
	
	 
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
	public Mover(double x, double y, int imageWidth, int imageHeight, double xIncr, double yIncr, String value) {
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

	public double getXIncr() {
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
	
	public double getTranslatedX() {
		return x - imageWidth/2;
	}
	
	public double getTranslatedY() {
		return y - imageHeight/2;
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
	 * Allows mover to bounce between two points
	 * 
	 * @param startx start x coordinate
	 * @param starty start y coordinate
	 * @param endx end x coordinate
	 * @param endy end y coordinate
	 */
	public void move(double startx, double starty, double endx, double endy) {
		this.move();
		if( y>=endy && x>=endx|| y<=starty && y<=startx) {
			yIncr=yIncr*-1;
			xIncr=xIncr*-1;
		}
	
	}
	
	/**
	 * Sets x,y to the parameters.
	 * Used for having an object "stick" to the mouse.
	 * 
	 * @author Ryan Peters
	 * @param x	x-coordinate for the Mover to go to
	 * @param y	y-coordinate for the Mover to go to
	 */
	public void move(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	public String toString() {
		return getValue() + ": " + x + " " + y; 
	}
	
	@Override 
	public int hashCode() {
		return Objects.hash(value);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Mover) {
			Mover m = (Mover) o;
			return this.value.equals(m.getValue());
		}
		return false;
	}
}
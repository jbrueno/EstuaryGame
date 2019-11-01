package pkgMover;

import pkgEnum.Direction;

public abstract class Mover {
	private int y;
	private int x;
	private int imageWidth;
	private int imageHeight;
	private int xIncr;
	private int yIncr;
	private Direction d;
	
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getImageWidth() {
		return imageWidth;
	}
	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}
	public int getImageHeight() {
		return imageHeight;
	}
	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}
	public int getxIncr() {
		return xIncr;
	}
	public void setxIncr(int xIncr) {
		this.xIncr = xIncr;
	}
	public int getyIncr() {
		return yIncr;
	}
	public void setyIncr(int yIncr) {
		this.yIncr = yIncr;
	}
	public Direction getD() {
		return d;
	}
	public void setD(Direction d) {
		this.d = d;
	}
	
	

}

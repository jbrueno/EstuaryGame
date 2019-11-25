package pkgMover;

public class Food extends SCMover {

	public Food(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
		super(x, y, imageWidth, imageHeight, xIncr, yIncr, value);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * constructor to be used within the SC model. It only allows input for the canvas width, because 
	 * all other factors should be the same given the game design
	 * 
	 * @param canvasWidth
	 */
	public Food(int canvasWidth, int y, int speed) {
		super(canvasWidth, y, 50, 50, speed, 0, "Food");
		this.scoreChange = 100;
		this.collisionSpeedChange = 3;
		this.setImageWidthAndHeight(100, 100);
	}
	
	/**
	 * the value that the speed of the xIncr of the terrapin will change after collision with 
	 * this mover
	 * 
	 * @return in value of the speed change that will be added to the xIncr
	 */
	public int getCollisionSpeedChange() {
		return collisionSpeedChange;
	}

	/**
	 * Changes the current score based on the score change for the food object.
	 * Adds scoreChange to current score
	 * 
	 */
	@Override
	public int changeScore(int score) {
		return score + scoreChange;
	}
	
	public void changeSpeed(int speed) {
		setXIncr(speed);
	}


	
	

}
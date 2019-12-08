package pkgMover;

public class Food extends SCMover {
	private static final long serialVersionUID = 27L;
	public Food(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
		super(x, y, imageWidth, imageHeight, xIncr, yIncr, value);
		// TODO Auto-generated constructor stub
		this.collisionSpeedChange = -3;
		this.scoreChange = 100;
	}
	
	/**
	 * constructor to be used within the SC model. It only allows input for the canvas width, because 
	 * all other factors should be the same given the game design
	 * 
	 * @author HM
	 * @param canvasWidth
	 */
	public Food(int canvasWidth, int y, int speed) {
		super(canvasWidth, y, 50, 50, speed, 0, "Food");
		this.scoreChange = 100;
		this.collisionSpeedChange = -3;
	}

	/**
	 * Changes the current score based on the score change for the food object.
	 * Adds scoreChange to current score
	 * 
	 * @author HM
	 */
	@Override
	public int changeScore(int score) {
		return score + scoreChange;
	}

}



	
	


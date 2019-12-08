package pkgMover;

public class Food extends SCMover {
	
	private static final long serialVersionUID = 27L;
	private static final int FOODWIDTHHEIGHT = 50;
	private static final int YINCR = 0;
	private static final int SCORECHANGE = 100;
	private static final int COLLISIONSPEEDCHANGE = -3;
	
	public Food(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
		super(x, y, imageWidth, imageHeight, xIncr, yIncr, value);
		// TODO Auto-generated constructor stub
		this.collisionSpeedChange = COLLISIONSPEEDCHANGE;
		this.scoreChange = SCORECHANGE;
	}
	
	/**
	 * constructor to be used within the SC model. It only allows input for the canvas width, because 
	 * all other factors should be the same given the game design
	 * 
	 * @author HM
	 * @param canvasWidth
	 */
	public Food(int canvasWidth, int y, int speed) {
		super(canvasWidth, y, FOODWIDTHHEIGHT, FOODWIDTHHEIGHT, speed, YINCR, "Food");
		this.scoreChange = SCORECHANGE;
		this.collisionSpeedChange = COLLISIONSPEEDCHANGE;
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



	
	


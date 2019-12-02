package pkgMover;

public class Seaweed extends SCMover{
	
	
	public Seaweed(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
		super(x, y, imageWidth, imageHeight, xIncr, yIncr, value);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * constructor to be used within the SC model. It only allows input for the canvas width, because 
	 * all other factors should be the same given the game design
	 * 
	 * @param canvasWidth where the SCMover will start
	 * @param canvasHeight to determine where the seaweed will be placed
	 */
	public Seaweed(int canvasWidth, int canvasHeight, int speed, int imageHeight) {
		super(canvasWidth, canvasHeight, 100, imageHeight, speed, 0, "Seaweed");
	}
	
	@Override
	public int changeScore(int score) {
		return score;
	}

	
}
package pkgMover;

public class Seaweed extends SCMover{
	private static final long serialVersionUID = 28L;
	private static final int SEAWEEDWIDTHHEIGHT = 0;
	private static final int YINCR = 0;
	int scoreChange = 0;
	
	public Seaweed(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
		super(x, y, imageWidth, imageHeight, xIncr, yIncr, value);
		this.collisionSpeedChange = 5;
		// TODO Auto-generated constructor stub

	}
	
	/**
	 * constructor to be used within the SC model. It only allows input for the canvas width, because 
	 * all other factors should be the same given the game design
	 * 
	 * @param canvasWidth where the SCMover will start
	 * @param canvasHeight to determine where the seaweed will be placed
	 */
	public Seaweed(int canvasWidth, int canvasHeight, int speed) {
		super(canvasWidth, canvasHeight, SEAWEEDWIDTHHEIGHT, SEAWEEDWIDTHHEIGHT, speed, YINCR, "Seaweed");
		this.collisionSpeedChange = 5;
	}
	
	/**
	 * Seaweed just returns the current score, since collisions with seaweed have no effect on score
	 * 
	 */
	@Override
	public int changeScore(int score) {
		return score;
	}
}
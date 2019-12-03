package pkgMover;

public class Seaweed extends SCMover{
	
	int speedChange = 1;
	int scoreChange = 0;
	int imageHeight = 100;
	int imageWidth = 100;
	
	public Seaweed(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
		super(x, y, imageWidth, imageHeight, xIncr, yIncr, value);
		this.collisionSpeedChange = -5;
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
		super(canvasWidth, canvasHeight, 100, 100, speed, 0, "Seaweed");
		this.collisionSpeedChange = -5;
	}
	
	/**
	 * Changes the value of the speed of the mover it collides with 
	 * 
	 * @return int by which the speed will be changed upon collision
	 */
	public int getSCSpeedChange() {
		return speedChange;
	}
	
	/**
	 * Changes the score of the SC game after collision with this mover, which will be a 0 change in score
	 * 
	 * @return int by which the score will be changed upon collision with this mover
	 */
	public int getSCScoreChange() {
		return scoreChange;
	}

	@Override
	public int changeScore(int score) {
		return score;
	}

	public void changeSpeed(int speed) {
		setXIncr(speed);
	}
}
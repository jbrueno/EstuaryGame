package pkgMover;

public class Trash extends Mover{
	
	public int scoreChange = -100;
	public int speedChange = -1;
	public int imageWidth = 100;
	public int imageHeight = 100;
	
	public Trash(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
		super(x, y, imageWidth, imageHeight, xIncr, yIncr, value);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * constructor to be used within the SC model. It only allows input for the canvas width, because 
	 * all other factors should be the same given the game design
	 * 
	 * @param canvasWidth
	 */
	public Trash(int canvasWidth) {
		super(canvasWidth, canvasWidth / 3, 100, 100, -10, 0, "Trash");
	}
	

	
	/** the value by which the score in SC will change based on collision with this mover
	 * 
	 * @return int the value of the score change, it will be added to the score of the SCgame
	 */
	public int getSCScoreChange() {
		return scoreChange;
	}
	
	/**
	 * the value by which the speed of the terrapin will change upon collision with this mover
	 * @return
	 */
	public int speedChange() {
		return speedChange;
	}
	
}

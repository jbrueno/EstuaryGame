package pkgMover;

public class Food extends Mover {
	
	public int scoreChange = 100;
	public int speedChange = 1;

	public Food(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
		super(x, y, imageWidth, imageHeight, xIncr, yIncr, value);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * the value by which the score in SC will change based on collision with this mover
	 * 
	 * @return int the value of the score change, it will be added to the score of the SCgame
	 *
	 * @return
	 */
	public int getScoreChange() {
		return scoreChange;
	}
	
	/**
	 * the value that the speed of the xIncr of the terrapin will change after collision with 
	 * this mover
	 * 
	 * @return in value of the speed change that will be added to the xIncr
	 */
	public int getSpeedChange() {
		return speedChange;
	}
	
	

}

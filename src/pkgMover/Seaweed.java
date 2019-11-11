package pkgMover;

public class Seaweed extends Mover{
	
	int speedChange = -1;
	int scoreChange = 0;
	
	public Seaweed(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
		super(x, y, imageWidth, imageHeight, xIncr, yIncr, value);
		// TODO Auto-generated constructor stub
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
	 * @return intby which the score will be changed upon collision with this mover
	 */
	public int getSCScoreChange() {
		return scoreChange;
	}
}

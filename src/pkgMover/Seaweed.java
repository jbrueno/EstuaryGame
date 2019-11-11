package pkgMover;

public class Seaweed extends Mover{
	
	int speedChange = -1;
	
	public Seaweed(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
		super(x, y, imageWidth, imageHeight, xIncr, yIncr, value);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Changes the value of the speed of the mover it collides with 
	 * 
	 * @return int by which the score will be changed upon collision
	 */
	public int getScoreChange() {
		return speedChange;
	}

}

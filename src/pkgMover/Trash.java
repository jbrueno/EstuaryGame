package pkgMover;

public class Trash extends SCMover{
	
	public Trash(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
		super(x, y, imageWidth, imageHeight, xIncr, yIncr, value);
		// TODO Auto-generated constructor stub
		this.scoreChange = 50;
		this.collisionSpeedChange = 3;
		this.setImageWidthAndHeight(100, 100);
	}
	
	/**
	 * constructor to be used within the SC model. It only allows input for the canvas width, because 
	 * all other factors should be the same given the game design
	 * 
	 * @param canvasWidth
	 */
	public Trash(int canvasWidth, int y, int speed) {
		super(canvasWidth, y, 100, 100, speed, 0, "Trash");
		this.scoreChange = 50;
		this.collisionSpeedChange = 3;
		this.setImageWidthAndHeight(100, 100);
	}

	@Override
	public int changeScore(int score) {
		return score - scoreChange;
	}
	
	



	
	

	
}
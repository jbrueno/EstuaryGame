package pkgMover;

public class Trash extends SCMover{
	
	public Trash(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
		super(x, y, imageWidth, imageHeight, xIncr, yIncr, value);
		// TODO Auto-generated constructor stub
		this.scoreChange = 50;
		this.collisionSpeedChange = 10;
	}
	
	/**
	 * constructor to be used within the SC model. It only allows input for the canvas width, because 
	 * all other factors should be the same given the game design
	 * 
	 * @param canvasWidth
	 */
	public Trash(int x, int y, int speed) {
		super(x, y, 50, 100, speed, 0, "Trash");
		this.scoreChange = 50;
		this.collisionSpeedChange = 10;
		
	}

	@Override
	public int changeScore(int score) {
		return score - scoreChange;
	}
	
	
	
	
	
	



	
	

	
}
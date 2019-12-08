package pkgMover;

public class Trash extends SCMover{
	
	private static final int TRASHWIDTH = 50;
	private static final int YINCR = 0;
	private static final int TRASHHEIGHT = 100;
	private static final int SCORECHANGE = 50;
	private static final int COLLISIONSPEEDCHANGE = 10;
	
	
	public Trash(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
		super(x, y, imageWidth, imageHeight, xIncr, yIncr, value);
		// TODO Auto-generated constructor stub
		this.scoreChange = SCORECHANGE;
		this.collisionSpeedChange = COLLISIONSPEEDCHANGE;
	}
	
	/**
	 * constructor to be used within the SC model. It only allows input for the canvas width, because 
	 * all other factors should be the same given the game design
	 * 
	 * @param canvasWidth
	 */
	public Trash(int x, int y, int speed) {
		super(x, y, TRASHWIDTH, TRASHHEIGHT, speed, YINCR, "Trash");
		this.scoreChange = SCORECHANGE;
		this.collisionSpeedChange = COLLISIONSPEEDCHANGE;
		
	}

	/**
	 * Overridden change score method subtracts instead of adding, since collisions with trash cause
	 * score loss
	 * 
	 * @return int the new updated score
	 */
	@Override
	public int changeScore(int score) {
		return score - scoreChange;
	}
	
	
	
	
	
	



	
	

	
}
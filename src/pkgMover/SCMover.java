  
/**
 * SCMover is an abstract class created just for objects that are being used in the items array in 
 * the SCModel. This is to avoid code that is only applicable to SCMovers (such as getSpeedChange() ) 
 * from being reproduced into all movers
 */

package pkgMover;

public abstract class SCMover extends Mover {
	private static final long serialVersionUID = 20L;
	
	int collisionSpeedChange;

	public SCMover(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
		super(x, y, imageWidth, imageHeight, xIncr, yIncr, value);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * getter for scoreChange
	 * 
	 * @return int that is the value by which the game score will change after collision with this object
	 */
	public int getScoreChange() {
		return scoreChange;
	}
	
	/**
	 * getter for speed change
	 * 
	 * @return int that is the value that the speed of the terrapin will change after collision with this object
	 */
	public int getCollisionSpeedChange() {
		return collisionSpeedChange;
	}
	
	/**
	 * Changes the current game score based on the need of the SCMover
	 * 
	 * @param  score  int that is the old score 
	 * @return int    The new updated score
	 */
	public abstract int changeScore(int score);
	
	/**
	 * Changes the xIncr to the new desired speed
	 * 
	 * @param speed int representing the new desired speed
	 */
	public void changeSpeed(int speed) {
		setXIncr(speed);
	}
	
	
	

}
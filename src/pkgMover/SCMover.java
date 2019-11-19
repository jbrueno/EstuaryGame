  
/**
 * SCMover is an abstract class created just for objects that are being used in the items array in 
 * the SCModel. This is to avoid code that is only applicable to SCMovers (such as getSpeedChange() ) 
 * from being reproduced into all movers
 */

package pkgMover;

public abstract class SCMover extends Mover {
	
	int speedChange;
	int scoreChange;

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
	public int getSpeedChange() {
		return speedChange;
	}

}
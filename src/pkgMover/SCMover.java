package pkgMover;

public abstract class SCMover extends Mover {
	
	int speedChange;
	int scoreChange;

	public SCMover(int x, int y, int imageWidth, int imageHeight, int xIncr, int yIncr, String value) {
		super(x, y, imageWidth, imageHeight, xIncr, yIncr, value);
		// TODO Auto-generated constructor stub
	}
	
	public int getScoreChange() {
		return scoreChange;
	}
	
	public int getSpeedChange() {
		return speedChange;
	}

}

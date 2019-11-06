package pkgMover;

public class FemaleHSC extends Mover{
	int imageHeight = 200; // unable to use in constructor unfortunately ?
	int imageWidth = 136; // ^
	public FemaleHSC(int x, int y, int xIncr, int yIncr) {
		super(x, y, 200, 136, xIncr, yIncr, "FemaleHSC");
	}
}

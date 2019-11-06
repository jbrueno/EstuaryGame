package pkgMG;


import java.util.ArrayList;

import javafx.scene.input.MouseEvent;
import pkgMover.Mover;
import pkgMover.Terrapin;

public class SCModel extends MinigameModel{
	
	int canvasWidth;
	int canvasHeight;
	int terrapinWidth;
	int terrapinHeight;
	Terrapin terry = new Terrapin();
	ArrayList<Mover> obstacles = new ArrayList<Mover>();
	ArrayList<Mover> food = new ArrayList<Mover>();
	double waterThreshold;
	
	/**Constructor that will be given information on the Terrapins
	 *  starting location, the movers and food currently onscreen
	 *  and the water Y threshold Terry needs to breather
	 *  @author HM
	 * 
	 */
	public SCModel(int cw, int ch, int tw, int th, double wt){
		this.canvasHeight = ch;
		this.canvasWidth = cw;
		this.terrapinHeight = th;
		this.terrapinWidth = tw;
		this.waterThreshold = wt;
	}

	@Override
	void update(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		if (terry.y >= waterThreshold) {
			terry.breathe();
		} else {
			terry.airAmount =- 0.1;
			System.out.println("Terrapin air level " + terry.airAmount);
		}
		
	}

	@Override
	void handleBorderCollision(Mover m) {
		// TODO Auto-generated method stub
		
	}

}

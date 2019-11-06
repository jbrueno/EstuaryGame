package pkgMG;


import java.util.ArrayList;

import javafx.scene.input.MouseEvent;
import pkgEnum.GameState;
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
	final long startNanoTime = System.nanoTime();
	GameState gameState;
	
	/**Constructor that will be given information on the Terrapins
	 *  starting location, the movers and food currently onscreen
	 *  and the water Y threshold Terry needs to breather
	 *  @author HM
	 * 
	 */
	public SCModel(int cw, int ch, int tw, int th, double wt){
		this.canvasHeight = ch;
		terry.y = (canvasHeight/2);
		this.canvasWidth = cw;
		this.terrapinHeight = th;
		this.terrapinWidth = tw;
		this.waterThreshold = wt;
		gameState = GameState.START;
	}

	@Override
	void update(MouseEvent me) {
		
		
	}

	@Override
	public void update() {
		if (terry.y >= waterThreshold) {
			terry.breathe();
		} else {
			terry.airAmount =- 0.1;
			System.out.println("Terrapin air level " + terry.airAmount);
		}
		
		for (Mover m : obstacles) {
			if (terry.x >= m.x - m.imageWidth && terry.x <= m.x + m.imageWidth) {
				if (m.species.equals("seaweed")) {
					
				}
			}
		}
		
		
		
	}

	@Override
	void handleBorderCollision(Mover m) {
		// TODO Auto-generated method stub
		
	}

}

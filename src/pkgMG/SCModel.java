package pkgMG;


import java.util.ArrayList;

import javafx.scene.input.MouseEvent;
import pkgMover.Mover;
import pkgMover.Terrapin;

public class SCModel extends MinigameModel{
	
	Terrapin terry = new Terrapin();
	ArrayList<Mover> obstacles = new ArrayList<Mover>();
	ArrayList<Mover> food = new ArrayList<Mover>();
	double waterThreshold;
	
	/**Constructor that will be given information on the Terrapins
	 *  starting location, the movers and food currently onscreen
	 *  and the water Y threshold Terry needs to breather
	 *  @author HM
	 * 
	 * @param terryx  the starting x location of the terrapin
	 * @param terryy  the starting y location of the terrapin
	 * @param o	      the starting ArrayList<Mover> of obstacles that
	 * 					first appear on-screen
	 * @param f		  the starting ArrayList<Mover> of food (including power-ups) that first appear
	 * 					on-screen
	 * @param waterY  the y-coordinate threshold that the Terrapin must pass in order to breathe
	 */
	public SCModel(double terryx, double terryy, ArrayList<Mover> o, ArrayList<Mover> f, 
					double waterY){
		terry.x = terryx;
		terry.y = terryy;
		this.obstacles = o;
		this.food = f;
		this.waterThreshold = waterY;
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

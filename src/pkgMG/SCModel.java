package pkgMG;

<<<<<<< HEAD
<<<<<<< HEAD
=======

import java.util.ArrayList;

>>>>>>> branch 'master' of https://github.com/CISC275-Fall2019/cisc275f19-project-cisc275f19-team-11-0
import javafx.scene.input.MouseEvent;
import pkgEnum.Game;
import pkgEnum.GameState;
import pkgMover.Mover;
import pkgMover.Terrapin;

public class SCModel extends MinigameModel{
	
	Terrapin terry;
	ArrayList<Mover> items =  new ArrayList<Mover>();
	private final double waterThreshold = 100;
	final long startNanoTime = System.nanoTime();
	GameState gameState;
	 
	/**Constructor that will be given information on the Terrapins
	 *  starting location, the movers and food currently onscreen
	 *  and the water Y threshold Terry needs to breather
	 *  @author HM
	 * 
	 */
	public SCModel(){
		g = Game.SIDESCROLLER;
		terry = new Terrapin(backgroundWidth/2, backgroundHeight/2, 2, 0);
		
		gameState = GameState.INPROGRESS;
		score = 0;
	}

	@Override
	public void update(MouseEvent me) {
		if (terry.getY() >= waterThreshold) {
			terry.breathe();
		} else {
			terry.airAmount = terry.airAmount - 0.5;
			System.out.println("Terrapin air level " + terry.airAmount);
		}
		
		for (Mover m : items) {
			m.setX(m.getX() + m.getxIncr());
			
			if (terry.getX() >= m.getX() - m.getImageWidth() && terry.getX() <= m.getX() + m.getImageWidth()) {
				if (m.getValue().equals("seaweed")) {
					terry.setXIncr(terry.getxIncr() - 1);
					System.out.println("hit seaweed, slowed down");
				} else if (m.getValue().equals("trash")) {
					terry.setXIncr(terry.getxIncr() - 1);
					score = score - 50;
					System.out.println("hit trash, lost points and slowed down");
				} else if (m.getValue().equals("food")) {
					terry.setXIncr(terry.getxIncr() + 1);
					score = score + 100;
					System.out.println("yummy, food!");
				}
			}
			
			if (m.getX() < 0) {
				items.remove(m);
			}
		}
		
	}
	
	public void addItem(Mover m) {
		m.setXIncr(-1);
		m.setYIncr(0);
		m.setX(backgroundWidth);
		if (m.getValue().equals("seaweed") || m.getValue().equals("food")) {
			//que pasa aqui?
		}
	
		
		items.add(m);
		
	}
=======

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
	ArrayList<Mover> items =  new ArrayList<Mover>();
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
		score = 0;
	}

	@Override
	void update(MouseEvent me) {
		me.getSceneY();
		me.getSceneX();
		
	}

	@Override
	public void update() {
		if (terry.y >= waterThreshold) {
			terry.breathe();
		} else {
			terry.airAmount = terry.airAmount - 0.5;
			System.out.println("Terrapin air level " + terry.airAmount);
		}
		
		for (Mover m : items) {
			m.x += m.xIncr;
			
			if (terry.x >= m.x - m.imageWidth && terry.x <= m.x + m.imageWidth) {
				if (m.species.equals("seaweed")) {
					terry.xIncr --;
					System.out.println("hit seaweed, slowed down");
				} else if (m.species.equals("trash")) {
					terry.xIncr--;
					score = score - 50;
					System.out.println("hit trash, lost points and slowed down");
				} else if (m.species.equals("food")) {
					terry.xIncr++;
					score = score + 100;
					System.out.println("yummy, food!");
				}
			}
			
			if (m.x < 0) {
				items.remove(m);
			}
		}
		
	}
	
	public void addItem(Mover m) {
		m.xIncr = -1;
		m.yIncr = 0;
		m.x = canvasWidth;
		if (m.species.equals("seaweed") || m.species.equals("food")) {
			
		}
	
		
		items.add(m);
		
	}

	@Override
	void handleBorderCollision(Mover m) {
		// TODO Auto-generated method stub
		
	}
	
	public double getTerrapinX() {
		return terry.x;
	}
	
	public double getTerrapinY() {
		return terry.y;
	}
	
	
	
>>>>>>> branch 'master' of https://github.com/CISC275-Fall2019/cisc275f19-project-cisc275f19-team-11-0

	@Override
	public void handleBorderCollision(Mover m) {
		// TODO Auto-generated method stub
		
	}
}


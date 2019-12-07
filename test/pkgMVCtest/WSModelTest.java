package pkgMVCtest;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import javafx.scene.input.MouseEvent;
import pkgEnum.Game;
import pkgEnum.GameState;
import pkgMGModel.WSModel;
import pkgMGModel.MinigameModel;
import pkgMover.Mover;
import pkgMGModel.WSModel.Bottle;
import pkgMGModel.WSModel.pHStrip;
import pkgMGModel.WSModel.testTube;

public class WSModelTest {
	private WSModel WSModel;
	private Bottle Bottle;
	private pHStrip pHStrip;
	private testTube testTube;
	
	private Method addObjects;
	private Method dipStrip;
	
	@Before
	public void setUp() throws Exception {
		WSModel = new WSModel();
		Bottle = WSModel.new Bottle(640, 600, 0, 0, "Bottle");
		pHStrip = WSModel.new pHStrip(640, 600, 0, 0, "pHStrip");
		testTube = WSModel.new testTube(0,0,0,0, "testtube");
	}
	
	@Test
	public void WSModelConstructor_test() {
		assertTrue(WSModel.getGame() == Game.WATERSAMPLING);
		assertTrue(WSModel.getGameState() == GameState.WS_COLLECTTUTORIAL);
		assertTrue(WSModel.getMovers().contains(Bottle) == true);
		
		WSModel.setGameState(GameState.WS_COLLECT);
		assertTrue(WSModel.getGame() == Game.WATERSAMPLING);
		assertTrue(WSModel.getGameState() == GameState.WS_COLLECT);
	}
	
	@Test
	public void addObjectsTest() {
	
	WSModel.addObjects(GameState.WS_COLLECT);
	assertTrue(WSModel.getMovers().contains(Bottle) == true);

	WSModel.addObjects(GameState.WS_PHTUTORIAL);
	assertTrue(WSModel.getMovers().contains(testTube) == true);
	}

	public void setpH_test() {
		System.out.println(pHStrip.getpH());
		
	}
}

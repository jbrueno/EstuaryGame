package pkgMGModelTest;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import javafx.scene.input.MouseEvent;
import pkgEnum.Game;
import pkgEnum.GameState;
import pkgMGModel.WSModel;
import pkgMGModel.AMModel.MatchingAnimal;
import pkgMGModel.WSModel.pHStrip;
import pkgMGModel.AMModel;
import pkgMGModel.MinigameModel;
import pkgMover.Mover;

public class WSModelTest {
	private WSModel WSModel;

	
	private Method addObjects;
	private Method fillBottle;
	private Method setPH;
	private Method calculateCollectSore;
	private Method dipStrip;
	private Method changeColor;
	private Method calculatePHScore;
	
	private static String ADD_OBJECTS = "addObjects";
	private static String SET_PH = "setPH";
	private static String FILL_BOTTLE = "fillBottle";
	private static String CALCULATE_PH_SCORE = "calculatePHScore";
	
	
	private Class<?>[] parameterTypes;
	Class<? extends MinigameModel> c;
	Class<? extends WSModel> cc;
	
	
	@Before
	public void setUp() throws Exception {
		WSModel = new WSModel();
		
		c = (Class<? extends MinigameModel>) WSModel.getClass().getSuperclass();
		cc = WSModel.getClass();

		

		
		parameterTypes = new Class[2];
		parameterTypes[0] = pkgMover.Mover.class;
		parameterTypes[1] = javafx.scene.input.MouseEvent.class;

		
		
		fillBottle = WSModel.getClass().getDeclaredMethod(FILL_BOTTLE, (Class<?>[]) null); 
		fillBottle.setAccessible(true);
		
		setPH= WSModel.getClass().getDeclaredMethod(SET_PH, (Class<?>[]) null); 
		setPH.setAccessible(true);
		
		calculatePHScore= WSModel.getClass().getDeclaredMethod(CALCULATE_PH_SCORE);
		//calculatePHScore= WSModel.getClass().getDeclaredMethod(CALCULATE_PH_SCORE, (Class<?>[]) null); 

		calculatePHScore.setAccessible(true);
	}
	
	@Test
	public void WSModelConstructor_test() {
		assertTrue(WSModel.getGame() == Game.WATERSAMPLING);
		assertTrue(WSModel.getGameState() == GameState.WS_COLLECTTUTORIAL);
		assertTrue(WSModel.getMovers().size() == 1);
	}
	
	@Test
	public void addObjects_test() {
		WSModel.addObjects(GameState.START); // default case, nothing new added
		assertTrue(WSModel.getMovers().size() == 1);
		
		WSModel.addObjects(GameState.WS_COLLECT);
		assertTrue(WSModel.getMovers().size() == 1);
		
		WSModel.addObjects(GameState.WS_PHTUTORIAL);
		assertTrue(WSModel.getMovers().size() == 2);
		
		WSModel.addObjects(GameState.WS_PH);
		assertTrue(WSModel.getMovers().size() == 2);
		
		
		
	
	}
	
	@Test
	public void fillBottle_test() {
		assertTrue(WSModel.getMovers().get(0).getValue().equals("Bottle"));
		WSModel.fillBottle();
		assertTrue(WSModel.getMovers().get(0).getValue().equals("fullBottle"));
	}
	
	@Test
	public void setPH_test() {
		System.out.println(WSModel.getMovers());
		WSModel.setPH();
		assertTrue(WSModel.getPH() >= 5);
		assertTrue(WSModel.getPH() <= 9);
	}
	
	
	@Test
	public void calculatePHScore() {
		WSModel.getMovers().get(0).setY(0);
		//WSModel.calculatePHScore();
		//
	}
	
	
	
	
	
	
	@Test
	public void update_test() throws Exception{
		WSModel.getMovers().get(0).setY(750);
		 MouseEvent me = new MouseEvent(MouseEvent.MOUSE_PRESSED, 500, 500, 0, 0, null, 0, false, false, false, false,
					true, false, false, false, false, false, null);
		 MouseEvent me2 = new MouseEvent(MouseEvent.MOUSE_RELEASED, 500, 500, 0, 0, null, 0, false, false, false, false,
					true, false, false, false, false, false, null);
		
		Field f = c.getDeclaredField("gs");
		f.setAccessible(true);
		f.set(WSModel, GameState.FINISHED); // unused, default switch
		WSModel.update(me);
		
		f.set(WSModel, GameState.START);
		WSModel.update(me);
		assertTrue(WSModel.getGameState() == GameState.WS_COLLECTTUTORIAL);
		WSModel.getMovers().get(0).setY(0);
		WSModel.update(me);
		f = cc.getDeclaredField("filled");
		f.setAccessible(true);
		f.set(WSModel, true); 
		WSModel.update(me);
		WSModel.update(me2);
		WSModel.getMovers().get(0).setY(0);
		WSModel.update(me);
		WSModel.update(me2);
		f.set(WSModel, false);
		WSModel.getMovers().get(0).setY(500);
		WSModel.update(me);


		f = cc.getDeclaredField("btnSourceId");
		f.setAccessible(true);
		f.set(WSModel, "Fill");
		WSModel.update(me2);
		WSModel.update(me);
		
		f.set(WSModel, "Play");
		WSModel.update(me);
		
		// GameState WS_COLLECT
		WSModel.update(me);
		f = cc.getDeclaredField("filled");
		f.setAccessible(true);
		f.set(WSModel, true);
		WSModel.update(me);
		f.set(WSModel, false);
		WSModel.update(me2);
		f = cc.getDeclaredField("btnSourceId");
		f.setAccessible(true);
		f.set(WSModel, "Fill");
		WSModel.getMovers().get(0).setY(0);
		WSModel.update(me);
		WSModel.getMovers().get(0).setY(500);
		WSModel.update(me);
		WSModel.update(me2);
		f.set(WSModel, "Lab");
		WSModel.update(me);
		
		// GameState WS_PHTUTORIAL
		WSModel.update(me);
		WSModel.update(me);
		WSModel.update(me2);
		f.set(WSModel, "phStripBox");
		WSModel.update(me2);
		f = cc.getDeclaredField("gotStrip");
		f.setAccessible(true);
		f.set(WSModel, true);
		WSModel.update(me);
		f.set(WSModel, false);
		WSModel.update(me);
		WSModel.update(me2);
		
		f = cc.getDeclaredField("btnSourceId");
		f.setAccessible(true);
		f.set(WSModel, "Play");
		WSModel.update(me);
		
		//GameState WS_PH
		WSModel.update(me);
		WSModel.update(me2);
		f = cc.getDeclaredField("btnSourceId");
		f.setAccessible(true);
		f.set(WSModel, "phStripBox");
		WSModel.update(me2);
		WSModel.update(me);
		WSModel.update(me);
		f = cc.getDeclaredField("btnSourceId");
		f.setAccessible(true);
		f.set(WSModel, "Submit");
		WSModel.update(me2);
		WSModel.update(me);
		WSModel.update(me);

	}
}

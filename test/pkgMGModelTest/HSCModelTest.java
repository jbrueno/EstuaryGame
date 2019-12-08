package pkgMGModelTest;

import java.lang.reflect.Method;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import javafx.scene.input.MouseEvent;
import pkgEnum.Game;
import pkgEnum.GameState;
import pkgMGModel.HSCModel;
import pkgMGModel.HSCModel.HSC;
import pkgMGModel.MinigameModel;
import pkgMover.Mover;

public class HSCModelTest {

	int backgroundWidth = 1280;
	int backgroundHeight = 768;
	int buffer = 200;
	private HSCModel HSCModel;
	private HSC HSC;
	private Method increaseSpeed;
	private Method tutorialHSCrabClicked;
	private Method spawnHSCrabs;
	private Method createHSCrabs;
	private static String INCREASE_SPEED = "increaseSpeed";
	private static String TUTORIAL_HSC_CLICKED = "tutorialHSCrabClicked";
	private static String SPAWN_HSCRABS = "spawnHSCrabs";
	private static String CREATE_HSCRABS = "createHSCrabs";
	private Class<?>[] parameterTypes;

	@Before
	public void setUp() throws Exception {
		HSCModel = new HSCModel();
		HSC = HSCModel.new HSC(100, 100, 5, 5);

		parameterTypes = new Class[2];
		parameterTypes[0] = pkgMover.Mover.class;
		parameterTypes[1] = javafx.scene.input.MouseEvent.class;

		increaseSpeed = HSC.getClass().getDeclaredMethod(INCREASE_SPEED, (Class<?>[]) null);
		increaseSpeed.setAccessible(true);

		createHSCrabs = HSCModel.getClass().getDeclaredMethod(CREATE_HSCRABS, (Class<?>[]) null);
		createHSCrabs.setAccessible(true);

		tutorialHSCrabClicked = HSCModel.getClass().getDeclaredMethod(TUTORIAL_HSC_CLICKED, parameterTypes[1]);
		tutorialHSCrabClicked.setAccessible(true);

		spawnHSCrabs = HSCModel.getClass().getDeclaredMethod(SPAWN_HSCRABS, parameterTypes[0]);
		spawnHSCrabs.setAccessible(true);

	}

	@Test
	public void HSCModelConstructor_test() {
		assertTrue(HSCModel.getGame() == Game.HSCCOUNT);
		assertTrue(HSCModel.getGameState() == GameState.TUTORIAL);
		assertTrue(HSCModel.getTime() == 300);
	}

	@Test
	public void HSCConstructor_test() {
		assertTrue(HSC.getX() == 100);
		assertTrue(HSC.getY() == 100);
		assertTrue(HSC.getXIncr() == 5);
		assertTrue(HSC.getYIncr() == 5);
		assertTrue(HSC.getImageWidth() == 200);
		assertTrue(HSC.getImageHeight() == 136);
		assertTrue(HSC.getTagged() == false);
	}

	@Test
	public void HSCTag_test() {
		HSC.tag();
		assertTrue(HSC.getTagged());
	}

	@Test
	public void HSCunTag_test() {
		HSC.unTag();
		assertFalse(HSC.getTagged());
	}

	@Test
	public void setTimerSet_test() {
		HSCModel.setTimerSet(false);
		assertFalse(HSCModel.getTimerSet());
	}

	@Test
	public void HSCIncreaseSpeedTest() throws Exception {
		increaseSpeed.invoke(HSC, (Object[]) null);
		assertTrue(HSC.getYIncr() > 5);
		assertTrue(HSC.getXIncr() > 5);

		HSC.setXIncr(-5);
		HSC.setYIncr(-5);
		increaseSpeed.invoke(HSC, (Object[]) null);

		assertTrue(HSC.getYIncr() < -5);
		assertTrue(HSC.getXIncr() < -5);
	}

	@Test
	public void createHSCrabs_test() throws Exception {
		createHSCrabs.invoke(HSCModel, (Object[]) null);
		assertTrue(HSCModel.getMovers().size() == 20);
		// 20 instead of 10 because createHSCrabs was called once before
	}

	@Test
	public void tutorialHSCrabClicked_test() throws Exception {
		MouseEvent me = new MouseEvent(MouseEvent.MOUSE_CLICKED, 641, 385, 0, 0, null, 0, false, false, false, false,
				true, false, false, false, false, false, null); 

		boolean result = (boolean) tutorialHSCrabClicked.invoke(HSCModel, me);
		assertTrue(result);

		MouseEvent me1 = new MouseEvent(MouseEvent.MOUSE_PRESSED, 50, 50, 0, 0, null, 0, false, false, false, false,
				true, false, false, false, false, false, null);

		result = (boolean) tutorialHSCrabClicked.invoke(HSCModel, me1);
		assertFalse(result);
		
		MouseEvent me3 = new MouseEvent(MouseEvent.MOUSE_CLICKED, 2000, 1500, 0, 0, null, 0, false, false, false, false,
				true, false, false, false, false, false, null);
		
		result = (boolean) tutorialHSCrabClicked.invoke(HSCModel, me3);
		
		MouseEvent me4 = new MouseEvent(MouseEvent.MOUSE_CLICKED, 641, -300, 0, 0, null, 0, false, false, false, false,
				true, false, false, false, false, false, null);
		
		result = (boolean) tutorialHSCrabClicked.invoke(HSCModel, me4);
		
		MouseEvent me5 = new MouseEvent(MouseEvent.MOUSE_CLICKED, 641, 1500, 0, 0, null, 0, false, false, false, false,
				true, false, false, false, false, false, null);
		
		result = (boolean) tutorialHSCrabClicked.invoke(HSCModel, me5);
	}

	@Test
	public void spawnHSCrabs_test() throws Exception {
		Mover m = HSC;
		// > backgroundWidth
		m.setX(2000);
		spawnHSCrabs.invoke(HSCModel, m);
		assertTrue(m.getX() <= 0);

		// < backgroundWidth
		m.setX(-300);
		spawnHSCrabs.invoke(HSCModel, m);
		assertTrue(m.getX() >= backgroundWidth);

		// > backgroundHeight
		m.setX(600);
		m.setY(1500);
		spawnHSCrabs.invoke(HSCModel, m);
		assertTrue(m.getY() <= 0);

		// < backgroundHeight
		m.setY(-500);
		spawnHSCrabs.invoke(HSCModel, m);
		assertTrue(m.getY() >= backgroundHeight);
	}

	@Test
	public void HSCModelUpdate_test() throws Exception {
		MouseEvent me = new MouseEvent(MouseEvent.DRAG_DETECTED, 641, 385, 0, 0, null, 0, false, false, false, false,
				true, false, false, false, false, false, null);
		HSCModel.update(me);
		
		assertTrue(HSCModel.getGameState() == GameState.TUTORIAL);
		
		MouseEvent me1 = new MouseEvent(MouseEvent.MOUSE_CLICKED, 641, 385, 0, 0, null, 0, false, false, false, false,
				true, false, false, false, false, false, null);

		HSCModel.update(me1);
		assertTrue(HSCModel.getGameState() == GameState.TUTORIAL2);
		assertFalse(HSCModel.getTimerSet());
		assertTrue(HSCModel.getScore() == 0);
		
		HSCModel.update(me);
		assertTrue(HSCModel.getGameState() == GameState.TUTORIAL2);

		HSCModel.update(me1);
		assertTrue(HSCModel.getGameState() == GameState.INPROGRESS);

		// create MouseEvent that clicks a HSC
		Mover m = HSCModel.getMovers().get(5);
		m.setX(500);
		m.setY(500);
		MouseEvent me2 = new MouseEvent(MouseEvent.MOUSE_CLICKED, 500, 500, 0, 0, null, 0, false, false, false, false,
				true, false, false, false, false, false, null);
 
		HSCModel.update(me2);
		assertTrue(HSCModel.getTimerSet());
		assertTrue(((HSC) m).getTagged() == true);
		assertTrue(HSCModel.getScore() > 0);
		
		Mover m2 = HSCModel.getMovers().get(9);
		m2.setX(500);
		m2.setY(500);
		MouseEvent me3 = new MouseEvent(MouseEvent.MOUSE_PRESSED, 500, 500, 0, 0, null, 0, false, false, false, false,
				true, false, false, false, false, false, null);
		HSCModel.update(me3);
		assertTrue(((HSC) m2).getTagged() == true);
		
	}

}

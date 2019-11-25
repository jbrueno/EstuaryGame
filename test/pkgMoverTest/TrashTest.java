package pkgMoverTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import pkgMover.Trash;

public class TrashTest {
	
	@Test
	public void changeScore_Test() {
		Trash t = new Trash(1000, 100, -10);
		int trashScore = t.changeScore(100);
		assertTrue(trashScore == 50);
	}
	
	@Test
	public void changeSpeed_test() {
		Trash t = new Trash (100, 100, -10);
		t.changeSpeed(-100);
		assertTrue(t.getxIncr() == -100);
	}
	
	@Test
	public void trashConstructor_test() {
		Trash t = new Trash(0, 0, 0, 0, 0, 0, "Trash");
		assertTrue(t instanceof Trash);
	}
	
	

}

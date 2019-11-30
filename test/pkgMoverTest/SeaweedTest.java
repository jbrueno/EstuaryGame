package pkgMoverTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import pkgMover.Seaweed;

public class SeaweedTest {
	
	@Test
	public void changeScore_Test() {
		Seaweed s = new Seaweed(1000, 100, -10);
		int sea = s.changeScore(100);
		assertTrue(sea == 100);
	}
	
	@Test
	public void changeSpeed_test() {
		Seaweed s = new Seaweed(100, 100, -10);
		s.changeSpeed(-100);
		assertTrue(s.getXIncr() == -100);
	}
	
	@Test
	public void trashConstructor_test() {
		Seaweed s = new Seaweed(0, 0, 0, 0, 0, 0, "Trash");
		assertTrue(s instanceof Seaweed);
	}

}

package pkgMoverTest;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import pkgMover.SCMover;
import pkgMover.Trash;

public class SCMoverTest {
	
	@Test
	public void collisionSpeedChange_Test() {
		SCMover trash = new Trash(100, 100, 100);
		assertTrue(trash.getCollisionSpeedChange() == 10);
	}
	
	@Test
	public void scoreChange_Test() {
		SCMover trash = new Trash(100, 100, 100);
		assertTrue(trash.getScoreChange() == 50);
	}
	
	@Test
	public void changeSpeed_Test() {
		SCMover trash = new Trash(100, 100, 100);
		trash.changeSpeed(10);
		assertTrue(trash.getXIncr() == 10);
	}

}

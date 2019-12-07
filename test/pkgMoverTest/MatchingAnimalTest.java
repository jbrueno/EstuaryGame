package pkgMoverTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import pkgMover.MatchingAnimal;

public class MatchingAnimalTest {
	
	@Test
	public void toString_test() {
		MatchingAnimal ma = new MatchingAnimal(0, 0, 0, 0, "animal", "name", "clue");
		String maString = "name: 0.0, 0.0";
		assertEquals(maString, ma.toString());
	}
	
	@Test
	public void isMatched_test() {
		MatchingAnimal ma = new MatchingAnimal(0, 0, 0, 0, "animal", "name", "clue");
		assertFalse(ma.isMatch("clue"));
		assertTrue(ma.isMatch("name"));
	}

}

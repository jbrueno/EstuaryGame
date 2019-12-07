package pkgMoverTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Objects;

import org.junit.Test;

import pkgMover.MatchingAnimal;
import pkgMover.Mover;
import pkgMover.Trash;

public class MoverTest {
	
	@Test
	public void getImageWidthHeight_test() {
		Mover m = new Trash(100, 100, 100);
		assertTrue(m.getImageHeight() == 100);
		assertTrue(m.getImageWidth() == 50);
	}
	
	@Test
	public void getTranslatedXY_test() {
		Mover m = new Trash(100, 100, 100);
		assertTrue(m.getTranslatedX() == 75);
		assertTrue(m.getTranslatedY() == 50);
	}
	
	@Test
	public void moveNoArg_test() {
		Mover m = new Trash(100, 100, 100);
		m.move();
		assertTrue(m.getX() == 200);
		assertTrue(m.getY() == 100);
	}
	
	@Test
	public void MoveTwoArg_test() {
		Mover m = new Trash(100, 100, 100);
		m.move(0, 0);
		assertTrue(m.getX() == 0);
		assertTrue(m.getY() == 0);
	}
	
	@Test
	public void getSetValue_test() {
		Mover m = new Trash(100, 100, 100);
		m.setValue("new value");
		assertEquals(m.getValue(), "new value");
	}
	
	@Test
	public void toString_test() {
		Mover m = new Trash(100, 100, 100);
		String mString = "Trash: 100.0 100.0";
		assertEquals(m.toString(), mString);
	}
	
	@Test
	public void hashCode_test() {
		Mover m = new Trash(100, 100, 100);
		assertEquals(m.hashCode(), Objects.hash("Trash"));
	}
	
	@Test
	public void equals_test() {
		Mover m1 = new Trash(100, 100, 100);
		Mover m2 = new Trash(0, 0, 0);
		assertTrue(m1.equals(m2));
		String s = "Trash";
		assertFalse(m1.equals(s));
	}
	
	@Test
	public void moveFourArg_test() {
		Mover m = new Trash(0, 0, 1);
		m.move(0, 0, 10, 10);
		assertTrue(m.getYIncr() == 0);
		assertTrue(m.getXIncr() == 1);
		
		m.move(-10, -10, -10, -10);
		assertTrue(m.getYIncr() == 0);
		assertTrue(m.getXIncr() == -1);
		
		m.setX(0);
		m.move(-10, 10, 10, -10);
		assertTrue(m.getYIncr() == 0);
		assertTrue(m.getXIncr() == -1);
		
		m.setX(0);
		m.move(10, -10, -10, 10);
		assertTrue(m.getYIncr() == 0);
		assertTrue(m.getXIncr() == -1);
		
		m.setX(0);
		m.move(-10, -10, 10, 10);
		assertTrue(m.getYIncr() == 0);
		assertTrue(m.getXIncr() == -1);
		
		m.setX(0);
		m.move(10, 10, -10, 10);
		assertTrue(m.getYIncr() == 0);
		assertTrue(m.getXIncr() == 1);
	}

}

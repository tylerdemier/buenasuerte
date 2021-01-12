package test;

import static org.junit.Assert.assertEquals;


import org.junit.Test;

import astronomy.Constellation;
import astronomy.SpectralType;

public class ConstelacionTest {
	
	@Test
	public void testGetAbbrv() {
		Constellation cons = new Constellation("Pac", "Paco", "francisco", "su casa", 3);
		assertEquals("Pac", cons.getAbbrv());
	}
	@Test
	public void testGetName() {
		Constellation cons = new Constellation("Pac", "Paco", "francisco", "su casa", 3);
		assertEquals("Paco", cons.getName());
	}
	@Test
	public void testGetMeaning() {
		Constellation cons = new Constellation("Pac", "Paco", "francisco", "su casa", 3);
		assertEquals("francisco", cons.getMeaning());
	}
	@Test
	public void testGetOrigin() {
		Constellation cons = new Constellation("Pac", "Paco", "francisco", "su casa", 3);
		assertEquals("su casa", cons.getOrigin());
	}
	@Test
	public void testGetStars() {
		Constellation cons = new Constellation("Pac", "Paco", "francisco", "su casa", 3);
		assertEquals(3, cons.getStars());
	}
	@Test
	public void testToString() {
		Constellation cons = new Constellation("Pac", "Paco", "francisco", "su casa", 3);
		assertEquals("Paco", cons.toString());
	}
	@Test
	public void testEquals() {
		Constellation cons = new Constellation("Pac", "Paco", "francisco", "su casa", 3);
		Constellation cons1 = new Constellation("Pac", "Paco", "francisco", "su casa", 3);
		SpectralType a = new SpectralType("A3");
		assertEquals(true, cons.equals(cons1));
		assertEquals(false, cons.equals(a));
	}

}
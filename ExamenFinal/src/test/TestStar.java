package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import astronomy.Constellation;
import astronomy.SpectralType;
import astronomy.Star;

public class TestStar {

	@Test
	public void testGetName() {
		SpectralType espectro = new SpectralType("A");
		Constellation constelacion = new Constellation("pesc", "Piscis", "el pesado", "Latin", 3);
		Star estrella = new Star("augusta", 50000.23f, 4123.3452f, constelacion, 123, 321, 5, espectro);
		
		assertEquals("augusta", estrella.getName());
	}
	
	@Test
	public void testGetConstellation() {
		SpectralType espectro = new SpectralType("A");
		Constellation constelacion = new Constellation("pesc", "Piscis", "el pesado", "Latin", 3);
		Star estrella = new Star("augusta", 50000.23f, 4123.3452f, constelacion, 123, 321, 5, espectro);

		assertEquals(constelacion, estrella.getConstellation());
	}
	
	@Test
	public void testGetRa() {
		SpectralType espectro = new SpectralType("A");
		Constellation constelacion = new Constellation("pesc", "Piscis", "el pesado", "Latin", 3);
		Star estrella = new Star("augusta", 50000.23f, 4123.3452f, constelacion, 123, 321, 5, espectro);
		
		assertEquals(50000.22f, estrella.getRa(), 0.02);
		
	}
	
	@Test
	public void testGetDec() {
		SpectralType espectro = new SpectralType("A");
		Constellation constelacion = new Constellation("pesc", "Piscis", "el pesado", "Latin", 3);
		Star estrella = new Star("augusta", 50000.23f, 4123.3452f, constelacion, 123, 321, 5, espectro);
		
		assertEquals(4123.3432f, estrella.getDec(), 0.02);
		
	}
	
	@Test
	public void testGetDistance() {
		SpectralType espectro = new SpectralType("A");
		Constellation constelacion = new Constellation("pesc", "Piscis", "el pesado", "Latin", 3);
		Star estrella = new Star("augusta", 50000.23f, 4123.3452f, constelacion, 123, 321, 5, espectro);
		
		assertEquals(122.98, estrella.getDistance(), 0.02);
		
	}
	
	@Test
	public void testGetMagnitude() {
		SpectralType espectro = new SpectralType("A");
		Constellation constelacion = new Constellation("pesc", "Piscis", "el pesado", "Latin", 3);
		Star estrella = new Star("augusta", 50000.23f, 4123.3452f, constelacion, 123, 321, 5, espectro);
		
		assertEquals(320.98, estrella.getMagnitude(), 0.02);
		
	}
	
	@Test
	public void testGetLuminosity() {
		SpectralType espectro = new SpectralType("A");
		Constellation constelacion = new Constellation("pesc", "Piscis", "el pesado", "Latin", 3);
		Star estrella = new Star("augusta", 50000.23f, 4123.3452f, constelacion, 123, 321, 5, espectro);
		
		assertEquals(4.98, estrella.getLuminosity(), 0.02);
	}
	
	@Test
	public void testGetSpectralType() {
		SpectralType espectro = new SpectralType("A");
		Constellation constelacion = new Constellation("pesc", "Piscis", "el pesado", "Latin", 3);
		Star estrella = new Star("augusta", 50000.23f, 4123.3452f, constelacion, 123, 321, 5, espectro);
		
		assertEquals(espectro, estrella.getSpectralType());
		
	}
	
	
}


package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import astronomy.SpectralType;
import astronomy.SpectralType.SpectralClass;

public class TestSpectralType {

	@Test
	public void testGetSpectralClass() {
		SpectralType espectro = new SpectralType("A9");
		SpectralClass expected = SpectralClass.A;
		assertEquals(expected, espectro.getSpectralClass());

		SpectralType espectroOther = new SpectralType("");
		SpectralClass expected1 = SpectralClass.OTHER;
		assertEquals(expected1, espectroOther.getSpectralClass());
	}

	@Test
	public void testGetSpectralNumber() {
		SpectralType espectro = new SpectralType("A9");
		assertEquals(9, espectro.getSpectralNumber());

		SpectralType espectroSinNum = new SpectralType("AA");
		assertEquals(-1, espectroSinNum.getSpectralNumber());
	}

	@Test
	public void testToString() {
		SpectralType espectroNoOtherYConNumero = new SpectralType("A9");
		String expected0 = "A9";
		assertEquals(expected0, espectroNoOtherYConNumero.toString());

		SpectralType espectroNoOtherYSinNumero = new SpectralType("A");
		String expected1 = "A";
		assertEquals(expected1, espectroNoOtherYSinNumero.toString());

		SpectralType espectroOther = new SpectralType("P8");
		String expected2 = "OTHER";
		assertEquals(expected2, espectroOther.toString());
	}

}
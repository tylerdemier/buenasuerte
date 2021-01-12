package astronomy;

/**
 * Esta clase representa una estrella con sus distintos
 * datos característicos.
 * No todas las estrellas tienen un nombre propio.
 */
public class Star {

	private String name; // nombre propio de la estrella si existe.
	private Constellation constellation; // constelación a la que pertenece la estrella
	private float ra, dec; // coordenadas ecuatoriales de la estrella en el cielo (ascensión recta y declinación)
	private float distance; // distancia en parsecs a la estrella
	private float magnitude; // magnitud del brillo de la estrella
	private float luminosity; // luminosidad como múltiplo de la luminosidad solar
	private SpectralType spectralType;

	/**
	 * Construye una constelación a partir de los datos indicados. 
	 * @param name nombre propio de la estrella
	 * @param ra ascensión recta de la estrella (coordenadas ecuatoriales)
	 * @param dec declinación de la estrella (coordenadas ecuatoriales)
	 * @param constellation constelación a la que pertenece la estrella
	 * @param distance distancia en parsecs a la estrella
	 * @param magnitude magnitud de la estrella 
	 * @param luminosity luminosidad de la estrella como múltiplo de la luminosidad solar
	 * @param spectralType tipo espectral de la estrella
	 */
	public Star(String name, float ra, float dec, Constellation constellation, float distance, float magnitude, float luminosity, SpectralType spectralType) {
		this.name = name;
		this.constellation = constellation;
		this.ra = ra;
		this.dec = dec;
		this.distance = distance;
		this.magnitude = magnitude;
		this.luminosity = luminosity;
		this.spectralType = spectralType;
	}

	/**
	 * Nombre propio de la estrella. La mayoría de la estrellas no tienen nombre propio
	 * @return nombre propio de la estrella
	 */
	public String getName() {
		return name;
	}

	/**
	 * Constelación a la que pertenece la estrella
	 * @return constelación a la que pertenece la estrella
	 */
	public Constellation getConstellation() {
		return constellation;
	}

	/**
	 * Ascensión recta de la posición de la estrella en coordenadas ecuatoriales
	 * @return ascensión recta de la estrella
	 */
	public float getRa() {
		return ra;
	}

	/**
	 * Declinación de la posición de la estrella en coordenadas ecuatoriales
	 * @return declinación de la estrella 
	 */
	public float getDec() {
		return dec;
	}

	/**
	 * Distancia en parsecs a la estrella
	 * @return distancia en parsecs a la estrella
	 */
	public float getDistance() {
		return distance;
	}

	/**
	 * Magnitud (brillo) de la estrella. Cuando menor es la magnitud de una estrella
	 * mayor es su brillo 
	 * @return magnitud de la estrella
	 */
	public float getMagnitude() {
		return magnitude;
	}

	/**
	 * Luminosidad de la estrella en múltiplos de la luminosidad solar
	 * @return luminosidad de la estrella
	 */
	public float getLuminosity() {
		return luminosity;
	}

	/**
	 * Tipo espectral de la estrella
	 * @return tipo espectral de la estrella
	 */
	public SpectralType getSpectralType() {
		return spectralType;
	}
}

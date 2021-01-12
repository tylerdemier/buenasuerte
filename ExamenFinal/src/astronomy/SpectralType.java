package astronomy;

/**
 * Esta clase contiene información sobre tipo espectral
 * de una estrella. El tipo espectral de una estrella viene
 * identificada por una letra y un número entre 0 y 9.
 * Por ejemplo, 06, K3, F2, etc.
 */
public class SpectralType {
	
	/**
	 * Representa las clases de valores espectrales a los
	 * que puede pertener una estrella (O-B-A-F-G-K-M-C y Otro)
	 */
	public enum SpectralClass {
		O, B, A, F, G, K, M, C, OTHER
	}
	
	private SpectralClass spectralClass; // clase espectral de la estrella
	private int spectralNumber = -1; // número espectral de la estrella (por defecto sin especificar)

	/**
	 * Construye el valor espectral de la estrella a partir de su representación
	 * en cadena de caracteres (string). Un tipo espectral viene representado por una letra
	 * de las siguientes: O-B-A-F-G-K-M-C y un número entre 0 y 9. Este número espectral es
	 * opcional y puede que no exista en el string pasado, permitiéndose que después de la
	 * letra inicial pueda ir cualquier otro caracter. En este caso, únicamente se considera
	 * la letra como la clase espectral, ignorando el resto de la información.
	 * En el caso de que se produzca un error al procesar el tipo espectral, o que el string
	 * pasado esté vacío, se considerará que el tipo espectral es OTHER, dejando el número
	 * espectral a -1 (sin especificar).
	 * @param spectralType la clase espectral de la estrella como cadena de caracteres.
	 */
	public SpectralType(String spectralType) {
		if (!spectralType.isEmpty()) {
			try {
				spectralClass = SpectralClass.valueOf(spectralType.substring(0, 1));
				
				if (spectralType.length() >= 2) {
					int number = Integer.parseInt(spectralType.substring(1, 2));
					spectralNumber = number;
				} 
			} catch (NumberFormatException e) {
				spectralNumber = -1;
			} catch (IllegalArgumentException e) {
				spectralClass = SpectralClass.OTHER;
			}
		} else {
			spectralClass = SpectralClass.OTHER;
		}
	}

	/**
	 * Obtiene la clase espectral de la estrella
	 * @return la clase espectral de la estrella
	 */
	public SpectralClass getSpectralClass() {
		return spectralClass;
	}

	/**
	 * Obtiene el número espectral de la estrella
	 * @return el número espectral de la estrella
	 */
	public int getSpectralNumber() {
		return spectralNumber;
	}
	
	@Override
	public String toString() {
		if (spectralClass != SpectralClass.OTHER) {
			if (spectralNumber != -1) {
				return String.format("%s%d", spectralClass.toString(), spectralNumber);
			} else {
				return String.format("%s", spectralClass.toString());
			}
		} else {
			return SpectralClass.OTHER.toString();
		}
	}
}

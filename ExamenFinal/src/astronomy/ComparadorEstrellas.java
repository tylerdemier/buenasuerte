package astronomy;

import java.util.Comparator;


public class ComparadorEstrellas implements Comparator<Star> {

	
	// COMPARA LAS ESTRELLAS
	
	@Override
	public int compare(Star o1, Star o2) {
		
		// SI EL SPECTRAL TYPE DE LA PRIMERA ESTRELLA ES LA MISMA QUE LA SPECTRAL TYPE QUE LA SEGUNDA
		if(o1.getSpectralType().getSpectralClass() == o2.getSpectralType().getSpectralClass()) {
			
			
			return o1.getSpectralType().getSpectralNumber() - (o2.getSpectralType().getSpectralNumber());
			
		} else {
			
			
			return o1.getSpectralType().getSpectralClass().compareTo(o2.getSpectralType().getSpectralClass());
		}
	}

}

package astronomy;

import java.util.Comparator;


public class ComparadorEstrellas implements Comparator<Star> {

	@Override
	public int compare(Star o1, Star o2) {
		if(o1.getSpectralType().getSpectralClass() == o2.getSpectralType().getSpectralClass()) {
			return o1.getSpectralType().getSpectralNumber() - (o2.getSpectralType().getSpectralNumber());
		} else {
			return o1.getSpectralType().getSpectralClass().compareTo(o2.getSpectralType().getSpectralClass());
		}
	}

}

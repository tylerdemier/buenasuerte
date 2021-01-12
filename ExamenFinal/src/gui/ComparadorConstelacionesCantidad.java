package gui;

import java.util.Comparator;

import astronomy.Constellation;

public class ComparadorConstelacionesCantidad implements Comparator<Constellation> {

	@Override
	public int compare(Constellation o1, Constellation o2) {
		if(o1.getStars() == o2.getStars()) {
			return o1.getName().compareToIgnoreCase(o2.getName());
		} else {
			return o1.getStars() - o2.getStars();
		}
			
	}

	

}

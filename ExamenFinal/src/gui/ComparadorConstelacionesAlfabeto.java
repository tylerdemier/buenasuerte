package gui;

import java.util.Comparator;

import astronomy.Constellation;

public class ComparadorConstelacionesAlfabeto implements Comparator<Constellation> {

	// COMPARA SEGUN EL ORDEN ALFABETICO
	@Override
	public int compare(Constellation o1, Constellation o2) {
		if(o1.getName() == o2.getName()) {
			return o1.getStars() - o2.getStars();
		} else {
			return o1.getName().compareToIgnoreCase(o2.getName());
		}
			
	}

	

}

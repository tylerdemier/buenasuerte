package gui;

import java.util.Comparator;

import astronomy.Constellation;

public class ComparadorConstelacionesCantidad implements Comparator<Constellation> {

	// COMPARA SEGUN LA CANTIDAD DE ESTRELLAS
	@Override
	public int compare(Constellation o1, Constellation o2) {
		if(o1.getStars() == o2.getStars()) {
			// COMPARETOIGNORECASE PARA STRINGS
			return o1.getName().compareToIgnoreCase(o2.getName());
		} else {
			// RESTA PARA INTS
			return o1.getStars() - o2.getStars();
		}
			
	}

	

}

package gui;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.DefaultListModel;

import astronomy.Constellation;

/**
 * Esta clase define un modelo propio para la lista de constelaciones
 * que permite obtener facilmente la lista de constelaciones.
 * @author kalgan
 *
 */
public class ConstellationListModel extends DefaultListModel<Constellation> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Obtiene la lista de constelaciones asociada al modelo de datos
	 * @return la lista de constelaciones asociada al modelo de datos
	 */
	public List<Constellation> getConstellations() {
		List<Constellation> constellations = new ArrayList<>();
		
		Enumeration<Constellation> en = this.elements();
		while (en.hasMoreElements()) {
			constellations.add(en.nextElement());
		}
		
		return constellations;
	}

}

package astronomy;

/**
 * Esta clase representa la información de una de las
 * 88 constelaciones astronómicas en las que se divide
 * el cielo.
 */
public class Constellation {

	private String abbrv; // abreviatura de la constelación
	private String name; // nombre de la constelación
	private String origin; // origen histórico de la constelación
	private String meaning; // significado del nombre de la constelación
	private int stars; // número de estrellas en la constelación

	/**
	 * Construye una constelación con los datos indicados.
	 * @param abbrv abreviatura de la constelación
	 * @param name nombre de la constelación
	 * @param meaning significado del nombre de la constelación
	 * @param origin origen histórico de la constelación
	 * @param stars número de estrellas en la constelación
	 */
	public Constellation(String abbrv, String name, String meaning, String origin, int stars) {
		this.abbrv = abbrv;
		this.name = name;
		this.origin = origin;
		this.meaning = meaning;
		this.stars = stars;
	}
	
	/**
	 * Obtiene la abreviatura que identifica a la constelación
	 * @return la abreviatura que identifica a la constelación
	 */
	public String getAbbrv() {
		return abbrv;
	}
	
	/**
	 * Obtiene el nombre de la constelación
	 * @return nombre de la constelación
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Obtiene un texto que describe el origen de la constelación
	 * @return un texto que describe el origen de la constelación
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * Obtiene un texto con el significado del nombre de la constelación
	 * @return texto con el signficado del nombre de la constelación
	 */
	public String getMeaning() {
		return meaning;
	}
	
	/**
	 * Obtiene el número de estrellas que pertenecen a la constelación
	 * @return número de estrellas que pertenecen a la constelación
	 */
	public int getStars() {
		return stars;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Constellation))
			return false;
		
		Constellation c = (Constellation) o;
		return this.abbrv.equals(c.abbrv);
	}
}

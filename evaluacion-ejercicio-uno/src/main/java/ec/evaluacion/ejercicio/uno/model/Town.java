package ec.evaluacion.ejercicio.uno.model;

import java.util.List;


/**
 * Model representing a town.
 *
 * @author erodriguez on 2019/3/29.
 * @version 1.0
 * @since 1.0.0
 */
public class Town {
	// Name of the town
	private String name;
	
	public Town(String name) {
		this.name =name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}

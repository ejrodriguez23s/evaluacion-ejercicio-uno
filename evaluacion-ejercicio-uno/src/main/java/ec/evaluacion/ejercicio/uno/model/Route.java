package ec.evaluacion.ejercicio.uno.model;


/**
 * Model representing a Routes.
 *
 * @author erodriguez on 2019/3/29.
 * @version 1.0
 * @since 1.0.0
 */
public class Route {
	//Route
    private Town startTown;
    
    //Route
    private Town destTown;
    
	//distance
    private Integer distance;

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public Town getStartTown() {
		return startTown;
	}

	public void setStartTown(Town startTown) {
		this.startTown = startTown;
	}

	public Town getDestTown() {
		return destTown;
	}

	public void setDestTown(Town destTown) {
		this.destTown = destTown;
	}
 
}

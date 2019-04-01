package ec.evaluacion.ejercicio.uno.common;

/**
 * class TrainVO.
 *
 * @author erodriguez on 2019/3/29.
 * @version 1.0
 * @since 1.0.0
 */
public class TrainVO {
	
	private String accion;
	private Integer respuesta;
	private String error;
	
	public TrainVO(String accion, Integer respuesta ) {
		this.accion = accion;
		this.respuesta = respuesta;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public Integer getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(Integer respuesta) {
		this.respuesta = respuesta;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

}


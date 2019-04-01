package ec.evaluacion.ejercicio.uno;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import ec.evaluacion.ejercicio.uno.common.TrainException;

/**
 * Properties Class for kafka.
 *
 * @author erodriguez on 2019/03/15.
 * @version 1.0
 * @since 1.0.0
 */
@Lazy
@Component
@ConfigurationProperties(prefix = "spring.datastart")
public class TrainProperties {

    /**
     * data for train
     */
    private String datos;
    
    /**
     * 
     * get list of data for evaluation
     * @return
     * @author erodriguez on 2019/03/15.
     */
    public List<String> getListData() {
    	if(StringUtils.isEmpty(datos)) {
    		throw new TrainException("Empty initial data");
    	}
    	return Arrays.asList(datos.split(","));
    }

	/**
	 * @return the datos
	 */
	public String getDatos() {
		return datos;
	}

	/**
	 * @param datos the datos to set
	 */
	public void setDatos(String datos) {
		this.datos = datos;
	}
    
   
    
}

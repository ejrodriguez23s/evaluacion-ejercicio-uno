package ec.evaluacion.ejercicio.uno.ws;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import ec.evaluacion.ejercicio.uno.TrainProperties;
import ec.evaluacion.ejercicio.uno.common.TrainException;
import ec.evaluacion.ejercicio.uno.common.TrainVO;
import ec.evaluacion.ejercicio.uno.service.ITrainService;

/**
 * app controller class.
 *
 * @author erodriguez on 2019/3/29.
 * @version 1.0
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api")
public class TrainController {

	private static final Logger LOG = LoggerFactory.getLogger(TrainController.class);

	@Lazy
	@Autowired
	private ITrainService trainService;

	@Lazy
	@Autowired
	private TrainProperties trainProperties;

	private ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Service to obtain evaluation actions
	 * 
	 * @param classLoader
	 * @return
	 * @author erodriguez on 2019/3/29.
	 */
	@RequestMapping(value = "/getevaluacion", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getEvaluacion() {
		try {
			List<TrainVO> list = obtenerListAcciones(trainProperties.getListData());
			if (list.isEmpty()) {
				throw new TrainException("Empty list of actions");
			}
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			return new ResponseEntity<>(objectMapper.writeValueAsString(list), HttpStatus.OK);
		} catch (JsonProcessingException e) {
			LOG.error("Problems when parcelling data {} ", e);
			return new ResponseEntity<>("Problemas al obtener datos.", HttpStatus.NOT_FOUND);
		} catch (TrainException e) {
			LOG.error("{} ", e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Get list of actions
	 * 
	 * @param classLoader
	 * @return
	 * @author erodriguez on 2019/3/29.
	 */
	private List<TrainVO> obtenerListAcciones(List<String> listData) {
		List<TrainVO> list = new ArrayList<>();
		TrainVO TrainVO;
		try {
			// load initial data
			trainService.loadInitialData(listData);

			TrainVO = new TrainVO("1. The distance of the route A-B-C.",
					trainService.distance(Arrays.asList("A", "B", "C")));
			list.add(TrainVO);

			TrainVO = new TrainVO("2. The distance of the route A-D.", trainService.distance(Arrays.asList("A", "D")));
			list.add(TrainVO);

			TrainVO = new TrainVO("3. The distance of the route A-D-C.",
					trainService.distance(Arrays.asList("A", "D", "C")));
			list.add(TrainVO);

			TrainVO = new TrainVO("4. The distance of the route A-E-B-C-D.",
					trainService.distance(Arrays.asList("A", "E", "B", "C", "D")));
			list.add(TrainVO);

			try {
				trainService.distance(Arrays.asList("A", "E", "D"));
			} catch (TrainException e) {
				TrainVO = new TrainVO("5. The distance of the route A-E-D.", null);
				TrainVO.setError(e.getMessage());
				list.add(TrainVO);
			}

			TrainVO = new TrainVO("6. The number of trips starting at C and ending at C with a maximum of 3 stops.",
					trainService.countRoutesWithMaxHops("C", "C", 3));
			list.add(TrainVO);

			TrainVO = new TrainVO("7. The number of trips starting at A and ending at C with exactly 4 stops.",
					trainService.countRoutesWithHops("A", "C", 4));
			list.add(TrainVO);

			TrainVO = new TrainVO("8. The length of the shortest route (in terms of distance to travel) from A to C.",
					trainService.lengthOfShortestPathBetween("A", "C"));
			list.add(TrainVO);

			TrainVO = new TrainVO("9. The length of the shortest route (in terms of distance to travel) from B to B.",
					trainService.lengthOfShortestPathBetween("B", "B"));
			list.add(TrainVO);

			TrainVO = new TrainVO("10. The number of different routes from C to C with a distance of less than 30.",
					trainService.countRoutesWithMaxDistance("C", "C", 29));
			list.add(TrainVO);
		} catch (TrainException e) {
			LOG.error(e.getMessage());
		}
		return list;
	}

}

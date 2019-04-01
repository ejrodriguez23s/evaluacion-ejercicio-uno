package ec.evaluacion.ejercicio.uno.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ec.evaluacion.ejercicio.uno.common.Graph;
import ec.evaluacion.ejercicio.uno.common.TrainException;
import ec.evaluacion.ejercicio.uno.common.UtilData;
import ec.evaluacion.ejercicio.uno.model.Route;
import ec.evaluacion.ejercicio.uno.model.Town;

/**
 * train service class.
 *
 * @author erodriguez on 2019/3/29.
 * @version 1.0
 * @since 1.0.0
 */
@Lazy
@Service
public class TrainService implements ITrainService {

	private static final Logger LOG = LoggerFactory.getLogger(TrainService.class);
	
	private Graph<Town> graph = new Graph<>();
	
	private Map<String, Town> towns = new HashMap<>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ec.evaluacion.ejercicio.uno.service.ITrainService#loadInitialData(java.lang.
	 * String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void loadInitialData(List<String> listData) {
		try {
			if(listData.isEmpty()) {
				throw new TrainException("List of initial data is empty");
			}
			Map<String, Object> mapReturn = UtilData.loadData(listData);
			towns = (Map<String, Town>) mapReturn.get("town");
			Map<String, Set<Route>> mapRout = (Map<String, Set<Route>>) mapReturn.get("rout");

			towns.forEach((nameT, town) -> {
				graph.addNode(town);
			});
			
			mapRout.forEach((nameT, setR) -> {
				setR.forEach((route) -> {
					graph.addEdge(route.getStartTown(), route.getDestTown(), route.getDistance());
				});
			});
		} catch (TrainException e) {
			LOG.error(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ec.evaluacion.ejercicio.uno.service.ITrainService#shortestPathBetween(java.
	 * lang.String, java.lang.String)
	 */
	@Override
	public String shortestPathBetween(String start, String dest) {
		try {
			return graph.shortestPathBetween(towns.get(start), towns.get(dest)).toString();
		} catch (TrainException e) {
			throw new TrainException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ec.evaluacion.ejercicio.uno.service.ITrainService#lengthOfShortestPathBetween
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public int lengthOfShortestPathBetween(String start, String dest) throws TrainException {
		try {
			return graph.shortestPathBetween(towns.get(start), towns.get(dest)).distance();
		} catch (TrainException e) {
			throw new TrainException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ec.evaluacion.ejercicio.uno.service.ITrainService#distance(java.lang.String[]
	 * )
	 */
	@Override
	public int distance(List<String> townNames) throws TrainException {
		List<Town> townList = new ArrayList<Town>();
		try {
			townNames.forEach((name) -> {
				townList.add(towns.get(name));
			});
			return graph.distance(townList);
		} catch (TrainException e) {
			throw new TrainException(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ec.evaluacion.ejercicio.uno.service.ITrainService#countRoutesWithMaxHops(java
	 * .lang.String, java.lang.String, int)
	 */
	@Override
	public int countRoutesWithMaxHops(String start, String dest, int maxHops) {
		return graph.countRoutesWithMaxHops(towns.get(start), towns.get(dest), maxHops);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ec.evaluacion.ejercicio.uno.service.ITrainService#countRoutesWithHops(java.
	 * lang.String, java.lang.String, int)
	 */
	@Override
	public int countRoutesWithHops(String start, String dest, int hops) {
		return graph.countRoutesWithHops(towns.get(start), towns.get(dest), hops);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ec.evaluacion.ejercicio.uno.service.ITrainService#countRoutesWithMaxDistance(
	 * java.lang.String, java.lang.String, int)
	 */
	@Override
	public int countRoutesWithMaxDistance(String start, String dest, int maxDistance) {
		return graph.countRoutesWithMaxDistance(towns.get(start), towns.get(dest), maxDistance);
	}
}

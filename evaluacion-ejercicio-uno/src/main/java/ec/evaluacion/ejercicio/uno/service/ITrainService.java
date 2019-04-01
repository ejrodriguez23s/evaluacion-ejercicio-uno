package ec.evaluacion.ejercicio.uno.service;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ec.evaluacion.ejercicio.uno.common.TrainException;

/**
 * train service interface.
 *
 * @author erodriguez on 2019/3/29.
 * @version 1.0
 * @since 1.0.0
 */
@Lazy
@Service
public interface ITrainService {
	
	/**
	 * Load Initial Data
	 * 
	 * @param listData 
	 * @author erodriguez on 2019/3/29.
	 */
	public void loadInitialData(List<String> listData);
	

	/**
	 * shortest path
	 * 
	 * @param start name of start town
	 * @param dest  name of town which we want to reach.
	 * @return
	 * @author erodriguez on 2019/3/29.
	 */
	String shortestPathBetween(String start, String dest);

	/**
	 * shorter distance
	 *
	 * @param start name of start town.
	 * @param dest  name of town which we want to reach.
	 * @return shorter distance
	 * @throws TrainException if no route exists between the two towns.
	 * @author erodriguez on 2019/3/29.
	 */
	int lengthOfShortestPathBetween(String start, String dest) throws TrainException;

	/**
	 * Distance of route.
	 *
	 * @param townNames town names in the order as they should be visited.
	 * @return length of the route.
	 * @throws TrainException if no such route exists.
	 * @author erodriguez on 2019/3/29.
	 */
	int distance(List<String> townNames) throws TrainException;

	/**
	 * Count number of possible routes starting from start and ending at dest with a
	 * maximum number of {@code maxHops} .
	 *
	 * @param start   name of starting town of route.
	 * @param dest    name of destination town of route.
	 * @param maxHops maximum number of stops in route.
	 * @return number of possible routes.
	 * @author erodriguez on 2019/3/29.
	 */
	int countRoutesWithMaxHops(String start, String dest, int maxHops);

	/**
	 * Number of possible routes starting from start and ending at dest with a
	 * exactly {@code hops} number of stops.
	 *
	 * @param start name of starting town of route.
	 * @param dest  name of destination town of route.
	 * @param hops  number of stops in route.
	 * @return number of possible routes.
	 * @author erodriguez on 2019/3/29.
	 */
	int countRoutesWithHops(String start, String dest, int hops);

	/**
	 * Number of possible routes starting from start and ending at dest with a
	 * maximum distance of {@code maxDistance}.
	 *
	 * @param start       name of starting town of route.
	 * @param dest        name of destination town of route.
	 * @param maxDistance maximum allowed distance.
	 * @return number of possible routes.
	 */
	int countRoutesWithMaxDistance(String start, String dest, int maxDistance);

}

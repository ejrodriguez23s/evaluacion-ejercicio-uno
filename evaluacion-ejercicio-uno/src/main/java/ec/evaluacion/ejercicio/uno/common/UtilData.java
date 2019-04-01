package ec.evaluacion.ejercicio.uno.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ec.evaluacion.ejercicio.uno.model.Route;
import ec.evaluacion.ejercicio.uno.model.Town;


/**
 * Useful class for initial data.
 *
 * @author erodriguez on 2019/3/29.
 * @version 1.0
 * @since 1.0.0
 */
public  class UtilData {

	private static final Logger LOG = LoggerFactory.getLogger(UtilData.class);

	/**
	 * Initial data load
	 * 
	 * @param path file route
	 * @return list of towns
	 * @throws TrainException if there are problems loading the data
	 * @author erodriguez on 2019/3/29.
	 */
	public static  Map<String, Object> loadData(List<String> listData) throws TrainException {

			//returns list of towns
			return getTownsStructure(getMapTowns(listData));

	}

	/**
	 * Metodo que nos permite mapear objeto Town
	 * 
	 * @param mapTowns Map de
	 * @return
	 * @throws TrainException
	 * @author erodriguez on 2019/3/29.
	 */
	private static Map<String, Object> getTownsStructure(Map<String, Set<String>>  mapTowns) throws TrainException {
		
		Map<String, Object> mapReturn  = new HashMap<>();
		Map<String, Town> mapTown = new HashMap<>();
		Map<String, Set<Route>> mapRout = new HashMap<>();
		Set<Route> setRoutes = new HashSet<>();
		mapTowns.forEach((nameTown,setRoute)->{
			mapTown.put(nameTown, new Town(nameTown));
		});
		
		mapTowns.forEach((nameT,setR)->{
			setR.forEach((ro)->{
				Route route = new Route();
				String[] aInput = ro.split("");
				route.setDistance(Integer.parseInt(aInput[2]));
				route.setStartTown( mapTown.get(aInput[0]));
				route.setDestTown(mapTown.get(aInput[1]));
				setRoutes.add(route);
			});
			mapRout.put(nameT, setRoutes);
		});
		mapReturn.put("town", mapTown);
		mapReturn.put("rout", mapRout);
		return mapReturn;
	}

	/**
	 * Get train map.
	 * 
	 * @param in Scanner object
	 * @return MapTowns
	 * @throws TrainException
	 * @author erodriguez on 2019/3/29.
	 */
	private static Map<String, Set<String>> getMapTowns(List<String> listData) throws TrainException {
		Map<String, Set<String>> mapTowns = new HashMap<>();
		
		listData.forEach((input ->{

			if (input.length() < 3) {
				LOG.info("Datos incorrectos {}", input);
				throw new TrainException("Datos incorrectos");
			}else {
				String[] aInput = input.split("");
				String initialTown = aInput[0];
				if(mapTowns.containsKey(initialTown)) {
					mapTowns.get(initialTown).add(input);
				}else {
					Set<String> setRoutes  = new HashSet<>();
					setRoutes.add(input);
					mapTowns.put(initialTown, setRoutes);
				}
			}	
		}));
		return mapTowns;
	}
	
	/**
	 * Get Town set.
	 * 
	 * @param listT list towns
	 * @return MapTowns
	 * @author erodriguez on 2019/3/29.
	 */
	public static Set<Town> obtenerSetTown(List<Town> listT){
		Set<Town> setTowns = new HashSet<>();
		listT.forEach((town->{
			setTowns.add(town);
		}));
		
		return setTowns;
	}
}

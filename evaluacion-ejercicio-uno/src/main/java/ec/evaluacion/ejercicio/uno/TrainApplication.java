package ec.evaluacion.ejercicio.uno;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Lazy;

import ec.evaluacion.ejercicio.uno.common.TrainException;
import ec.evaluacion.ejercicio.uno.service.ITrainService;

/**
 * Main class to run spring boot app.
 *
 * @author erodriguez on 2019/3/22.
 * @version 1.0
 * @since 1.0.0
 */
@EnableConfigurationProperties({ TrainProperties.class })
@SpringBootApplication(scanBasePackages = { "ec.evaluacion.ejercicio" })
public class TrainApplication extends SpringBootServletInitializer implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(TrainApplication.class);

	@Lazy
	@Autowired
	private ITrainService trainService;

	@Lazy
	@Autowired
	private TrainProperties trainProperties;

	/**
	 * Main to run app.
	 *
	 * @param args args to pass app
	 */
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TrainApplication.class);
		app.setAdditionalProfiles("CONSOLA");
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		trainService.loadInitialData(trainProperties.getListData());

		LOG.info("1. The distance of the route A-B-C.-->>:  {} ", trainService.distance(Arrays.asList("A", "B", "C")));
		LOG.info("2. The distance of the route A-D.-->>:  {} ", trainService.distance(Arrays.asList("A", "D")));
		LOG.info("3. The distance of the route A-D-C.-->>:  {} ", trainService.distance(Arrays.asList("A", "D", "C")));
		LOG.info("4. The distance of the route A-E-B-C-D.-->>:  {} ",
				trainService.distance(Arrays.asList("A", "E", "B", "C", "D")));
		try {
			trainService.distance(Arrays.asList("A", "E", "D"));
		} catch (TrainException e) {
			LOG.info("5. The distance of the route A-E-D..-->>:  {} ", e.getMessage());
		}

		LOG.info("6. The number of trips starting at C and ending at C with a maximum of 3 stops.-->>:  {} ",
				trainService.countRoutesWithMaxHops("C", "C", 3));
		LOG.info("7. The number of trips starting at A and ending at C with exactly 4 stops.-->>:  {} ",
				trainService.countRoutesWithHops("A", "C", 4));
		LOG.info("8. The length of the shortest route (in terms of distance to travel) from A to C.-->>:  {} ",
				trainService.lengthOfShortestPathBetween("A", "C"));
		LOG.info("9. The length of the shortest route (in terms of distance to travel) from B to B.-->>:  {} ",
				trainService.lengthOfShortestPathBetween("B", "B"));
		LOG.info("10. The number of different routes from C to C with a distance of less than 30-->>:  {} ",
				trainService.countRoutesWithMaxDistance("C", "C", 29));

	}
}

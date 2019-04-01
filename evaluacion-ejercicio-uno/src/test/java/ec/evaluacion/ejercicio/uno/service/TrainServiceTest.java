package ec.evaluacion.ejercicio.uno.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;

import ec.evaluacion.ejercicio.uno.common.TrainException;

/**
 * train trainService class.
 *
 * @author erodriguez on 2019/3/29.
 * @version 1.0
 * @since 1.0.0
 */

@SpringBootTest(classes = { TrainService.class })
public class TrainServiceTest {

	@Lazy
	@Autowired
	private ITrainService trainService;
	

	@Test
	public void  initObjects() {
		trainService.loadInitialData(Arrays.asList("AB5","BC4","CD8","DC8","DE6","AD5","CE2","EB3","AE7"));
	}

	@Test
	public void testDistance1() {
		int ans = trainService.distance(Arrays.asList("A", "B", "C"));
		assertEquals(9, ans);
	}

	@Test
	public void testDistance2() {
		int ans = trainService.distance(Arrays.asList("A", "D"));
		assertEquals(5, ans);
	}

	@Test
	public void testDistance3() {
		int ans = trainService.distance(Arrays.asList("A", "D", "C"));
		assertEquals(13, ans);
	}

	@Test
	public void testDistance4() {
		int ans = trainService.distance(Arrays.asList("A", "E", "B", "C", "D"));
		assertEquals(22, ans);
	}

	@Test
	public void testDistanceException() {

		final TrainException thrown = assertThrows(TrainException.class, () -> {
			trainService.distance(Arrays.asList("A", "E", "D"));
		});
		assertEquals("Elementos incorrectos", thrown.getMessage());

	}

	@Test
	public void testCountRoutesWithMaxHops1() {
		int ans = trainService.countRoutesWithMaxHops("C", "C", 3);
		assertEquals(2, ans);
	}

	@Test
	public void testCountRoutesWithHops1() {
		int ans = trainService.countRoutesWithHops("A", "C", 4);
		assertEquals(3, ans);
	}

	@Test
	public void testLengthOfShortestPathBetween1() {
		int ans = trainService.lengthOfShortestPathBetween("A", "C");
		assertEquals(9, ans);
	}

	@Test
	public void testLengthOfShortestPathBetween2() {
		int ans = trainService.lengthOfShortestPathBetween("B", "B");
		assertEquals(9, ans);
	}

	@Test
	public void testCountRoutesWithMaxDistance1() {
		int ans = trainService.countRoutesWithMaxDistance("C", "C", 29);
		assertEquals(7, ans);
	}

}

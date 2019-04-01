package ec.evaluacion.ejercicio.uno.ws;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * test controller class.
 *
 * @author erodriguez on 2019/3/29.
 * @version 1.0
 * @since 1.0.0
 */

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TrainControllerTest {

	// bind the above RANDOM_PORT
	@LocalServerPort
	private Integer port;

	@Autowired
	private TestRestTemplate restTemplate;

	private final String CONTEX_PATH = "evaluacionWS/api/";

	@Test
	public void getEvaluacion() throws Exception {
		ResponseEntity<String> response = restTemplate.getForEntity(new URL(
				"http://localhost:".concat(port.toString()).concat("/").concat(CONTEX_PATH).concat("/getevaluacion"))
						.toString(),
				String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}

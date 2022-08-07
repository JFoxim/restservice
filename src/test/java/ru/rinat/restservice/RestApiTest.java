package ru.rinat.restservice;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.core.ParameterizedTypeReference;
import org.apache.naming.java.javaURLContextFactory;
import java.util.List;
import ru.rinat.restservice.dto.*;



@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestApiTest {
	
	private static final Logger logger = LoggerFactory.getLogger(RestApiTest.class);
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void restBasicAuthTest() throws Exception {
		
		ResponseEntity<List<UserDto>> responseEntity = restTemplate.withBasicAuth("user", "password")
				.exchange(new URL("http://localhost:" + port +"/users").toString(),
						HttpMethod.GET,
						null,
						new ParameterizedTypeReference<List<UserDto>>() {});
		
		List<UserDto> usersDtos = responseEntity.getBody();
		assertTrue(usersDtos.size() > 0);
		
	}
	

}

package ru.rinat.restservice;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.rinat.restservice.entity.Address;
import ru.rinat.restservice.service.AddressService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestApiTest {
	
	private static final Logger logger = LoggerFactory.getLogger(RestApiTest.class);

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@MockBean
	private AddressService addressService;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}

	//@Test
	public void restBasicAuthTest() throws Exception {
		
//		ResponseEntity<List<UserDto>> responseEntity = restTemplate.withBasicAuth("user", "password")
//				.exchange(new URL("http://localhost:" + port +"/users").toString(),
//						HttpMethod.GET,
//						null,
//						new ParameterizedTypeReference<List<UserDto>>() {});
//
//		List<UserDto> usersDtos = responseEntity.getBody();
//		assertTrue(usersDtos.size() > 0);
	}

	@Test
	@WithMockUser("spring")
	public void addressController() throws Exception {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();

		Address address = new Address("Томск", "Лесная", "2", 1);

		List<Address> addressList = new ArrayList<>();
		addressList.add(address);

		given(addressService.findAll()).willReturn(addressList);

		this.mockMvc.perform(get("/addresses"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].city", is(address.getCity())));
				//.andExpect((ResultMatcher) content().json("{'id':'4c7371e4-92cf-4b5c-b4cf-0ec940c7da23','city':'New York','street':'test','houseNumber':'12','flatNumber':34},{'id':'49c0a419-117c-48fc-80f4-bd3ce1b44a46','city':'London','street':'Hasming','houseNumber':'16','flatNumber':3},{'id':'c900ab31-aba1-4dea-b678-4645241542fd','city':'York','street':'test','houseNumber':'12','flatNumber':34}"));

	}

	//	@InjectMocks
//	AddressController addressController;
//
//	@Mock
//	AddressService addressService;
//

//	@Test
//	void addressTest() throws IOException {
//		MockHttpServletRequest request = new MockHttpServletRequest();
//		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//
//		when(addressService.create(any(Address.class))).thenReturn(null);
//
//		Address newAddress = new Address("Рязань", "Ленина", "10", 3);
//		ResponseEntity<Object> responseEntity = addressController.create(newAddress);
//
//		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
//		//assertTrue(responseEntity.getHeaders().getLocation().getPath().contains("/addresses"));
//	}
	

}

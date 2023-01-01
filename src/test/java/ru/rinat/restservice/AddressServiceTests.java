package ru.rinat.restservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import ru.rinat.restservice.entity.Address;
import ru.rinat.restservice.entity.Company;
import ru.rinat.restservice.exception.AddressNotFoundException;
import ru.rinat.restservice.exception.CompanyNotFoundExeption;
import ru.rinat.restservice.repository.AddressRepository;
import ru.rinat.restservice.service.AddressService;
import ru.rinat.restservice.service.CompanyService;

import java.util.UUID;

@SpringBootTest
class AddressServiceTests {
	
	@Autowired
	private AddressService addressService;

	@Autowired
	private AddressRepository addressRepository;
	
	private Address preparedData() {
		Address address;

		if (!addressService.existsByCityStreetHouseFlat("Москва", "Композиторская", "13", 2)) {
			Address newAddress = new Address("Москва", "Композиторская", "13", 2);
			address = addressService.create(newAddress);
		} else {
			address = addressService.findByCityStreetHouseFlat("Москва", "Композиторская", "13", 2).get();
		}
		return address;
	}

	@Test
	public void addressAddTest() {

		Address newAddress = new Address("Саратов", "Ленина", "5", 3);
		addressService.create(newAddress);
		Address selectedAddress = addressService.findByCityStreetHouseFlat("Саратов", "Ленина", "5", 3).get();

		assertEquals(newAddress, selectedAddress);
		
		addressService.delete(selectedAddress);
	}

	@Test
	public void addressUpdateTest() {
		Address createdAddress = preparedData();
		createdAddress.setHouseNumber("45");
		addressService.update(createdAddress);

		Address selectedAddress = addressService.findById(createdAddress.getId());

		assertEquals(createdAddress, selectedAddress);
		
		addressService.delete(selectedAddress);
	}

	@Test
	public void addressDeleteTest() {
		Address createdAddress = preparedData();
		addressService.delete(createdAddress);
		
		Exception exception = assertThrows(AddressNotFoundException.class, () -> {
			addressService.findById(createdAddress.getId());
	    });
	    
	    String expectedMessage = String.format("id - %s", createdAddress.getId());
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}

}

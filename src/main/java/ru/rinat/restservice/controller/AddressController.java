package ru.rinat.restservice.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ru.rinat.restservice.dto.AddressDto;
import ru.rinat.restservice.entity.Address;
import ru.rinat.restservice.service.AddressService;


@RestController
@RequestMapping("/addresses")
public class AddressController {
	final static Logger logger = LoggerFactory.getLogger(AddressController.class);
	
	@Autowired
	private ModelMapper modelMapper;
	
	private AddressService addressService;
	
	@Autowired
	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}
	
	@GetMapping
	public List<AddressDto> getAddreses() {
		logger.info("Get list address...");
		
		List<Address> companyDtos = addressService.findAll();
		return companyDtos.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody Address address) {
		logger.info(String.format("Create address with id %s", address.getId()));
		
		Address savedAddress = addressService.create(address);

		URI locationUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedAddress.getId()).toUri();

		return ResponseEntity.created(locationUri).build();
	}

	@GetMapping("/{id}")
	public AddressDto getById(@PathVariable UUID id) {
		return convertToDto(addressService.findById(id));
	}

	@PutMapping("/{id}")
	public String update(@RequestBody Address address, @PathVariable UUID id) {
		logger.info(String.format("Update address with id %s", address.getId()));
		
		checkIdAddress(address, id);

		return addressService.update(address);
	}

	private void checkIdAddress(Address address, UUID id) {	
		if (address.getId() == null) 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Не верно задан id для параметра Address");
		
		if (!address.getId().equals(id)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Параметр Id не соотвествует Id параметра Address");
		}
	}
	
	@DeleteMapping("/{id}")
	public String delete(@RequestBody Address address, @PathVariable UUID id) {
		logger.info(String.format("Delete address with id %s", address.getId()));
		
		checkIdAddress(address, id);
		
		return addressService.delete(address);
	}


	private AddressDto convertToDto(Address address) {
		AddressDto addressDto = modelMapper.map(address, AddressDto.class);
		return addressDto;
	}
}

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

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.rinat.restservice.dto.AddressDto;
import ru.rinat.restservice.dto.CompanyDto;
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
	@Tag(name = "Список адресов", description = "Позволяет получить список адресов")
	@ApiResponses(value = { @ApiResponse(content = { @Content(mediaType = "application/json",
	 array = @ArraySchema(schema = @Schema(implementation = AddressDto.class)))})})
	public List<AddressDto> getAddreses() {
		logger.info("Get list address...");
		
		List<Address> companyDtos = addressService.findAll();
		return companyDtos.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	@PostMapping
	@Tag(name = "Создать адрес", description = "Позволяет создать новый адрес")
	@ApiResponses(value = { @ApiResponse(description = "Адрес создан успешно", responseCode = "200", 
	 content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))), 
	@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true)))})
	public ResponseEntity<Object> create(@RequestBody Address address) {
		logger.info(String.format("Create address with id %s", address.getId()));
		
		Address savedAddress = addressService.create(address);

		URI locationUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedAddress.getId()).toUri();

		return ResponseEntity.created(locationUri).build();
	}

	@GetMapping("/{id}")
	@Tag(name = "Найти адрес по идентификатору", description = "Позволяет найти адрес по идентификатору")
	@ApiResponses(value = { @ApiResponse(description = "Адрес найден успешно", responseCode = "200", 
	 content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDto.class))), 
	@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true)))})
	public AddressDto getById(@PathVariable UUID id) {
		return convertToDto(addressService.findById(id));
	}

	@PutMapping("/{id}")
	@Tag(name = "Изменить адрес", description = "Позволяет изменить поля адреса")
	@ApiResponses(value = { @ApiResponse(description = "Адрес изменён успешно", responseCode = "200", 
	 content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))), 
	@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true)))})
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
	@Tag(name = "Удалить адрес", description = "Позволяет удалить адрес")
	@ApiResponses(value = { @ApiResponse(description = "Адрес удалён успешно", responseCode = "200", 
	 content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))), 
	@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true)))})
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

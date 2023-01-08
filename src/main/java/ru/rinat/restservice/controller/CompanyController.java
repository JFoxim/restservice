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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ru.rinat.restservice.dto.CompanyDto;
import ru.rinat.restservice.entity.Company;
import ru.rinat.restservice.service.CompanyService;

@RestController
@RequestMapping("/companies")
public class CompanyController {
	
	final static Logger logger = LoggerFactory.getLogger(CompanyController.class);
	
	@Autowired
	private ModelMapper modelMapper;
	
	private CompanyService companyService;
	
	@Autowired
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	@GetMapping
	@Tag(name = "Список компаний", description = "Позволяет получить список компаний")
	@ApiResponses(value = { @ApiResponse(content = { @Content(mediaType = "application/json",
	 array = @ArraySchema(schema = @Schema(implementation = CompanyDto.class)))})})
	public List<CompanyDto> getCompanies() {
		logger.info("Get list companies...");
		
		List<Company> companyDtos = companyService.findAll();
		return companyDtos.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	@PostMapping
	@Tag(name = "Создать компанию", description = "Позволяет создать новую компанию")
	@ApiResponses(value = { @ApiResponse(description = "Компания создана успешно", responseCode = "200", 
	 content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))), 
	@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true)))})
	public ResponseEntity<Object> create(@RequestBody Company company) {
		logger.info(String.format("Create company with id %s", company.getId()));
		
		Company savedCompany = companyService.create(company);

		URI locationUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedCompany.getId()).toUri();

		return ResponseEntity.created(locationUri).build();
	}

	@GetMapping("/{id}")
	@Tag(name = "Найти компанию по идентификатору", description = "Позволяет найти компанию по идентификатору")
	@ApiResponses(value = { @ApiResponse(description = "Компания найдена успешено", responseCode = "200", 
	 content = @Content(mediaType = "application/json", schema = @Schema(implementation = CompanyDto.class))), 
	@ApiResponse(responseCode = "404", description = "Компания не найдена", content = @Content),
	@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true)))})
	public CompanyDto getById(@PathVariable UUID id) {
		return convertToDto(companyService.findById(id));
	}

	@PutMapping("/{id}")
	@Tag(name = "Изменить компанию", description = "Позволяет изменить поля компании")
	@ApiResponses(value = { @ApiResponse(description = "Компания изменена успешно", responseCode = "200", 
	 content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))), 
	@ApiResponse(responseCode = "404", description = "Компания не найдена", content = @Content),
	@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true)))})
	public String update(@RequestBody Company company, @PathVariable UUID id) {
		logger.info(String.format("Update company with id %s", company.getId()));
		
		checkIdCompany(company, id);

		return companyService.update(company);
	}

	private void checkIdCompany(Company company, UUID id) {	
		if (company.getId() == null) 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Не верно задан id для параметра Company");
		
		if (!company.getId().equals(id)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Параметр Id не соотвествует Id параметра Company");
		}
	}
	
	@DeleteMapping("/{id}")
	@Tag(name = "Удалить компанию", description = "Позволяет удалить компанию")
	@ApiResponses(value = { @ApiResponse(description = "Компания удалена успешно", responseCode = "200", 
	 content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))), 
	@ApiResponse(responseCode = "404", description = "Компания не найдена", content = @Content),
	@ApiResponse(responseCode = "401", description = "Authentication Failure", content = @Content(schema = @Schema(hidden = true)))})
	public String delete(@RequestBody Company company, @PathVariable UUID id) {
		logger.info(String.format("Delete company with id %s", company.getId()));
		
		checkIdCompany(company, id);
		
		return companyService.delete(company);
	}


	private CompanyDto convertToDto(Company company) {
		return modelMapper.map(company, CompanyDto.class);
	}
}

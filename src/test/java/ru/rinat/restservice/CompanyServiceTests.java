package ru.rinat.restservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.rinat.restservice.entity.Company;
import ru.rinat.restservice.exception.CompanyNotFoundExeption;
import ru.rinat.restservice.service.CompanyService;

@SpringBootTest
class CompanyServiceTests {
	
	@Autowired
	private CompanyService companyService;
	
	private Company preparedData() {
		Company company;
		String newCompanyName = "Рога и Копыта"; 

		if (!companyService.existsByName(newCompanyName)) {
			Company newCompany = new Company(newCompanyName, "123456789");
			company = companyService.create(newCompany);
		} else {
			company = companyService.findByName(newCompanyName).get();
		}
		return company;
	}

	@Test
	public void companyAddTest() {
		Company newCompany = new Company("Эльбрус", "120056780");
		companyService.create(newCompany);
		Company selectedCompany = companyService.findByName("Эльбрус").get();

		assertEquals(newCompany, selectedCompany);
		
		companyService.delete(selectedCompany);
	}

	@Test
	public void companyUpdateTest() {
		Company createdCompany = preparedData();
		createdCompany.setPhone("4444444444");
		companyService.update(createdCompany);

		Company selectedCompany = companyService.findById(createdCompany.getId());

		assertEquals(createdCompany, selectedCompany);
		
		companyService.delete(selectedCompany);
	}

	@Test
	public void companyDeleteTest() {
		Company createdCompany = preparedData();
		companyService.delete(createdCompany);
		
		Exception exception = assertThrows(CompanyNotFoundExeption.class, () -> {
			companyService.findById(createdCompany.getId());
	    });
	    
	    String expectedMessage = String.format("id - %s", createdCompany.getId());
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}

}

package ru.rinat.restservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.rinat.restservice.entity.Company;
import ru.rinat.restservice.exception.CompanyNotFoundExeption;
import ru.rinat.restservice.repository.CompanyRepository;


@Service
public class CompanyService {
	
	private CompanyRepository companyRepository;
	
	@Autowired
    public void setCompanyRepository(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	public List<Company> findAll() {
		return companyRepository.findAll();
	}
	
	public Company findById(UUID id) {
		
		Optional<Company> companyOpt = companyRepository.findById(id);

		if (companyOpt.isEmpty()) {
			throw new CompanyNotFoundExeption("id - " + id);
		}
		return companyOpt.get();
	}
	
	public Company create(Company company) {
		return companyRepository.save(company);
	}
	
	public String update(Company company) {
		checkExistCompany(company);
		
		companyRepository.save(company);
		
		return 	String.format("Компания с id %s обновлёна", company.getId());
	}
	
	public Optional<Company> findByName(String name) {	
		return companyRepository.findByName(name);
	}
	
	public String delete(Company company) {
		companyRepository.delete(company);
		return String.format("Компания с id %s удалёна", company.getId());
	}
	
	public boolean existsByName(String name) {
		Optional<Company> companyOpt = companyRepository.findByName(name);

		return companyOpt.isPresent();
	}
	
	private void checkExistCompany(Company company) {
		if (!companyRepository.existsById(company.getId())) {
			throw new CompanyNotFoundExeption("id - " + company.getId());
		}
	}
}

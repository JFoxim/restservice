package ru.rinat.restservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CompanyNotFoundExeption extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public CompanyNotFoundExeption(String message) {
		super(message);
	}
}

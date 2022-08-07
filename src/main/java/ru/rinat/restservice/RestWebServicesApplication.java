package ru.rinat.restservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title="REST API example", version = "2.0", description = "REST API example"))
public class RestWebServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestWebServicesApplication.class, args);
	}

}

package com.example.restaurant.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
			.info(
				new Info()
					.title("Api de administracion de restaurante")
					.description("Documentacion interactiva de la API de restaurante")
					.version("1.0.0")
					.contact(new Contact()
						.name("Equipo de desarrollo")
						.email("equipo@desarrollo.com")
					)
			);
	}
}

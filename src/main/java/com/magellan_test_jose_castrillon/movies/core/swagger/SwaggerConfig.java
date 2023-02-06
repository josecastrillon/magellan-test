package com.magellan_test_jose_castrillon.movies.core.swagger;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	private ApiInfo apiInfo() {
		return new ApiInfo(
				"Magellan test", "Magellan test.", "1.0", "", new Contact("Jose Castrillon - Development",
						"https://www.linkedin.com/in/jose-castrillon/", "josecastrillon@gmail.com"),
				"", "", Collections.emptyList());
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.OAS_30)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.regex("/api.*"))
				.paths(PathSelectors.any()).build();
	}
}
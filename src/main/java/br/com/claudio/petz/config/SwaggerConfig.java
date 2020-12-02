package br.com.claudio.petz.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.claudio.petz"))
				.paths(regex("/petz.*"))
				.build()
				.apiInfo(metaInfo());
	}
	
	private ApiInfo metaInfo() {
		ApiInfo apiInfo = new ApiInfo(
				"APIs Petz",
				"API REST de cadastro de Clientes e Pets", 
				"1.0", 
				"Terms Of Service", 
				new Contact("Claudio Moreira", "https://github.com/claudiusmore", "claudius_more@hotmail.com"),
				"Apache License version 2.0",
				"http://www.apache.org/license.html", new ArrayList<VendorExtension>());
		return apiInfo;
	}
}

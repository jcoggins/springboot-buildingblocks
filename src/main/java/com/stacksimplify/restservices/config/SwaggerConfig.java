package com.stacksimplify.restservices.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
		
	@Bean  // All controllers and models
	public GroupedOpenApi api() {
		return GroupedOpenApi.builder()
				.group("default")
				.packagesToScan("com.stacksimplify.restservices")
				.pathsToMatch("/users/**")
				.build();
	}
	@Bean
	public OpenAPI UserMgmtServiceOpenAPI() {
      	 return new OpenAPI()
              .info(new Info().title("StackSimplify User Management Service")
              .description("This page lists all API's of User Management")
              .version("v2.0")
              .license(new License().name("License 2.0")
              .url("http://www.stacksimplify.com/license.html")))
              .externalDocs(new ExternalDocumentation()
              .description("Joe Coggins, stacksimplify@gmail.com")
              .url("https://www.stacksimplify.com"));
	}
	//Swagger Metadata: http://localhost:8080/v3/api-docs/com-controllers
	//Swagger UI URL: http://localhost:8080/swagger-ui/index.html
	
	// Documentation https://springdoc.org/#getting-started  swagger 3.0
	
}

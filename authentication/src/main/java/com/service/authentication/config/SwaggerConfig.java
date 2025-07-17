package com.service.authentication.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
          .info(new Info().title("Authentication Service API")
          .version("1.0")
          .description("API documentation for the Authentication Service"));
    }
}

// // This configuration class sets up Swagger for API documentation in the Authentication Service.
// // It defines an OpenAPI bean with basic information about the API, including title, version, and description.
// // Swagger will automatically generate documentation for the endpoints defined in the service.
// // The OpenAPI bean can be customized further to include more details like contact information, license, etc.
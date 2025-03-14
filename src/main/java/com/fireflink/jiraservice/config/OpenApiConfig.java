package com.fireflink.jiraservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        description = "Bearer Authentication using JWT Token"
)
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Jiraservice",
                version = "1.0",
                description = "API Documentation with Multiple Servers. Example: [Google](https://www.google.com)"
        )
)
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Jiraservice")
                        .version("1.0")
                        .description("API Documentation with Multiple Servers [Google](https://www.google.com)"))
                .addServersItem(new Server().url("http://localhost:8080").description("Local Server"))
                .addServersItem(new Server().url("https://localhost:8081").description("Development Server"))
                .addServersItem(new Server().url("https://localhost:8082").description("Staging Server"))
                .addServersItem(new Server().url("https://localhost:8083").description("Production Server"));
        // Remove the global security requirement:
        // .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
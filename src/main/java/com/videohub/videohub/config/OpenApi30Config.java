package com.videohub.videohub.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;


/*
 * This configuration class defines OpenAPI 3.0 related configurations for the application.
 * It uses annotations provided by Swagger to specify API metadata and security schemes.
 */

@Configuration
@OpenAPIDefinition(info = @Info(title = "VideoHub-BackEnd-Final-Project", version = "v1", contact = @Contact(name = "Amir Hossein Olya Nasab Narab", email = "amirholyanasabnarab@gmail.com")))
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApi30Config {
}

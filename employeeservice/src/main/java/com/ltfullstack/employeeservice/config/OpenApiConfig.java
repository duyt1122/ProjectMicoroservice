package com.ltfullstack.employeeservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Employee Api Specification ",
                description = "Api documentation for employee service",
                version = "1.0",
                contact = @Contact(
                        name = "Duy",
                        email = "duytrinh10622@gmail.com",
                        url = "duytrinh1122"
                ),
                license = @License(
                        name = "MIT license",
                        url = "http:/license"
                ),
                termsOfService = "http:duy1111"

        ),
        servers ={
                @Server(
                        description = "localhost",
                        url = "http://localhost:9002"
                ),
                @Server(
                        description = "Dev Evm",
                        url = "http://localhost:9002"
                )
        }

)
public class OpenApiConfig {
}

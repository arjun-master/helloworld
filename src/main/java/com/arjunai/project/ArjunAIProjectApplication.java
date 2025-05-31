package com.arjunai.project;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main application class for Arjun_AI_Project.
 * This Spring Boot application provides REST APIs for mathematical operations
 * and bill splitting functionality.
 *
 * @author Arjun Raju
 * @version 1.0
 * @since 2024-03-31
 */
@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = "com.arjunai.project")
@OpenAPIDefinition(
    info = @Info(
        title = "Arjun_AI_Project API",
        version = "1.0",
        description = "REST APIs for mathematical operations and bill splitting",
        contact = @Contact(
            name = "Arjun Raju",
            email = "arjun.raju@example.com",
            url = "https://github.com/arjun-master"
        ),
        license = @License(
            name = "MIT License",
            url = "https://opensource.org/licenses/MIT"
        )
    )
)
public class ArjunAIProjectApplication {
    
    /**
     * Main method to start the Spring Boot application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        log.info("Starting Arjun_AI_Project application...");
        SpringApplication.run(ArjunAIProjectApplication.class, args);
        log.info("Arjun_AI_Project application started successfully.");
    }
} 
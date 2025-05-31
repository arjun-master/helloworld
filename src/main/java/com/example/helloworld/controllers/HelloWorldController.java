package com.example.helloworld.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for basic Hello World functionality
 */
@RestController
@RequestMapping("/api")
public class HelloWorldController {
    
    private static final Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    /**
     * Simple endpoint that returns a greeting message
     * @return greeting message
     */
    @GetMapping("/hello")
    public String sayHello() {
        logger.info("Received request for hello endpoint");
        String greeting = "Hello, World!";
        logger.debug("Returning greeting: {}", greeting);
        return greeting;
    }
} 
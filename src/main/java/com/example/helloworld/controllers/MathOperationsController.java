package com.example.helloworld.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling basic mathematical operations
 */
@RestController
@RequestMapping("/api/math")
public class MathOperationsController {
    
    private static final Logger logger = LoggerFactory.getLogger(MathOperationsController.class);
    private static final String OPERATION_FORMAT = "%.2f %s %.2f = %.2f";

    /**
     * Adds two numbers
     * @param num1 first number
     * @param num2 second number
     * @return formatted result of addition
     */
    @GetMapping("/add/{num1}/{num2}")
    public ResponseEntity<String> add(@PathVariable double num1, @PathVariable double num2) {
        logger.info("Performing addition operation: {} + {}", num1, num2);
        double result = num1 + num2;
        logger.debug("Addition result: {}", result);
        return ResponseEntity.ok(String.format(OPERATION_FORMAT, num1, "+", num2, result));
    }

    /**
     * Subtracts two numbers
     * @param num1 first number
     * @param num2 second number
     * @return formatted result of subtraction
     */
    @GetMapping("/subtract/{num1}/{num2}")
    public ResponseEntity<String> subtract(@PathVariable double num1, @PathVariable double num2) {
        logger.info("Performing subtraction operation: {} - {}", num1, num2);
        double result = num1 - num2;
        logger.debug("Subtraction result: {}", result);
        return ResponseEntity.ok(String.format(OPERATION_FORMAT, num1, "-", num2, result));
    }

    /**
     * Multiplies two numbers
     * @param num1 first number
     * @param num2 second number
     * @return formatted result of multiplication
     */
    @GetMapping("/multiply/{num1}/{num2}")
    public ResponseEntity<String> multiply(@PathVariable double num1, @PathVariable double num2) {
        logger.info("Performing multiplication operation: {} * {}", num1, num2);
        double result = num1 * num2;
        logger.debug("Multiplication result: {}", result);
        return ResponseEntity.ok(String.format(OPERATION_FORMAT, num1, "*", num2, result));
    }

    /**
     * Divides two numbers
     * @param num1 first number (dividend)
     * @param num2 second number (divisor)
     * @return formatted result of division
     */
    @GetMapping("/divide/{num1}/{num2}")
    public ResponseEntity<String> divide(@PathVariable double num1, @PathVariable double num2) {
        logger.info("Performing division operation: {} / {}", num1, num2);
        
        if (num2 == 0) {
            logger.error("Division by zero attempted with dividend: {}", num1);
            return ResponseEntity.badRequest().body("Error: Division by zero is not allowed");
        }
        
        double result = num1 / num2;
        logger.debug("Division result: {}", result);
        return ResponseEntity.ok(String.format(OPERATION_FORMAT, num1, "/", num2, result));
    }
} 
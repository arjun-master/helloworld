package com.example.helloworld.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Math Operations", description = "APIs for basic mathematical operations")
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
    @Operation(
        summary = "Add two numbers",
        description = "Takes two numbers and returns their sum"
    )
    @ApiResponse(responseCode = "200", description = "Successfully calculated the sum")
    @GetMapping("/add/{num1}/{num2}")
    public ResponseEntity<String> add(
            @Parameter(description = "First number") @PathVariable double num1,
            @Parameter(description = "Second number") @PathVariable double num2) {
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
    @Operation(
        summary = "Subtract two numbers",
        description = "Takes two numbers and returns their difference"
    )
    @ApiResponse(responseCode = "200", description = "Successfully calculated the difference")
    @GetMapping("/subtract/{num1}/{num2}")
    public ResponseEntity<String> subtract(
            @Parameter(description = "First number (minuend)") @PathVariable double num1,
            @Parameter(description = "Second number (subtrahend)") @PathVariable double num2) {
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
    @Operation(
        summary = "Multiply two numbers",
        description = "Takes two numbers and returns their product"
    )
    @ApiResponse(responseCode = "200", description = "Successfully calculated the product")
    @GetMapping("/multiply/{num1}/{num2}")
    public ResponseEntity<String> multiply(
            @Parameter(description = "First number") @PathVariable double num1,
            @Parameter(description = "Second number") @PathVariable double num2) {
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
    @Operation(
        summary = "Divide two numbers",
        description = "Takes two numbers and returns their quotient. Returns an error if attempting to divide by zero."
    )
    @ApiResponse(responseCode = "200", description = "Successfully calculated the quotient")
    @ApiResponse(responseCode = "400", description = "Division by zero error")
    @GetMapping("/divide/{num1}/{num2}")
    public ResponseEntity<String> divide(
            @Parameter(description = "First number (dividend)") @PathVariable double num1,
            @Parameter(description = "Second number (divisor)") @PathVariable double num2) {
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
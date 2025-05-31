package com.arjunai.project.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling mathematical operations.
 * Provides REST endpoints for basic arithmetic operations.
 *
 * @author Arjun Raju
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/math")
@Tag(name = "Math Operations", description = "APIs for basic mathematical operations")
public class MathOperationsController {

    /**
     * Adds two numbers.
     *
     * @param num1 First number
     * @param num2 Second number
     * @return Sum of the two numbers
     */
    @Operation(summary = "Add two numbers",
            description = "Takes two numbers as input and returns their sum")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @GetMapping("/add/{num1}/{num2}")
    public ResponseEntity<Double> add(
            @Parameter(description = "First number") @PathVariable double num1,
            @Parameter(description = "Second number") @PathVariable double num2) {
        log.debug("Adding numbers: {} + {}", num1, num2);
        return ResponseEntity.ok(num1 + num2);
    }

    /**
     * Subtracts two numbers.
     *
     * @param num1 First number
     * @param num2 Second number
     * @return Difference of the two numbers
     */
    @Operation(summary = "Subtract two numbers",
            description = "Takes two numbers as input and returns their difference")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @GetMapping("/subtract/{num1}/{num2}")
    public ResponseEntity<Double> subtract(
            @Parameter(description = "First number") @PathVariable double num1,
            @Parameter(description = "Second number") @PathVariable double num2) {
        log.debug("Subtracting numbers: {} - {}", num1, num2);
        return ResponseEntity.ok(num1 - num2);
    }

    /**
     * Multiplies two numbers.
     *
     * @param num1 First number
     * @param num2 Second number
     * @return Product of the two numbers
     */
    @Operation(summary = "Multiply two numbers",
            description = "Takes two numbers as input and returns their product")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @GetMapping("/multiply/{num1}/{num2}")
    public ResponseEntity<Double> multiply(
            @Parameter(description = "First number") @PathVariable double num1,
            @Parameter(description = "Second number") @PathVariable double num2) {
        log.debug("Multiplying numbers: {} * {}", num1, num2);
        return ResponseEntity.ok(num1 * num2);
    }

    /**
     * Divides two numbers.
     *
     * @param num1 First number (dividend)
     * @param num2 Second number (divisor)
     * @return Quotient of the division
     * @throws IllegalArgumentException if divisor is zero
     */
    @Operation(summary = "Divide two numbers",
            description = "Takes two numbers as input and returns their quotient")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "400", description = "Division by zero")
    @GetMapping("/divide/{num1}/{num2}")
    public ResponseEntity<Double> divide(
            @Parameter(description = "First number (dividend)") @PathVariable double num1,
            @Parameter(description = "Second number (divisor)") @PathVariable double num2) {
        log.debug("Dividing numbers: {} / {}", num1, num2);
        if (num2 == 0) {
            log.error("Division by zero attempted");
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return ResponseEntity.ok(num1 / num2);
    }
} 
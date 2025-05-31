package com.example.helloworld.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller for handling bill splitting operations between people
 */
@Tag(name = "Bill Splitting", description = "APIs for splitting bills and payments between people")
@RestController
@RequestMapping("/api/split")
public class PaymentSplitController {
    
    private static final Logger logger = LoggerFactory.getLogger(PaymentSplitController.class);
    private static final String CURRENCY_FORMAT = "$%.2f";

    /**
     * Validates if the number of people is valid for splitting
     * @param numberOfPeople number of people to split between
     * @return true if valid, false otherwise
     */
    private boolean isValidNumberOfPeople(int numberOfPeople) {
        return numberOfPeople > 0;
    }

    /**
     * Calculates tip amount based on bill amount and tip percentage
     * @param billAmount the base bill amount
     * @param tipPercentage the tip percentage
     * @return the calculated tip amount
     */
    private double calculateTipAmount(double billAmount, double tipPercentage) {
        return billAmount * (tipPercentage / 100);
    }

    /**
     * Splits amount equally between people
     * @param totalAmount amount to split
     * @param numberOfPeople number of people to split between
     * @return formatted response with split details
     */
    @Operation(
        summary = "Split amount equally",
        description = "Splits a total amount equally between a specified number of people"
    )
    @ApiResponse(responseCode = "200", description = "Successfully calculated the split")
    @ApiResponse(responseCode = "400", description = "Invalid number of people")
    @GetMapping("/equal/{totalAmount}/{numberOfPeople}")
    public ResponseEntity<String> splitEqually(
            @Parameter(description = "Total amount to split") @PathVariable double totalAmount,
            @Parameter(description = "Number of people to split between") @PathVariable int numberOfPeople) {
        
        logger.info("Splitting {} equally between {} people", totalAmount, numberOfPeople);
        
        if (!isValidNumberOfPeople(numberOfPeople)) {
            logger.error("Invalid number of people: {}", numberOfPeople);
            return ResponseEntity.badRequest().body("Error: Number of people must be greater than 0");
        }

        double perPersonAmount = totalAmount / numberOfPeople;
        logger.debug("Per person amount calculated: {}", perPersonAmount);
        
        return ResponseEntity.ok(String.format(
            CURRENCY_FORMAT + " split between %d people = " + CURRENCY_FORMAT + " per person", 
            totalAmount, numberOfPeople, perPersonAmount));
    }

    /**
     * Splits bill with tip between people
     * @param billAmount base bill amount
     * @param tipPercentage tip percentage
     * @param numberOfPeople number of people to split between
     * @return formatted response with split details including tip
     */
    @Operation(
        summary = "Split bill with tip",
        description = "Splits a bill amount with tip between a specified number of people"
    )
    @ApiResponse(responseCode = "200", description = "Successfully calculated the split with tip")
    @ApiResponse(responseCode = "400", description = "Invalid number of people")
    @GetMapping("/with-tip/{billAmount}/{tipPercentage}/{numberOfPeople}")
    public ResponseEntity<String> splitWithTip(
            @Parameter(description = "Base bill amount") @PathVariable double billAmount,
            @Parameter(description = "Tip percentage") @PathVariable double tipPercentage,
            @Parameter(description = "Number of people") @PathVariable int numberOfPeople) {
        
        logger.info("Splitting bill {} with {}% tip between {} people", 
            billAmount, tipPercentage, numberOfPeople);
        
        if (!isValidNumberOfPeople(numberOfPeople)) {
            logger.error("Invalid number of people: {}", numberOfPeople);
            return ResponseEntity.badRequest().body("Error: Number of people must be greater than 0");
        }

        double tipAmount = calculateTipAmount(billAmount, tipPercentage);
        double totalAmount = billAmount + tipAmount;
        double perPersonAmount = totalAmount / numberOfPeople;

        logger.debug("Calculated values - Tip: {}, Total: {}, Per Person: {}", 
            tipAmount, totalAmount, perPersonAmount);

        StringBuilder result = new StringBuilder();
        result.append(String.format("Bill: " + CURRENCY_FORMAT + "\n", billAmount));
        result.append(String.format("Tip (%.1f%%): " + CURRENCY_FORMAT + "\n", tipPercentage, tipAmount));
        result.append(String.format("Total: " + CURRENCY_FORMAT + "\n", totalAmount));
        result.append(String.format("Per person: " + CURRENCY_FORMAT + "\n", perPersonAmount));

        return ResponseEntity.ok(result.toString());
    }

    /**
     * Splits amount unequally based on percentage shares
     * @param totalAmount total amount to split
     * @param shares map of person name to their percentage share
     * @return formatted response with individual split amounts
     */
    @Operation(
        summary = "Split amount unequally",
        description = "Splits a total amount unequally based on specified percentage shares"
    )
    @ApiResponse(responseCode = "200", description = "Successfully calculated the unequal split")
    @ApiResponse(responseCode = "400", description = "Invalid share percentages")
    @GetMapping("/unequal/{totalAmount}")
    public ResponseEntity<String> splitUnequally(
            @Parameter(description = "Total amount to split") @PathVariable double totalAmount,
            @Parameter(description = "Map of person names to their percentage shares") 
            @RequestParam Map<String, String> shares) {
        
        logger.info("Splitting {} unequally between {} people", totalAmount, shares.size());
        
        Map<String, Double> sharePercentages = shares.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> Double.parseDouble(entry.getValue())
            ));

        double totalShares = sharePercentages.values().stream().mapToDouble(Double::doubleValue).sum();
        if (Math.abs(totalShares - 100.0) > 0.01) {
            logger.error("Invalid share percentages. Total: {}", totalShares);
            return ResponseEntity.badRequest().body("Error: Total percentage shares must equal 100%");
        }

        StringBuilder result = new StringBuilder();
        result.append(String.format("Total amount: " + CURRENCY_FORMAT + "\nIndividual splits:\n", totalAmount));

        sharePercentages.forEach((person, share) -> {
            double amount = totalAmount * (share / 100);
            logger.debug("Calculated share for {}: {}%", person, amount);
            result.append(String.format("%s: " + CURRENCY_FORMAT + " (%.1f%%)\n", 
                person, amount, share));
        });

        return ResponseEntity.ok(result.toString());
    }

    /**
     * Splits bill by items with optional tip
     * @param items map of item names to prices
     * @param participants list of participants
     * @param tipPercentage optional tip percentage
     * @return formatted response with detailed bill breakdown
     */
    @Operation(
        summary = "Split bill by items",
        description = "Splits a bill by individual items with optional tip between participants"
    )
    @ApiResponse(responseCode = "200", description = "Successfully calculated the item-wise split")
    @ApiResponse(responseCode = "400", description = "Invalid number of participants")
    @GetMapping("/per-item")
    public ResponseEntity<String> splitByItems(
            @Parameter(description = "Map of item names to their prices") 
            @RequestParam Map<String, String> items,
            @Parameter(description = "List of participant names") 
            @RequestParam List<String> participants,
            @Parameter(description = "Optional tip percentage", example = "15.0") 
            @RequestParam(defaultValue = "0") double tipPercentage) {
        
        logger.info("Splitting bill with {} items between {} people with {}% tip", 
            items.size(), participants.size(), tipPercentage);
        
        if (!isValidNumberOfPeople(participants.size())) {
            logger.error("Invalid number of participants: {}", participants.size());
            return ResponseEntity.badRequest().body("Error: Number of participants must be greater than 0");
        }

        // Filter out non-item parameters from the request
        Map<String, Double> itemPrices = items.entrySet().stream()
            .filter(entry -> !entry.getKey().equals("participants") && !entry.getKey().equals("tipPercentage"))
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> Double.parseDouble(entry.getValue())
            ));

        double subtotal = itemPrices.values().stream().mapToDouble(Double::doubleValue).sum();
        double tipAmount = calculateTipAmount(subtotal, tipPercentage);
        double totalAmount = subtotal + tipAmount;
        double perPersonShare = totalAmount / participants.size();

        logger.debug("Calculated values - Subtotal: {}, Tip: {}, Total: {}, Per Person: {}", 
            subtotal, tipAmount, totalAmount, perPersonShare);

        StringBuilder result = new StringBuilder("Bill Breakdown:\n");
        itemPrices.forEach((item, price) -> 
            result.append(String.format("%s: " + CURRENCY_FORMAT + "\n", item, price)));
        
        result.append(String.format("\nSubtotal: " + CURRENCY_FORMAT + "\n", subtotal));
        result.append(String.format("Tip (%.1f%%): " + CURRENCY_FORMAT + "\n", tipPercentage, tipAmount));
        result.append(String.format("Total: " + CURRENCY_FORMAT + "\n", totalAmount));
        result.append(String.format("\nPer person (equal split): " + CURRENCY_FORMAT + "\n", perPersonShare));
        
        return ResponseEntity.ok(result.toString());
    }
} 
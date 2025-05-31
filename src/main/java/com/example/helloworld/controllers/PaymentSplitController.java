package com.example.helloworld.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

/**
 * Controller for handling bill splitting operations between people
 */
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
    @GetMapping("/equal/{totalAmount}/{numberOfPeople}")
    public ResponseEntity<String> splitEqually(
            @PathVariable double totalAmount, 
            @PathVariable int numberOfPeople) {
        
        logger.info("Splitting {} equally between {} people", totalAmount, numberOfPeople);
        
        if (!isValidNumberOfPeople(numberOfPeople)) {
            logger.error("Invalid number of people: {}", numberOfPeople);
            return ResponseEntity.badRequest().body("Error: Number of people must be greater than 0");
        }

        double perPersonAmount = totalAmount / numberOfPeople;
        logger.debug("Per person amount calculated: {}", perPersonAmount);
        
        return ResponseEntity.ok(String.format("Total amount: " + CURRENCY_FORMAT + 
            " split between %d people = " + CURRENCY_FORMAT + " per person", 
            totalAmount, numberOfPeople, perPersonAmount));
    }

    /**
     * Splits bill with tip between people
     * @param billAmount base bill amount
     * @param tipPercentage tip percentage
     * @param numberOfPeople number of people to split between
     * @return formatted response with split details including tip
     */
    @GetMapping("/with-tip/{billAmount}/{tipPercentage}/{numberOfPeople}")
    public ResponseEntity<String> splitWithTip(
            @PathVariable double billAmount,
            @PathVariable double tipPercentage,
            @PathVariable int numberOfPeople) {
        
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

        return ResponseEntity.ok(String.format(
            "Bill: " + CURRENCY_FORMAT + "\n" +
            "Tip (%.1f%%): " + CURRENCY_FORMAT + "\n" +
            "Total: " + CURRENCY_FORMAT + "\n" +
            "Per person: " + CURRENCY_FORMAT,
            billAmount, tipPercentage, tipAmount, totalAmount, perPersonAmount));
    }

    /**
     * Splits amount unequally based on percentage shares
     * @param totalAmount total amount to split
     * @param shares map of person name to their percentage share
     * @return formatted response with individual split amounts
     */
    @GetMapping("/unequal/{totalAmount}")
    public ResponseEntity<String> splitUnequally(
            @PathVariable double totalAmount,
            @RequestParam Map<String, Double> shares) {
        
        logger.info("Splitting {} unequally between {} people", totalAmount, shares.size());
        
        double totalShares = shares.values().stream().mapToDouble(Double::doubleValue).sum();
        if (Math.abs(totalShares - 100.0) > 0.01) {
            logger.error("Invalid share percentages. Total: {}", totalShares);
            return ResponseEntity.badRequest().body("Error: Total percentage shares must equal 100%");
        }

        StringBuilder result = new StringBuilder();
        result.append(String.format("Total amount: " + CURRENCY_FORMAT + "\nIndividual splits:\n", totalAmount));

        shares.forEach((person, share) -> {
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
    @GetMapping("/per-item")
    public ResponseEntity<String> splitByItems(
            @RequestParam Map<String, Double> items,
            @RequestParam List<String> participants,
            @RequestParam(defaultValue = "0") double tipPercentage) {
        
        logger.info("Splitting bill with {} items between {} people with {}% tip", 
            items.size(), participants.size(), tipPercentage);
        
        if (!isValidNumberOfPeople(participants.size())) {
            logger.error("Invalid number of participants: {}", participants.size());
            return ResponseEntity.badRequest().body("Error: Number of participants must be greater than 0");
        }

        double subtotal = items.values().stream().mapToDouble(Double::doubleValue).sum();
        double tipAmount = calculateTipAmount(subtotal, tipPercentage);
        double totalAmount = subtotal + tipAmount;
        double perPersonShare = totalAmount / participants.size();

        logger.debug("Calculated values - Subtotal: {}, Tip: {}, Total: {}, Per Person: {}", 
            subtotal, tipAmount, totalAmount, perPersonShare);

        StringBuilder result = new StringBuilder("Bill Breakdown:\n");
        items.forEach((item, price) -> 
            result.append(String.format("%s: " + CURRENCY_FORMAT + "\n", item, price)));
        
        result.append(String.format("\nSubtotal: " + CURRENCY_FORMAT + "\n", subtotal));
        result.append(String.format("Tip (%.1f%%): " + CURRENCY_FORMAT + "\n", tipPercentage, tipAmount));
        result.append(String.format("Total: " + CURRENCY_FORMAT + "\n", totalAmount));
        result.append(String.format("\nPer person (equal split): " + CURRENCY_FORMAT + "\n", perPersonShare));
        
        return ResponseEntity.ok(result.toString());
    }
} 
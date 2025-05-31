package com.example.helloworld.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.containsString;

@WebMvcTest(MathOperationsController.class)
class MathOperationsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("Addition Tests")
    class AdditionTests {
        @Test
        @DisplayName("Should add two positive numbers correctly")
        void shouldAddTwoPositiveNumbers() throws Exception {
            mockMvc.perform(get("/api/math/add/5/3"))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("5.00 + 3.00 = 8.00")));
        }

        @Test
        @DisplayName("Should add positive and negative numbers correctly")
        void shouldAddPositiveAndNegativeNumbers() throws Exception {
            mockMvc.perform(get("/api/math/add/5/-3"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("5.00 + -3.00 = 2.00")));
        }

        @Test
        @DisplayName("Should add decimal numbers correctly")
        void shouldAddDecimalNumbers() throws Exception {
            mockMvc.perform(get("/api/math/add/5.5/3.3"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("5.50 + 3.30 = 8.80")));
        }
    }

    @Nested
    @DisplayName("Subtraction Tests")
    class SubtractionTests {
        @Test
        @DisplayName("Should subtract two positive numbers correctly")
        void shouldSubtractTwoPositiveNumbers() throws Exception {
            mockMvc.perform(get("/api/math/subtract/8/3"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("8.00 - 3.00 = 5.00")));
        }

        @Test
        @DisplayName("Should handle negative result correctly")
        void shouldHandleNegativeResult() throws Exception {
            mockMvc.perform(get("/api/math/subtract/3/8"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("3.00 - 8.00 = -5.00")));
        }

        @Test
        @DisplayName("Should subtract decimal numbers correctly")
        void shouldSubtractDecimalNumbers() throws Exception {
            mockMvc.perform(get("/api/math/subtract/5.5/3.3"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("5.50 - 3.30 = 2.20")));
        }
    }

    @Nested
    @DisplayName("Multiplication Tests")
    class MultiplicationTests {
        @Test
        @DisplayName("Should multiply two positive numbers correctly")
        void shouldMultiplyTwoPositiveNumbers() throws Exception {
            mockMvc.perform(get("/api/math/multiply/4/3"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("4.00 * 3.00 = 12.00")));
        }

        @Test
        @DisplayName("Should handle multiplication by zero")
        void shouldHandleMultiplicationByZero() throws Exception {
            mockMvc.perform(get("/api/math/multiply/4/0"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("4.00 * 0.00 = 0.00")));
        }

        @Test
        @DisplayName("Should handle multiplication of negative numbers")
        void shouldHandleNegativeNumbers() throws Exception {
            mockMvc.perform(get("/api/math/multiply/-4/-3"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("-4.00 * -3.00 = 12.00")));
        }

        @Test
        @DisplayName("Should multiply decimal numbers correctly")
        void shouldMultiplyDecimalNumbers() throws Exception {
            mockMvc.perform(get("/api/math/multiply/2.5/2.0"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("2.50 * 2.00 = 5.00")));
        }
    }

    @Nested
    @DisplayName("Division Tests")
    class DivisionTests {
        @Test
        @DisplayName("Should divide two positive numbers correctly")
        void shouldDivideTwoPositiveNumbers() throws Exception {
            mockMvc.perform(get("/api/math/divide/6/2"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("6.00 / 2.00 = 3.00")));
        }

        @Test
        @DisplayName("Should handle division by zero")
        void shouldHandleDivisionByZero() throws Exception {
            mockMvc.perform(get("/api/math/divide/4/0"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString("Division by zero is not allowed")));
        }

        @Test
        @DisplayName("Should handle division of negative numbers")
        void shouldHandleNegativeNumbers() throws Exception {
            mockMvc.perform(get("/api/math/divide/-6/2"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("-6.00 / 2.00 = -3.00")));
        }

        @Test
        @DisplayName("Should handle decimal division correctly")
        void shouldHandleDecimalDivision() throws Exception {
            mockMvc.perform(get("/api/math/divide/5/2"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("5.00 / 2.00 = 2.50")));
        }
    }
} 
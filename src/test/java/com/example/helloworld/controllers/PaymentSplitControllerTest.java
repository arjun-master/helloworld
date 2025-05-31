package com.example.helloworld.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.containsString;

@WebMvcTest(PaymentSplitController.class)
class PaymentSplitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("Equal Split Tests")
    class EqualSplitTests {
        @Test
        @DisplayName("Should split amount equally between people")
        void shouldSplitAmountEqually() throws Exception {
            mockMvc.perform(get("/api/split/equal/100/4"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("$100.00 split between 4 people = $25.00 per person")));
        }

        @Test
        @DisplayName("Should handle decimal amounts correctly")
        void shouldHandleDecimalAmounts() throws Exception {
            mockMvc.perform(get("/api/split/equal/99.99/3"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("$99.99 split between 3 people = $33.33 per person")));
        }

        @Test
        @DisplayName("Should reject invalid number of people")
        void shouldRejectInvalidNumberOfPeople() throws Exception {
            mockMvc.perform(get("/api/split/equal/100/0"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString("Number of people must be greater than 0")));
        }
    }

    @Nested
    @DisplayName("Split with Tip Tests")
    class SplitWithTipTests {
        @Test
        @DisplayName("Should split bill with tip correctly")
        void shouldSplitBillWithTip() throws Exception {
            mockMvc.perform(get("/api/split/with-tip/100/15/4"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Bill: $100.00")))
                    .andExpect(content().string(containsString("Tip (15.0%): $15.00")))
                    .andExpect(content().string(containsString("Total: $115.00")))
                    .andExpect(content().string(containsString("Per person: $28.75")));
        }

        @Test
        @DisplayName("Should handle zero tip percentage")
        void shouldHandleZeroTip() throws Exception {
            mockMvc.perform(get("/api/split/with-tip/100/0/4"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Tip (0.0%): $0.00")))
                    .andExpect(content().string(containsString("Total: $100.00")))
                    .andExpect(content().string(containsString("Per person: $25.00")));
        }

        @Test
        @DisplayName("Should reject invalid number of people")
        void shouldRejectInvalidNumberOfPeople() throws Exception {
            mockMvc.perform(get("/api/split/with-tip/100/15/0"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString("Number of people must be greater than 0")));
        }
    }

    @Nested
    @DisplayName("Unequal Split Tests")
    class UnequalSplitTests {
        @Test
        @DisplayName("Should split amount unequally based on percentages")
        void shouldSplitAmountUnequally() throws Exception {
            mockMvc.perform(get("/api/split/unequal/100")
                    .param("Alice", "40")
                    .param("Bob", "35")
                    .param("Charlie", "25"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Alice: $40.00 (40.0%)")))
                    .andExpect(content().string(containsString("Bob: $35.00 (35.0%)")))
                    .andExpect(content().string(containsString("Charlie: $25.00 (25.0%)")));
        }

        @Test
        @DisplayName("Should reject shares not totaling 100%")
        void shouldRejectInvalidShares() throws Exception {
            mockMvc.perform(get("/api/split/unequal/100")
                    .param("Alice", "40")
                    .param("Bob", "35"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString("Total percentage shares must equal 100%")));
        }

        @Test
        @DisplayName("Should handle decimal percentages correctly")
        void shouldHandleDecimalPercentages() throws Exception {
            mockMvc.perform(get("/api/split/unequal/100")
                    .param("Alice", "33.33")
                    .param("Bob", "33.33")
                    .param("Charlie", "33.34"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Alice: $33.33 (33.3%)")))
                    .andExpect(content().string(containsString("Charlie: $33.34 (33.3%)")));
        }
    }

    @Nested
    @DisplayName("Split by Items Tests")
    class SplitByItemsTests {
        @Test
        @DisplayName("Should split items equally with tip")
        void shouldSplitItemsWithTip() throws Exception {
            mockMvc.perform(get("/api/split/per-item")
                    .param("Pizza", "20")
                    .param("Drinks", "15")
                    .param("participants", "Alice", "Bob")
                    .param("tipPercentage", "15"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Pizza: $20.00")))
                    .andExpect(content().string(containsString("Drinks: $15.00")))
                    .andExpect(content().string(containsString("Subtotal: $35.00")))
                    .andExpect(content().string(containsString("Tip (15.0%): $5.25")))
                    .andExpect(content().string(containsString("Total: $40.25")))
                    .andExpect(content().string(containsString("Per person (equal split): $20.13")));
        }

        @Test
        @DisplayName("Should handle empty tip percentage")
        void shouldHandleEmptyTip() throws Exception {
            mockMvc.perform(get("/api/split/per-item")
                    .param("Pizza", "20")
                    .param("participants", "Alice", "Bob"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Tip (0.0%): $0.00")));
        }

        @Test
        @DisplayName("Should reject empty participants list")
        void shouldRejectEmptyParticipants() throws Exception {
            mockMvc.perform(get("/api/split/per-item")
                    .param("Pizza", "20"))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Should handle multiple items correctly")
        void shouldHandleMultipleItems() throws Exception {
            mockMvc.perform(get("/api/split/per-item")
                    .param("Pizza", "20")
                    .param("Drinks", "15")
                    .param("Dessert", "10")
                    .param("participants", "Alice", "Bob", "Charlie"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Subtotal: $45.00")))
                    .andExpect(content().string(containsString("Per person (equal split): $15.00")));
        }
    }
} 
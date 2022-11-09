package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.prophecies.api.propheciesapi.model.Cart;

@Tag("Model-tier")
public class CartTest {
    
    @Test
    public void testCtor() {
        // Setup
        String expected_username = "TestName";
        Cart cart = new Cart(expected_username, null);

        // Analyze
        assertEquals("TestName", cart.getUsername());
    }
    @Test
    public void testUsername() {
        // Setup
        String expected_username = "TestName";
        Cart cart = new Cart(expected_username, null);

        // Analyze
        assertEquals("TestName", cart.getUsername());
    }
    @Test
    public void testProphecies() {
        // Setup
        String expected_username = "TestName";
        Cart cart = new Cart(expected_username, null);

        // Analyze
        assertEquals(null, cart.getProphecies());
    }
    



}

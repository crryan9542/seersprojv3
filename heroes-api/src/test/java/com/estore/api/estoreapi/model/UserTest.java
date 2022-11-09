package com.estore.api.estoreapi.model;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.prophecies.api.propheciesapi.model.User;
@Tag("Model-tier")
public class UserTest {
    @Test
    public void testCtor() {
        // Setup
        String expected_username = "TestName";
        String expected_password = "TestPassword";
        User user = new User(expected_username, expected_password);

        // Analyze
        assertEquals("TestName", user.getUsername());
        assertEquals("TestPassword", user.getPassword());
    }
    @Test
    public void testUsername() {
        // Setup
        String expected_username = "TestName";
        String expected_password = "TestPassword";
        User user = new User(expected_username, expected_password);

        // Analyze
        assertEquals("TestName", user.getUsername());
    }
    @Test
    public void testPassword() {
        // Setup
        String expected_username = "TestName";
        String expected_password = "TestPassword";
        User user = new User(expected_username, expected_password);

        // Analyze
        assertEquals("TestPassword", user.getPassword());
    }
}

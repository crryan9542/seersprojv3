package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.prophecies.api.propheciesapi.model.Prophecy;

import org.junit.jupiter.api.Tag;

/**
 * Test the Prophecy class
 * 
 * @author Christian Ryan (crryan9542) and Chanel Cheng (cfc6715)
 */
@Tag("Model-tier")
public class ProphecyTest {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 1;
        String expected_name = "TestName";
        String expected_desc = "TestDesc";
        double expected_price = 1.0;
        int expected_quant = 1;

        // Invoke
        Prophecy prophecy = new Prophecy(expected_id, expected_name, expected_desc, expected_price, expected_quant);

        // Analyze
        assertEquals(1, prophecy.getId());
        assertEquals("TestName", prophecy.getName());
        assertEquals("TestDesc", prophecy.getDescription());
        assertEquals(1.0, prophecy.getPrice());
        assertEquals(1, prophecy.getQuantity());
    }
    @Test
    public void testID() {
        // Setup
        int expected_id = 1;
        String expected_name = "TestName";
        String expected_desc = "TestDesc";
        double expected_price = 1.0;
        int expected_quant = 1;

        // Invoke
        Prophecy prophecy = new Prophecy(expected_id, expected_name, expected_desc, expected_price, expected_quant);

        // Analyze
        assertEquals(1, prophecy.getId());
    }



    @Test
    public void TestName() {
        Prophecy prophecy = new Prophecy(1, "horse", "TestDesc", 1.0, 1);
        String expected_name = "TestName";

        prophecy.setName(expected_name);
        
        assertEquals(expected_name, prophecy.getName());
    }

    @Test
    public void TestDesc() {
        Prophecy prophecy = new Prophecy(1, "TestName", "horse", 1.0, 1);
        String expected_desc = "TestDesc";

        prophecy.setDescription(expected_desc);
        
        assertEquals(expected_desc, prophecy.getDescription());
    }

    @Test
    public void TestPrice() {
        Prophecy prophecy = new Prophecy(1, "TestName", "TestDesc", 5.0, 1);
        double expected_price = 1.0;
        double delta = 0.001;

        prophecy.setPrice(expected_price);
        
        assertEquals(expected_price, prophecy.getPrice(), delta);
    }

    @Test
    public void TestQuantity() {
        Prophecy prophecy = new Prophecy(1, "TestName", "TestDesc", 1.0, 2);
        int expected_quant = 1;

        prophecy.setQuantity(expected_quant);
        
        assertEquals(expected_quant, prophecy.getQuantity());
    }

    @Test
    public void testToString()  {
        Prophecy prophecy = new Prophecy(1, "TestName", "TestDesc", 1.00, 1);
        assertEquals("Prophecy [id=1, name=TestName, description=TestDesc, price=1.00, quantity=1]",
               
                prophecy.toString());
    }
}
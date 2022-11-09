package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prophecies.api.propheciesapi.model.Cart;
import com.prophecies.api.propheciesapi.model.Prophecy;
import com.prophecies.api.propheciesapi.persistence.CartFileDAO;

@Tag("Persistence-tier")
public class CartFileDAOTest {
    CartFileDAO cartFileDAO;
    Cart[] testCarts;
    ObjectMapper mockObjectMapper;


    @BeforeEach
    public void setupCartFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        Prophecy[] prophecy = new Prophecy[1];
        prophecy[0] = new Prophecy(0, "TestName0", "TestDesc0", 0.00, 0);
        testCarts = new Cart[3];
        testCarts[0] = new Cart("TestName1", prophecy );
        testCarts[1] = new Cart("TestName2", prophecy);
        testCarts[2] = new Cart("TestName3", prophecy);
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Cart[].class))
                .thenReturn(testCarts);
        cartFileDAO = new CartFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetCarts() throws IOException  {
        Cart[] Carts = cartFileDAO.getCarts();
        assertNotNull(Carts);
        assertEquals(3, Carts.length);
        assertEquals("TestName1", Carts[0].getUsername());
        assertEquals("TestName2", Carts[1].getUsername());
        assertEquals("TestName3", Carts[2].getUsername());
    }
    @Test
    public void testGetCartsIOException() throws IOException  {
        doThrow(IOException.class).when(mockObjectMapper)
            .readValue(new File("doesnt_matter.txt"),Cart[].class);
        assertThrows(IOException.class, () -> cartFileDAO.getCarts());
    }
    @Test
    public void testGetCart() throws IOException  {
        Cart Cart = cartFileDAO.getCart("TestName1");
        assertNotNull(Cart);
        assertEquals("TestName1", Cart.getUsername());
    }
    @Test
    public void testGetCartIOException() throws IOException  {
        doThrow(IOException.class).when(mockObjectMapper)
            .readValue(new File("doesnt_matter.txt"),Cart[].class);
        assertThrows(IOException.class, () -> cartFileDAO.getCart("TestName1"));
    }
    @Test
    public void testGetCartNull() throws IOException  {
        Cart Cart = cartFileDAO.getCart("TestName4");
        assertNull(Cart);
    }
    @Test
    public void testFindCarts() throws IOException  {
        Cart[] Carts = cartFileDAO.findCarts("TestName1");
        assertNotNull(Carts);
        assertEquals(1, Carts.length);
        assertEquals("TestName1", Carts[0].getUsername());
    }
    @Test
    public void testFindCartsIOException() throws IOException  {
        doThrow(IOException.class).when(mockObjectMapper)
            .readValue(new File("doesnt_matter.txt"),Cart[].class);
        assertThrows(IOException.class, () -> cartFileDAO.findCarts("TestName1"));
    }
    @Test
    public void testFindCartsNull() throws IOException  {
        Cart[] Carts = cartFileDAO.findCarts("TestName4");
        assertNull(Carts);
    }
    @Test
    public void testcreateCart() throws IOException  {
        Prophecy[] prophecy = new Prophecy[1];
        prophecy[0] = new Prophecy(0, "TestName0", "TestDesc0", 0.00, 0);
        Cart Cart = new Cart("TestName4", prophecy);
        assertDoesNotThrow(() -> cartFileDAO.createCart(Cart));
    }
    @Test
    public void testcreateCartIOException() throws IOException  {
        doThrow(IOException.class).when(mockObjectMapper)
            .writeValue(new File("doesnt_matter.txt"),testCarts);
        Prophecy[] prophecy = new Prophecy[1];
        prophecy[0] = new Prophecy(0, "TestName0", "TestDesc0", 0.00, 0);
        Cart Cart = new Cart("TestName4", prophecy);
        assertThrows(IOException.class, () -> cartFileDAO.createCart(Cart));
    }
    @Test
    public void testupdateCart() throws IOException  {
        Prophecy[] prophecy = new Prophecy[1];
        prophecy[0] = new Prophecy(0, "TestName0", "TestDesc0", 0.00, 0);
        Cart Cart = new Cart("TestName1", prophecy);
        assertDoesNotThrow(() -> cartFileDAO.updateCart(Cart));
    }
    @Test
    public void testupdateCartIOException() throws IOException  {
        doThrow(IOException.class).when(mockObjectMapper)
            .writeValue(new File("doesnt_matter.txt"),testCarts);
        Prophecy[] prophecy = new Prophecy[1];
        prophecy[0] = new Prophecy(0, "TestName0", "TestDesc0", 0.00, 0);
        Cart Cart = new Cart("TestName1", prophecy);
        assertThrows(IOException.class, () -> cartFileDAO.updateCart(Cart));
    }
    @Test
    public void testdeleteCart() throws IOException  {
        assertDoesNotThrow(() -> cartFileDAO.deleteCart("TestName1"));
    }
    @Test
    public void testdeleteCartIOException() throws IOException  {
        doThrow(IOException.class).when(mockObjectMapper)
            .writeValue(new File("doesnt_matter.txt"),testCarts);
        assertThrows(IOException.class, () -> cartFileDAO.deleteCart("TestName1"));
    }
    
}   


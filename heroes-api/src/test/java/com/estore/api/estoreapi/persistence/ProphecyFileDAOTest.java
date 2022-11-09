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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prophecies.api.propheciesapi.persistence.ProphecyFileDAO;
import com.prophecies.api.propheciesapi.model.Prophecy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Prophecy File DAO class
 * 
 * @author Chanel Cheng (cfc6715)
 */
@Tag("Persistence-tier")
public class ProphecyFileDAOTest {
    ProphecyFileDAO prophecyFileDAO;
    Prophecy[] testProphecies;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupProphecyFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testProphecies = new Prophecy[3];
        testProphecies[0] = new Prophecy(1, "TestName1", "TestDesc1", 1.00, 1);
        testProphecies[1] = new Prophecy(2, "TestName2", "TestDesc2", 1.00, 5);
        testProphecies[2] = new Prophecy(3, "TestName3", "TestDesc3", 1.00, 10);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the Prophecy array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Prophecy[].class))
                .thenReturn(testProphecies);
        prophecyFileDAO = new ProphecyFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetProphecies()  {
        // Invoke
        Prophecy[] Prophecies = prophecyFileDAO.getProphecies();

        // Analyze
        assertEquals(Prophecies.length,testProphecies.length);
        for (int i = 0; i < testProphecies.length;++i)
            assertEquals(Prophecies[i],testProphecies[i]);
    }

    @Test
    public void testFindProphecies() {
        // Invoke
        Prophecy[] Prophecies = prophecyFileDAO.findProphecies("Test");

        // Analyze
        assertEquals(Prophecies.length,3);
        assertEquals(Prophecies[0],testProphecies[0]);
        assertEquals(Prophecies[1],testProphecies[1]);
        assertEquals(Prophecies[2],testProphecies[2]);
    }

    @Test
    public void testGetProphecy() {
        // Invoke
        Prophecy prophecy = prophecyFileDAO.getProphecy(1);

        // Analzye
        assertEquals(prophecy,testProphecies[0]);
    }


    @Test
    public void testDeleteProphecy() throws IOException {
        // Setup
        Prophecy[] expectedProphecies = new Prophecy[2];
        expectedProphecies[0] = new Prophecy(2, "TestName2", "TestDesc2", 1.00, 5);
        expectedProphecies[1] = new Prophecy(3, "TestName3", "TestDesc3", 1.00, 10);

        // Invoke
        prophecyFileDAO.deleteProphecy(1);

        // Analyze
        Prophecy[] Prophecies = prophecyFileDAO.getProphecies();
        assertEquals(Prophecies.length,expectedProphecies.length);
        for (int i = 0; i < expectedProphecies.length;++i)
            assertEquals(Prophecies[i],expectedProphecies[i]);
    }




    @Test
    public void testCreateProphecy() {
        // Setup
        Prophecy prophecy = new Prophecy(1, "TestName1", "TestDesc1", 1.00, 1);

        // Invoke
        Prophecy result = assertDoesNotThrow(() -> prophecyFileDAO.createProphecy(prophecy),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Prophecy actual = prophecyFileDAO.getProphecy(prophecy.getId());
        assertEquals(actual.getId(),prophecy.getId());
        assertEquals(actual.getName(),prophecy.getName());
    }

    @Test
    public void testUpdateProphecy() {
        // Setup
        Prophecy Prophecy = new Prophecy(1, "TestName1", "TestDesc1", 1.00, 1);

        // Invoke
        Prophecy result = assertDoesNotThrow(() -> prophecyFileDAO.updateProphecy(Prophecy),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Prophecy actual = prophecyFileDAO.getProphecy(Prophecy.getId());
        assertEquals(actual,Prophecy);
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Prophecy[].class));

        Prophecy Prophecy = new Prophecy(1, "TestName1", "TestDesc1", 1.00, 1);

        assertThrows(IOException.class,
                        () -> prophecyFileDAO.createProphecy(Prophecy),
                        "IOException not thrown");
    }

    @Test
    public void testGetProphecyNotFound() {
        // Invoke
        Prophecy Prophecy = prophecyFileDAO.getProphecy(98);

        // Analyze
        assertEquals(Prophecy,null);
    }

    @Test
    public void testDeleteProphecyNotFound() {
        // Invoke
        assertThrows(IllegalArgumentException.class,
                        () -> prophecyFileDAO.deleteProphecy(94575478),
                        "IllegalArgumentException not thrown");
    }


    
    @Test
    public void testUpdateProphecyNotFound() {
        // Setup
        Prophecy prophecy = new Prophecy(0, "TestName0", "TestDesc0", 0.00, 0);

        // Invoke
        Prophecy result = assertDoesNotThrow(() -> prophecyFileDAO.updateProphecy(prophecy),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the ProphecyFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Prophecy[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new ProphecyFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
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
import com.prophecies.api.propheciesapi.persistence.UserFileDAO;
import com.prophecies.api.propheciesapi.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Persistence-tier")
public class UserFileDAOTest {
    UserFileDAO userFileDAO;
    User[] testUsers;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    public void setupUserFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testUsers = new User[3];
        testUsers[0] = new User("TestName1", "TestPass1");
        testUsers[1] = new User("TestName2", "TestPass2");
        testUsers[2] = new User("TestName3", "TestPass3");
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),User[].class))
                .thenReturn(testUsers);
        userFileDAO = new UserFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetUsers()  {
        User[] Users = userFileDAO.getUsers();
        assertNotNull(Users);
        assertEquals(3, Users.length);
        assertEquals("TestName1", Users[0].getUsername());
        assertEquals("TestPass1", Users[0].getPassword());
        assertEquals("TestName2", Users[1].getUsername());
        assertEquals("TestPass2", Users[1].getPassword());
        assertEquals("TestName3", Users[2].getUsername());
        assertEquals("TestPass3", Users[2].getPassword());
    }
    @Test
    public void testGetUser()  {
        User user = userFileDAO.getUser("TestName1");
        assertNotNull(user);
        assertEquals("TestName1", user.getUsername());
        assertEquals("TestPass1", user.getPassword());
    }
    @Test
    public void testGetUserNotFound()  {
        User user = userFileDAO.getUser("TestName4");
        assertNull(user);
    }
    @Test
    public void testAddUser() throws IOException {
        User user = new User("TestName4", "TestPass4");
        assertDoesNotThrow(() -> userFileDAO.createUser(user));
    }
    @Test
    public void testAddUserAlreadyExists() throws IOException {
        User user = new User("TestName1", "TestPass1");
        assertThrows(IllegalArgumentException.class, () -> userFileDAO.createUser(user));
    }
    @Test
    public void testAddUserIOException() throws IOException {
        User user = new User("TestName4", "TestPass4");
        doThrow(IOException.class).when(mockObjectMapper).writeValue(any(File.class), any(User[].class));
        assertThrows(IOException.class, () -> userFileDAO.createUser(user));
    }
    @Test
    public void testUpdateUser() throws IOException {
        User user = new User("TestName1", "TestPass1");
        assertDoesNotThrow(() -> userFileDAO.updateUser(user));
    }
    @Test
    public void testUpdateUserDoesNotExist() throws IOException {
        User user = new User("TestName4", "TestPass4");
        assertThrows(IllegalArgumentException.class, () -> userFileDAO.updateUser(user));
    }
    @Test
    public void testUpdateUserIOException() throws IOException {
        User user = new User("TestName1", "TestPass1");
        doThrow(IOException.class).when(mockObjectMapper).writeValue(any(File.class), any(User[].class));
        assertThrows(IOException.class, () -> userFileDAO.updateUser(user));
    }
    @Test
    public void testDeleteUser() throws IOException {
        User user = new User("TestName1", "TestPass1");
        assertDoesNotThrow(() -> userFileDAO.deleteUser("TestName1"));
    }
    @Test
    public void testDeleteUserDoesNotExist() throws IOException {
        User user = new User("TestName4", "TestPass4");
        assertThrows(IllegalArgumentException.class, () -> userFileDAO.deleteUser("TestName4"));
    }
    @Test
    public void testDeleteUserIOException() throws IOException {
        User user = new User("TestName1", "TestPass1");
        doThrow(IOException.class).when(mockObjectMapper).writeValue(any(File.class), any(User[].class));
        assertThrows(IOException.class, () -> userFileDAO.deleteUser("TestName1"));
    }


}

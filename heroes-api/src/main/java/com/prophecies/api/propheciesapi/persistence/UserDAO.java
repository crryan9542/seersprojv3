package com.prophecies.api.propheciesapi.persistence;
import java.io.IOException;
import com.prophecies.api.propheciesapi.model.User;

public interface UserDAO {
User[] getUsers() throws IOException;
User[] findUsers(String username) throws IOException;
User getUser(String username) throws IOException;
User createUser(User user) throws IOException;
User updateUser(User user) throws IOException;
boolean deleteUser(String username) throws IOException;

    
}

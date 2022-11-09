package com.prophecies.api.propheciesapi.persistence;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prophecies.api.propheciesapi.model.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserFileDAO implements UserDAO{
    private static final Logger LOG = Logger.getLogger(UserFileDAO.class.getName());
    Map<String,User> users;   // Provides a local cache of the User objects
                            // so that we don't need to read from the file
                            // each time
    private ObjectMapper objectMapper;  // Provides conversion between User
                                        // objects and JSON text format written
                                        // to the file
    private String filename;    // Filename to read from and write to


    public UserFileDAO(@Value("${users.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the users from the file
    }
    
    private User[] getUserArray() {
        return getUserArray(null);
    }

    private User[] getUserArray(String containstext) {
        ArrayList<User> userArrayList = new ArrayList<>();

        for (User user : users.values()) {
            if (containstext == null || user.getUsername().contains(containstext)) {
                userArrayList.add(user);
            }
        }

        User[] userArray = new User[userArrayList.size()];
        userArrayList.toArray(userArray);
        return userArray;
    }


    private boolean save() throws IOException{
        User[] userArray = getUserArray();
        objectMapper.writeValue(new File(filename), userArray);
        return true;
    }

    private boolean load() throws IOException{
        User[] userArray = objectMapper.readValue(new File(filename), User[].class);
        users = new TreeMap<>();
        for (User user : userArray) {
            users.put(user.getUsername(), user);
        }
        return true;
    }


    @Override
    public User[] getUsers() {
        synchronized (users) {
            return getUserArray();
        }
    }
    @Override
    public User[] findUsers(String username) {
        synchronized (users) {
            return getUserArray(username);
        }
    }

    @Override
    public User getUser(String username) {
        synchronized (users) {
            if(users.containsKey(username)){
                return users.get(username);
            }else{
                return null;
            }
        }
    }
    @Override
    public User createUser (User user) throws IOException{
        synchronized(users){
            User newUser = new User(user.getUsername(), user.getPassword());
            users.put(newUser.getUsername(), newUser);
            save();
            return newUser;
        }
    }
    @Override
    public User updateUser (User user) throws IOException{
        synchronized(users){
            if(users.containsKey(user.getUsername())){
                users.put(user.getUsername(), user);
                save();
                return user;
            }else{
                return null;
            }
        }
    }
    @Override
    public boolean deleteUser (String username) throws IOException{
        synchronized(users){
            if(users.containsKey(username)){
                users.remove(username);
                save();
                return true;
            }else{
                return false;
            }
        }
    }

    




}

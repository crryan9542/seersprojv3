package com.prophecies.api.propheciesapi.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.prophecies.api.propheciesapi.model.User;
import com.prophecies.api.propheciesapi.persistence.UserDAO;

@RestController
@RequestMapping("users")
public class UserController{
private static final Logger LOG = Logger.getLogger(UserController.class.getName());
private UserDAO userDao;

public UserController(UserDAO userDao) {
    this.userDao = userDao;
}

@GetMapping("/{username}")
public ResponseEntity<User> getUser(@PathVariable("username") String username) {
    try {
        User user = userDao.getUser(username);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } catch (IOException ex) {
        LOG.log(Level.SEVERE, "Error getting user", ex);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

@GetMapping("")
public ResponseEntity<User[]> getUsers(){
    LOG.info("GET /users");
    try {
        User[] users = userDao.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    } catch (IOException ex) {
        LOG.log(Level.SEVERE, "Error getting users", ex);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

@PostMapping("/")
public ResponseEntity<User[]> searchUsers(@RequestParam String username){
    LOG.info("GET /users" + username);
    try{
        User[] users = userDao.findUsers(username);
        return new ResponseEntity<>(users, HttpStatus.OK);
    } catch (IOException ex) {
        LOG.log(Level.SEVERE, "Error getting users", ex);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
@PostMapping("")
public ResponseEntity<User> createUser(@RequestBody User user) {
    LOG.info("POST /users" + user);
    try{
        User[] curUsers = userDao.getUsers();
        boolean alreadyPresent = Arrays.asList(curUsers).contains(user);
        if (alreadyPresent) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            userDao.createUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    } catch (IOException ex) {
        LOG.log(Level.SEVERE, "Error creating user", ex);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }
}

@PutMapping("")
public ResponseEntity<User> updateUser(@RequestBody User user) {
    LOG.info("PUT /users" + user);
    try{
        ArrayList<User> curUsers = new ArrayList<User>(Arrays.asList(userDao.getUsers()));

        if(!curUsers.contains(user)){
            user = userDao.updateUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } catch (IOException ex) {
        LOG.log(Level.SEVERE, "Error updating user", ex);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}

@DeleteMapping("/{username}")
public ResponseEntity<User> deleteUser(@PathVariable String user){
    LOG.info("DELETE /users" + user);

    try{
    User userlst = userDao.getUser(user);
    ArrayList<User> lst = new ArrayList<User>(Arrays.asList(userDao.getUsers()));
        if(lst.contains(userlst)){
            userDao.deleteUser(user);
            return new ResponseEntity<>(userlst, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } catch (IOException ex) {
        LOG.log(Level.SEVERE, "Error deleting user", ex);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}


}
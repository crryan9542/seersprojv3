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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.prophecies.api.propheciesapi.model.Cart;
import com.prophecies.api.propheciesapi.persistence.CartDAO;

@RestController
@RequestMapping("carts")
public class CartController{
private static final Logger LOG = Logger.getLogger(CartController.class.getName());
private CartDAO cartDao;

public CartController(CartDAO cartDao) {
    this.cartDao = cartDao;
}

@GetMapping("/{username}")
public ResponseEntity<Cart> getCart(@PathVariable("username") String username) {
    try {
        Cart cart = cartDao.getCart(username);
        if (cart != null) {
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } catch (Exception ex) {
        LOG.log(Level.SEVERE, "Error getting cart", ex);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
@GetMapping("")
public ResponseEntity<Cart[]> getCarts(){
    LOG.info("GET /carts");
    try {
        Cart[] carts = cartDao.getCarts();
        return new ResponseEntity<>(carts, HttpStatus.OK);
    } catch (Exception ex) {
        LOG.log(Level.SEVERE, "Error getting carts", ex);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
@PostMapping("/")
public ResponseEntity<Cart[]> searchCarts(@RequestParam String name){
    LOG.info("GET /carts/?name=" + name);
    try{
        return new ResponseEntity<Cart[]>(cartDao.findCarts(name), HttpStatus.OK);
    }catch(Exception ex){
        LOG.log(Level.SEVERE, "Error getting carts", ex);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

@PostMapping("")
public ResponseEntity<Cart> createCart(@RequestBody Cart cart){
LOG.info("POST /carts" + cart);
try{
    Cart[] carts = cartDao.getCarts();
    boolean alreadythere = Arrays.asList(carts).contains(cart);
    if(alreadythere){
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    else{
        cartDao.createCart(cart);
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }
} catch (Exception ex) {
    LOG.log(Level.SEVERE, "Error creating cart", ex);
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
}
}


@PutMapping("")
public ResponseEntity<Cart> updateCart(@RequestBody Cart cart){
LOG.info("PUT /carts" + cart);
try{
    ArrayList<Cart> carts = new ArrayList<Cart>(Arrays.asList(cartDao.getCarts()));
    if(!carts.contains(cart)){
        cart = cartDao.updateCart(cart);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    else
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    catch(Exception ex){
        LOG.log(Level.SEVERE, "Error updating cart", ex);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


@DeleteMapping("/{username}")
public ResponseEntity<Cart> deleteCart(@PathVariable("username") String username){
LOG.info("DELETE /carts/" + username);
try{
    Cart cart = cartDao.getCart(username);
    if(cart != null){
        cartDao.deleteCart(username);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    else
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    catch(Exception ex){
        LOG.log(Level.SEVERE, "Error deleting cart", ex);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}










}




























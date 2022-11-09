package com.prophecies.api.propheciesapi.persistence;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prophecies.api.propheciesapi.model.Cart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CartFileDAO implements CartDAO{
    private static final Logger LOG = Logger.getLogger(CartFileDAO.class.getName());
    Map<String,Cart> carts;   // Provides a local cache of the Cart objects
                            // so that we don't need to read from the file
                            // each time
    
    private ObjectMapper objectMapper;  // Provides conversion between Cart
                                        // objects and JSON text format written
                                        // to the file

    private String filename;    // Filename to read from and write to


    public CartFileDAO(@Value("${carts.file}") String filename,ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();  // load the carts from the file
    }

    private Cart[] getCartArray() {
        return getCartArray(null);
    }

    private Cart[] getCartArray(String containstext) {
        ArrayList<Cart> cartArrayList = new ArrayList<>();

        for (Cart cart : carts.values()) {
            if (containstext == null || cart.getUsername().contains(containstext)) {
                cartArrayList.add(cart);
            }
        }

        Cart[] cartArray = new Cart[cartArrayList.size()];
        cartArrayList.toArray(cartArray);
        return cartArray;
    }

    private boolean save() throws IOException{
        Cart[] cartArray = getCartArray();
        objectMapper.writeValue(new File(filename), cartArray);
        return true;
    }

    private boolean load() throws IOException{
        Cart[] cartArray = objectMapper.readValue(new File(filename), Cart[].class);
        carts = new TreeMap<>();
        for (Cart cart : cartArray) {
            carts.put(cart.getUsername(), cart);
        }
        return true;
    }




    @Override
    public Cart[] getCarts() throws IOException {
        synchronized (carts) {
            return getCartArray();
        }
    }

    @Override
    public Cart[] findCarts(String username) throws IOException {
        synchronized (carts) {
            return getCartArray(username);
        }
    }

    @Override
    public Cart getCart(String username) throws IOException {
        synchronized (carts) {
            if(carts.containsKey(username)){
                return carts.get(username);
            }else{
                return null;
            }
        }
    }

    @Override
    public Cart createCart(Cart cart) throws IOException {
        synchronized(carts){
            Cart newCart = new Cart(cart.getUsername(), cart.getProphecies());
            carts.put(newCart.getUsername(), newCart);
            save();
            return newCart;
        }
    }

    @Override
    public Cart updateCart(Cart cart) throws IOException {
        synchronized(carts){
            if(carts.containsKey(cart.getUsername())){
                Cart newCart = new Cart(cart.getUsername(), cart.getProphecies());
                carts.put(newCart.getUsername(), newCart);
                save();
                return newCart;
            }else{
                return null;
            }
        }
    }

    @Override
    public boolean deleteCart(String username) throws IOException {
        synchronized(carts){
            if(carts.containsKey(username)){
                carts.remove(username);
                save();
                return true;
            }else{
                return false;   
            }
        }
    }
   
}

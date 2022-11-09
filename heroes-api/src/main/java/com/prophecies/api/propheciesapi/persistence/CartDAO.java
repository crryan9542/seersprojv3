package com.prophecies.api.propheciesapi.persistence;

import java.io.IOException;

import com.prophecies.api.propheciesapi.model.Cart;

public interface CartDAO {

    Cart[] getCarts() throws IOException;
    Cart[] findCarts(String username) throws IOException;
    Cart getCart(String username) throws IOException;
    Cart createCart(Cart cart) throws IOException;
    Cart updateCart(Cart cart) throws IOException;
    boolean deleteCart(String username) throws IOException;


    
}

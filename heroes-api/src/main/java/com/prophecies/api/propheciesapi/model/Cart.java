package com.prophecies.api.propheciesapi.model;
import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;
public class Cart {
    private static final Logger LOG = Logger.getLogger(Cart.class.getName());
    

    @JsonProperty("username") private String username;
    @JsonProperty("prophecies") private Prophecy[] prophecies;


    public Cart(@JsonProperty("username") String username, @JsonProperty("prophecies") Prophecy[] prophecies) {
        this.username = username;
        this.prophecies = prophecies;
    }

    public String getUsername() {
        return username;
    }
    public Prophecy[] getProphecies() {
        return prophecies;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setProphecies(Prophecy[] prophecies) {
        this.prophecies = prophecies;
    }
    


}

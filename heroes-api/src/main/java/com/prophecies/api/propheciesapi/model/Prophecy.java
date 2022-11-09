package com.prophecies.api.propheciesapi.model;
import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Prophecy {
    private static final Logger LOG = Logger.getLogger(Prophecy.class.getName());
    static final String STRING_FORMAT = "Prophecy [id=%d, name=%s, description=%s, price=%.2f, quantity=%d]";
    
    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("description") private String description;
    @JsonProperty("price") private double price;
    @JsonProperty("quantity") private int quantity;
    
    public Prophecy(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("description") String description, @JsonProperty("price") double price, @JsonProperty("quantity") int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id; 
        // this returns the id
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT,id,name,description,price,quantity);
    }
}



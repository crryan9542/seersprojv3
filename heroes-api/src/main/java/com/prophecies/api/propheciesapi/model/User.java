package com.prophecies.api.propheciesapi.model;
import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private static final Logger LOG = Logger.getLogger(User.class.getName());
    // didn't see a need for STRING_FORMAT here :()
    @JsonProperty("username") private String username;
    @JsonProperty("password") private String password;

    public User(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

public String getUsername() {
    return username;
}

public String getPassword() {
    return password;
}
public void setUsername(String username) {
    this.username = username;
}
public void setPassword(String password) {
    this.password = password;
}

}

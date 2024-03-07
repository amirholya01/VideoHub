package com.videohub.videohub.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Data annotation from Lombok automatically generates getters, setters, equals, hashCode, and toString methods
@Data

@AllArgsConstructor
// @AllArgsConstructor annotation from Lombok generates a constructor with all fields in the class as arguments

// @NoArgsConstructor annotation from Lombok generates a constructor with no arguments
@NoArgsConstructor

public class LoginRequest {

    // Private fields to store username and password

    private String username;
    private String password;
}

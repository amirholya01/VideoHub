package com.videohub.videohub.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  // @Data annotation from Lombok automatically generates getters, setters, equals, hashCode, and toString methods
@AllArgsConstructor  // @AllArgsConstructor annotation from Lombok generates a constructor with all fields in the class as arguments
@NoArgsConstructor  // @NoArgsConstructor annotation from Lombok generates a constructor with no arguments
public class ResponseMessage {

    // Private field to store the response message
    private String message;
}

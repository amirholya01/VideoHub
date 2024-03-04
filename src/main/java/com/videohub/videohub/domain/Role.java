package com.videohub.videohub.domain;

import com.videohub.videohub.enums.ERole;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "roles")
public class Role {

    private String id;
    private ERole name;

    public Role() {
    }

    public Role(ERole name) {
        this.name = name;
    }
}

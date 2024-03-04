package com.videohub.videohub.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "categories")
public class Category {

    /**
     * The unique identifier for the category.
     */
    @Id
    private String id;

    /**
     * The name of the category.
     */
    @NotBlank
    private String name;

    /**
     * The description of the category.
     */
    @NotBlank
    private String description;

    /**
     * The set of videos associated with the category.
     * Uses MongoDB's DBRef to establish a reference to Video entities.
     */
    @DBRef
    private Set<Video> videos = new HashSet<>();
}

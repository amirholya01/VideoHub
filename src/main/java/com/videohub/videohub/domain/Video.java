package com.videohub.videohub.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "videos")
public class Video {

    @Id
    private String id;

    private String title;
    private double size;
    private String url;
    private Date created;
    private Date modified;
}

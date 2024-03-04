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
    private User user;
    private Category category;
    public Video() {
    }

    public Video(User user, String title, double size, String url) {
        this.user = user;
        this.title = title;
        this.size = size;
        this.url = url;
        this.created = new Date();
        this.modified = new Date();
    }

    public Video(String title, double size, String url, User user, Category category) {
        this.title = title;
        this.size = size;
        this.url = url;
        this.user = user;
        this.category = category;
        this.created = new Date();
        this.modified = new Date();
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", size=" + size +
                ", url='" + url + '\'' +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}

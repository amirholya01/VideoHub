package com.videohub.videohub.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


/**
 * Represents a video entity in the application.
 */
@Data
@Document(collection = "videos")
public class Video {


    /**
     * The unique identifier for the video.
     */
    @Id
    private String id;

    /**
     * The title of the video.
     */
    private String title;

    /**
     * The size of the video in bytes.
     */
    private double size;

    /**
     * The URL where the video is stored or can be accessed.
     */
    private String url;

    /**
     * The date when the video was created.
     */
    private Date created;

    /**
     * The date when the video was last modified.
     */
    private Date modified;

    /**
     * The user who uploaded the video.
     */
    private User user;

    /**
     * The category to which the video belongs.
     */
    private Category category;

    /**
     * Default constructor for the Video class.
     */
    public Video() {
    }


    /**
     * Constructor for the Video class with parameters for user, title, size, and URL.
     * @param user The user who uploaded the video.
     * @param title The title of the video.
     * @param size The size of the video in bytes.
     * @param url The URL where the video is stored or can be accessed.
     */
    public Video(User user, String title, double size, String url) {
        this.user = user;
        this.title = title;
        this.size = size;
        this.url = url;
        this.created = new Date();
        this.modified = new Date();
    }


    /**
     * Constructor for the Video class with parameters for title, size, URL, user, and category.
     * @param title The title of the video.
     * @param size The size of the video in bytes.
     * @param url The URL where the video is stored or can be accessed.
     * @param user The user who uploaded the video.
     * @param category The category to which the video belongs.
     */
    public Video(String title, double size, String url, User user, Category category) {
        this.title = title;
        this.size = size;
        this.url = url;
        this.user = user;
        this.category = category;
        this.created = new Date();
        this.modified = new Date();
    }


    /**
     * Returns a string representation of the Video object.
     * @return A string representation of the Video object.
     */
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

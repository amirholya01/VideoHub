package com.videohub.videohub.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



/**
 * Represents a rating given to a video.
 * Each rating consists of a video ID, number of likes, and number of dislikes.
 */
@Data
@Document(collection = "rate")
public class Rate {
    @Id
    private String id;  // Unique identifier for the rate object
    private String videoId;  // ID of the video that is being rated
    private int like;  // Number of likes received for the video
    private int disLike;  // Number of dislikes received for the video
}

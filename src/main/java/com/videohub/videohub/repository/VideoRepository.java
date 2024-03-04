package com.videohub.videohub.repository;

import com.videohub.videohub.domain.Video;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Repository interface for managing Video entities in the database.
 */
@Repository
public interface VideoRepository extends MongoRepository<Video, String> {

    /**
     * Finds videos by the username of the user who uploaded them.
     * @param user The username of the user.
     * @return A list of videos uploaded by the user.
     */
    List<Video> findByUserUsername(String user);

    /**
     * Finds videos by their URL.
     * @param url The URL of the video.
     * @return An optional containing a list of videos with the specified URL.
     */
    Optional<List<Video>> findByUrl(String url);

    /**
     * Finds videos by the ID of the category they belong to.
     * @param categoryId The ID of the category.
     * @return A list of videos belonging to the category with the specified ID.
     */
    List<Video> findByCategoryId(String categoryId);
}

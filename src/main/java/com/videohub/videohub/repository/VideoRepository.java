package com.videohub.videohub.repository;

import com.videohub.videohub.domain.Video;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends MongoRepository<Video, String> {
    List<Video> findByUserUsername(String user);
    Optional<List<Video>> findByUrl(String url);
    List<Video> findByCategoryId(String categoryId);
}

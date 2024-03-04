package com.videohub.videohub.repository;

import com.videohub.videohub.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Repository interface for managing Category entities in the database.
 */
@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {

    /**
     * Finds a category by its name.
     * @param categoryName The name of the category.
     * @return An optional containing the category with the specified name, if found.
     */
    Optional<Category> findByName(String categoryName);
}

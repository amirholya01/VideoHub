package com.videohub.videohub.service;

import com.videohub.videohub.domain.Category;
import com.videohub.videohub.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Method to create a new category
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    // Method to retrieve all categories
    public List<Category> findAllCategory() {
        return categoryRepository.findAll();
    }

    // Method to delete a category by ID
    public void deleteCategoryById(String id) {
        categoryRepository.deleteById(id);
    }


    // Method to update a category by ID
    public Category updateCategoryById(String id, Category categoryReq) {

        // Create a new instance of Category
        Category category = new Category();
        // Retrieve the category data by ID
        Optional<Category> categoryData = categoryRepository.findById(id);
        // If category data exists
        if (categoryData.isPresent()) {
            category = categoryData.get();  // Get the category object
        } else {
            return null;  // If category doesn't exist, return null
        }

        // Update category properties if the corresponding property in categoryReq is not null or empty
        if (categoryReq.getName() != null) {
            if (!categoryReq.getName().isEmpty())
                category.setName(categoryReq.getName());
        }
        if (categoryReq.getDescription() != null) {
            if (!categoryReq.getDescription().isEmpty())
                category.setDescription(categoryReq.getDescription());
        }
        // Save and return the updated category
        return categoryRepository.save(category);
    }

    // Method to find a category by ID
    public Category findCategoryById(String id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return category.get();
        }
        // If category exists, return it, otherwise return null
        return null;
    }
}

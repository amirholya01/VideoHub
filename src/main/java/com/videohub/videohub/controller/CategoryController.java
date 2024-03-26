package com.videohub.videohub.controller;

import com.videohub.videohub.domain.Category;
import com.videohub.videohub.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)  // Allowing requests from all origins and caching pre-flight response for 3600 seconds
@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    // Endpoint to create a new category (requires ADMIN role)
    @PostMapping
   // @PreAuthorize("hasRole('ADMIN')")  // Allowing only users with ADMIN role to access this endpoint
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Category> create(@Valid @RequestBody Category category) {
        return new ResponseEntity<>(categoryService.create(category), HttpStatus.OK);
    }


    // Endpoint to retrieve all categories (requires ADMIN role)
    @GetMapping
   // @PreAuthorize("hasRole('ADMIN')")  // Allowing only users with ADMIN role to access this endpoint
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<List<Category>> findAllCategory() {
        return new ResponseEntity<>(categoryService.findAllCategory(), HttpStatus.OK);
    }


    // Endpoint to find category by ID (requires ADMIN role)
    @GetMapping("{id}")
   // @PreAuthorize("hasRole('ADMIN')")  // Allowing only users with ADMIN role to access this endpoint
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Category> findCategoryById(@PathVariable String id) {
        return new ResponseEntity<>(categoryService.findCategoryById(id), HttpStatus.OK);
    }


    // Endpoint to delete category by ID (requires ADMIN role)
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")  // Allowing only users with ADMIN role to access this endpoint
    @SecurityRequirement(name = "Bearer Authentication")
    public void deleteCategoryById(@PathVariable String id) {
        categoryService.deleteCategoryById(id);
    }


    // Endpoint to update category by ID (requires ADMIN role)
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")  // Allowing only users with ADMIN role to access this endpoint
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Category> updateCategoryById(@PathVariable String id, @RequestBody Category categoryReq) {
        return new ResponseEntity<>(categoryService.updateCategoryById(id, categoryReq), HttpStatus.OK);
    }


}

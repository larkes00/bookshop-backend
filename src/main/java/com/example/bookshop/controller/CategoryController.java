package com.example.bookshop.controller;

import com.example.bookshop.model.Category;
import com.example.bookshop.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("categories/")
    public ResponseEntity<Collection<Category>> getAllCategory(@RequestParam int limit) {
        return ResponseEntity.ok(
                categoryService.list(limit)
        );
    }

    @GetMapping("categories/{id}/")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.get(id);
        return category == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(
                categoryService.get(id)
        );
    }

    @DeleteMapping("categories/{id}/")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        boolean category = categoryService.delete(id);
        return !category ? ResponseEntity.badRequest().body("No such category") : ResponseEntity.ok("deleted");
    }

    @PostMapping(value = "categories/", consumes={"application/json"})
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        Category tempCategory = categoryService.create(category);
        return tempCategory == null ? ResponseEntity.badRequest().body("Category exists") : ResponseEntity.ok(
                tempCategory
        );
    }
}

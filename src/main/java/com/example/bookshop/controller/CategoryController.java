package com.example.bookshop.controller;

import com.example.bookshop.model.Category;
import com.example.bookshop.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/")
    public ResponseEntity<Collection<Category>> getAllCategory(@RequestParam int limit) {
        return ResponseEntity.ok(
                categoryService.list(limit)
        );
    }

    @GetMapping("/{id}/")
    public ResponseEntity<Map<String, Category>> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.get(id);
        return category == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(
                Map.of("category", categoryService.get(id))
        );
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteCategory(@RequestParam Long id) {
        boolean category = categoryService.delete(id);
        return !category ? ResponseEntity.badRequest().body("No such category") : ResponseEntity.ok("deleted");
    }

    @PostMapping(value = "/", consumes={"application/json"})
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        Category tempCategory = categoryService.create(category);
        return tempCategory == null ? ResponseEntity.badRequest().body("Category exists") : ResponseEntity.ok(
                tempCategory
        );
    }
}

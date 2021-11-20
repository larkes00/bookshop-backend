package com.example.bookshop.controller;

import com.example.bookshop.exception.CategoryExistsException;
import com.example.bookshop.exception.CategoryNotFoundException;
import com.example.bookshop.model.Category;
import com.example.bookshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(
                categoryService.list()
        );
    }

    @GetMapping("/{id}/books/")
    public ResponseEntity<?> getBooksByCategoryId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(categoryService.get(id));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.delete(id);
            return ResponseEntity.ok("Deleted");
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/")
    @ResponseBody public ResponseEntity<?> createCategory(@RequestBody Category category) {
        try {
            return ResponseEntity.ok(categoryService.create(category));
        } catch (CategoryExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}/")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try {
            return ResponseEntity.ok(categoryService.update(id, category));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (CategoryExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

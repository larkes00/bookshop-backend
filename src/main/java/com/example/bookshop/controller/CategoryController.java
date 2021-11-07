package com.example.bookshop.controller;

import com.example.bookshop.exception.CategoryExistsException;
import com.example.bookshop.exception.CategoryNotFoundException;
import com.example.bookshop.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("categories/")
    public ResponseEntity<?> getAllCategory() {
        return ResponseEntity.ok(
                categoryService.list()
        );
    }

    @GetMapping("categories/{id}/books/")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(categoryService.get(id));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("categories/{id}/")
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

    @PostMapping(value = "categories/")
    public ResponseEntity<?> createCategory(@RequestParam String name) {
        try {
            return ResponseEntity.ok(categoryService.create(name));
        } catch (CategoryExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "categories/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestParam String name) {
        try {
            categoryService.update(id, name);
            return ResponseEntity.ok(true);
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (CategoryExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

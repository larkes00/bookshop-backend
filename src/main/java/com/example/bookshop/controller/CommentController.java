package com.example.bookshop.controller;

import com.example.bookshop.exception.BookNotFoundException;
import com.example.bookshop.exception.CommentNotFoundException;
import com.example.bookshop.exception.UserNotFoundException;
import com.example.bookshop.model.Comment;
import com.example.bookshop.service.CommentService;
import com.example.bookshop.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public ResponseEntity<?> getAllComments() {
        return ResponseEntity.ok(commentService.list());
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> createComment(@RequestBody Comment comment) {
        try {
            return ResponseEntity.ok(commentService.create(comment));
        } catch (BookNotFoundException | UserNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(commentService.delete(id));
        } catch (CommentNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}/")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody Comment comment) {
        try {
            return ResponseEntity.ok(commentService.update(id, comment));
        } catch (CommentNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

package com.example.bookshop.controller;

import com.example.bookshop.exception.BookExistsException;
import com.example.bookshop.exception.BookNotFoundException;
import com.example.bookshop.exception.CategoryNotFoundException;
import com.example.bookshop.model.Book;
import com.example.bookshop.service.BookService;
import com.example.bookshop.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private CommentService commentService;


    @GetMapping("/{id}/")
    private ResponseEntity<?> getBookById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(bookService.get(id));
        } catch (BookNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    private ResponseEntity<?> getAllBooks() {
        try {
            return ResponseEntity.ok(bookService.list());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/")
    private ResponseEntity<?> createBook(@RequestBody Book book) {
        try {
            return ResponseEntity.ok(bookService.create(book));
        } catch (BookExistsException | CategoryNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/")
    private ResponseEntity<?> deleteBook(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(bookService.delete(id));
        } catch (BookNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping(value = "/{id}/comments/")
    public ResponseEntity<?> getBookComments(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(commentService.getBookComments(id));
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/")
    public ResponseEntity<?> updateBook(@PathVariable Long id,
                                        @RequestParam BigDecimal price,
                                        @RequestParam int booksAvailableNumber) {
        try {
            return ResponseEntity.ok(bookService.update(id, price, booksAvailableNumber));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

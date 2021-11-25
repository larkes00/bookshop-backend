package com.example.bookshop.controller;

import com.example.bookshop.exception.CommentNotFoundException;
import com.example.bookshop.exception.OrderNotFoundException;
import com.example.bookshop.service.OrderService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<?> getAllOrders() {
        try {
            return ResponseEntity.ok(orderService.list());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> addItemToOrder(@RequestBody ItemToOrderForm itemToOrderForm) {
        try {
            return ResponseEntity.ok(orderService.addItem(itemToOrderForm.getUserId(), itemToOrderForm.getBookId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/books/user/{id}/")
    public ResponseEntity<?> getUserOrder(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(orderService.get(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/user/{id}/")
    public ResponseEntity<?> getUserOrders(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(orderService.getList(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(orderService.delete(id));
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

@Data
class ItemToOrderForm {
    private Long userId;
    private Long bookId;
}
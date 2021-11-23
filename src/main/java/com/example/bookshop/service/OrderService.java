package com.example.bookshop.service;

import com.example.bookshop.exception.BookNotFoundException;
import com.example.bookshop.exception.OrderNotFoundException;
import com.example.bookshop.exception.UserNotFoundException;
import com.example.bookshop.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> list();

    boolean addItem(Long userId, Long bookId) throws UserNotFoundException, BookNotFoundException;

    Order get(Long id) throws OrderNotFoundException, UserNotFoundException;

    List<Order> getAll(Long id);
}

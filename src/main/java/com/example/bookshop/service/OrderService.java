package com.example.bookshop.service;

import com.example.bookshop.exception.BookNotFoundException;
import com.example.bookshop.exception.OrderNotFoundException;
import com.example.bookshop.exception.UserNotFoundException;
import com.example.bookshop.model.Book;
import com.example.bookshop.model.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {
    List<Order> list();

    boolean addItem(Long userId, Long bookId) throws UserNotFoundException, BookNotFoundException;

    Map<String, List<Book>> get(Long id) throws OrderNotFoundException, UserNotFoundException;

    List<Map<String, String>> getList(Long id) throws UserNotFoundException, OrderNotFoundException;
}

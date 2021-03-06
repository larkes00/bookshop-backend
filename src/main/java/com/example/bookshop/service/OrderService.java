package com.example.bookshop.service;

import com.example.bookshop.exception.BookNotFoundException;
import com.example.bookshop.exception.OrderNotFoundException;
import com.example.bookshop.exception.UserNotFoundException;

import java.util.List;
import java.util.Map;

public interface OrderService {
    List<Map<String, String>> list();

    boolean addItem(Long userId, Long bookId) throws UserNotFoundException, BookNotFoundException;

    List<Map<?, ?>> get(Long id) throws OrderNotFoundException, UserNotFoundException;

    List<Map<String, String>> getList(Long id) throws UserNotFoundException, OrderNotFoundException;

    Boolean delete(Long id) throws OrderNotFoundException;

    Boolean deleteItem(Long orderId, Long itemId) throws OrderNotFoundException;

    Boolean changeStatus(Long id, String deliveryAddress, String status) throws OrderNotFoundException;
}

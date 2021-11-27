package com.example.bookshop.service;

import com.example.bookshop.exception.BookExistsException;
import com.example.bookshop.exception.BookNotFoundException;
import com.example.bookshop.exception.CategoryNotFoundException;
import com.example.bookshop.model.Book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface BookService {
    Boolean create(Book book) throws BookNotFoundException, BookExistsException, CategoryNotFoundException;

    Map<String, String> get(Long id) throws BookNotFoundException;

    List<Map<String, String>> list();

    Boolean update(Long id, BigDecimal price, int booksAvailableNumber) throws BookNotFoundException;

    Boolean delete(Long id) throws BookNotFoundException;
}

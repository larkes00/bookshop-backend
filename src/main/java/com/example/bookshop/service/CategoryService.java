package com.example.bookshop.service;

import com.example.bookshop.exception.CategoryExistsException;
import com.example.bookshop.exception.CategoryNotFoundException;
import com.example.bookshop.model.Category;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CategoryService {
    Category create(String name) throws CategoryExistsException;

    List<Map<String, String>> list();

    Optional<Category> get(Long id) throws CategoryNotFoundException;

    Category update(Category category);

    Boolean delete(Long id) throws CategoryNotFoundException;
}

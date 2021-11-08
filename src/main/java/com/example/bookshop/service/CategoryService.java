package com.example.bookshop.service;

import com.example.bookshop.exception.CategoryExistsException;
import com.example.bookshop.exception.CategoryNotFoundException;
import com.example.bookshop.model.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    Category create(Category category) throws CategoryExistsException;

    List<Map<String, String>> list();

    Category get(Long id) throws CategoryNotFoundException;

    Category update(Long id, Category category) throws CategoryNotFoundException, CategoryExistsException;

    Boolean delete(Long id) throws CategoryNotFoundException;
}

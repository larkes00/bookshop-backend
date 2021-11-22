package com.example.bookshop.service;

import com.example.bookshop.exception.CategoryExistsException;
import com.example.bookshop.exception.CategoryNotFoundException;
import com.example.bookshop.model.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    Boolean create(Category category) throws CategoryExistsException;

    List<Category> list();

    List<Map<String, String>> getbooksByCategory(Long id) throws CategoryNotFoundException;

    Boolean update(Long id, Category category) throws CategoryNotFoundException, CategoryExistsException;

    Boolean delete(Long id) throws CategoryNotFoundException;
}

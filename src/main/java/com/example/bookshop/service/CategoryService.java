package com.example.bookshop.service;

import com.example.bookshop.exception.CategoryExistsException;
import com.example.bookshop.exception.CategoryNotFoundException;
import com.example.bookshop.model.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    Category create(String name) throws CategoryExistsException;

    List<Map<String, String>> list();

    Category get(Long id) throws CategoryNotFoundException;

    Category update(Long id, String name) throws CategoryNotFoundException, CategoryExistsException;

    Boolean delete(Long id) throws CategoryNotFoundException;
}

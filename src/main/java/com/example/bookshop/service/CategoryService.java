package com.example.bookshop.service;

import com.example.bookshop.model.Category;

import java.util.Collection;

public interface CategoryService {
    Category create(Category category);
    Collection<Category> list(int limit);
    Category get(Long id);
    Category update(Category category);
    Boolean delete(Long id);
}

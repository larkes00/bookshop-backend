package com.example.bookshop.service.impl;

import com.example.bookshop.exception.CategoryExistsException;
import com.example.bookshop.exception.CategoryNotFoundException;
import com.example.bookshop.model.Category;
import com.example.bookshop.repository.CategoryRepository;
import com.example.bookshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Boolean create(Category category) throws CategoryExistsException {
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new CategoryExistsException("Category " + category.getName() + " already exist");
        }
        categoryRepository.save(category);
        return true;
    }

    @Override
    public List<Category> list() {
        List<Category> categories = categoryRepository.findAll();
        categories.forEach(category -> category.setBooks(null));
        return categoryRepository.findAll();
    }

    @Override
    public Category get(Long id) throws CategoryNotFoundException {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new CategoryNotFoundException("Category by id " + id + " not found");
        }
        Category resultCategory = category.get();
        resultCategory.getBooks().forEach(book -> book.setComments(null));
        return resultCategory;
    }

    @Override
    public Boolean update(Long id, Category category) throws CategoryNotFoundException, CategoryExistsException {
        Optional<Category> foundCategoryById = categoryRepository.findById(id);
        if (foundCategoryById.isEmpty()) {
            throw new CategoryNotFoundException("Category id " + id + " not found");
        }
        Optional<Category> foundCategoryByName = categoryRepository.findByName(category.getName());
        if (foundCategoryByName.isPresent()) {
            throw new CategoryExistsException("Category " + category.getName() + " already exists");
        }
        Category result = foundCategoryById.get();
        result.setName(category.getName());
        categoryRepository.save(result);
        return true;
    }

    @Override
    public Boolean delete(Long id) throws CategoryNotFoundException {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new CategoryNotFoundException("Category by id " + id + " not found");
        }
        categoryRepository.delete(category.get());
        return true;
    }
}

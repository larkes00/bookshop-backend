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
    public Category create(String name) throws CategoryExistsException {
        if (categoryRepository.findByName(name) != null) {
            throw new CategoryExistsException("Category " + name + " already exist");
        }
        Category category = new Category();
        category.setName(name);
        return categoryRepository.save(category);
    }

    @Override
    public List<Map<String, String>> list() {
        List<Map<String, String>> result = new ArrayList<>();
        Collection<String> collection = categoryRepository.getAllCategoriesNames();
        for (String element: collection) {
            String[] temp = element.split(",");
            Map<String, String> map = new HashMap<>();
            map.put("id", temp[0]);
            map.put("name", temp[1]);
            result.add(map);

        }
        return result;
    }

    @Override
    public Category get(Long id) throws CategoryNotFoundException {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new CategoryNotFoundException("Category by id " + id + " not found");
        }
        return category.get();
    }

    @Override
    public Category update(Long id, String name) throws CategoryNotFoundException, CategoryExistsException {
        Optional<Category> foundCategoryById = categoryRepository.findById(id);
        if (foundCategoryById.isEmpty()) {
            throw new CategoryNotFoundException("Category id " + id + " does not exist");
        }
        Category foundCategoryByName = categoryRepository.findByName(name);
        if (foundCategoryByName != null) {
            throw new CategoryExistsException("Category " + name + " already exists");
        }
        Category result = foundCategoryById.get();
        result.setName(name);
        return categoryRepository.save(result);
    }

    @Override
    public Boolean delete(Long id) throws CategoryNotFoundException {
        try {
            categoryRepository.deleteById(id);
        } catch (Exception e) {
            throw new CategoryNotFoundException("Category by id " + id + " not found");
        }
        return true;
    }
}

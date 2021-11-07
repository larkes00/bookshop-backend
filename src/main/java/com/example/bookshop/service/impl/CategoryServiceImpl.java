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
    public Optional<Category> get(Long id) throws CategoryNotFoundException {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new CategoryNotFoundException("Category by id " + id + " not found");
        }
        return category;
    }

    @Override
    public Category update(Category category) {
        return categoryRepository.save(category);
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

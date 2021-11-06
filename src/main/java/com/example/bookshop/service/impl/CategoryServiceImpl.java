package com.example.bookshop.service.impl;

import com.example.bookshop.model.Category;
import com.example.bookshop.repository.CategoryRepository;
import com.example.bookshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collection;
import java.util.NoSuchElementException;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Collection<Category> list(int limit) {
        return categoryRepository.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Category get(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category update(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Boolean delete(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

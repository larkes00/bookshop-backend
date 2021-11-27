package com.example.bookshop.service.impl;

import com.example.bookshop.exception.BookExistsException;
import com.example.bookshop.exception.BookNotFoundException;
import com.example.bookshop.exception.CategoryNotFoundException;
import com.example.bookshop.model.Book;
import com.example.bookshop.model.Category;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.repository.CategoryRepository;
import com.example.bookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private Map<String, String> createMap(Book book) {
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(book.getBookId()));
        map.put("name", book.getName());
        map.put("description", book.getDescription());
        map.put("image", book.getImage());
        map.put("price", String.valueOf(book.getPrice()));
        map.put("booksAvailableNumber", String.valueOf(book.getBooksAvailableNumber()));
        map.put("category", book.getCategory() != null ? book.getCategory().getName(): null);
        return map;
    }

    @Override
    public Boolean create(Book book) throws BookExistsException, CategoryNotFoundException {
        Optional<Category> category = categoryRepository.findById(book.getCategory().getCategoryId());
        if (bookRepository.findByName(book.getName()).isPresent()) {
            throw new BookExistsException("Book " + book.getName() + " already exist");
        } else if (category.isEmpty()) {
            throw new CategoryNotFoundException("Category by id " + book.getCategory().getCategoryId() + " not found");
        }
        bookRepository.save(book);
        return true;
    }

    @Override
    public Map<String, String> get(Long id) throws BookNotFoundException {
        Optional<Book> foundBookById = bookRepository.findById(id);
        if (foundBookById.isEmpty()) {
            throw new BookNotFoundException("Book by id " + id + " not found");
        }
        Book book = foundBookById.get();
        return createMap(book);
    }

    @Override
    public List<Map<String, String>> list() {
        List<Book> books = bookRepository.findAll();
        List<Map<String, String>> result = new ArrayList<>();
        for (Book element: books) {
            result.add(createMap(element));
        }
        return result;
    }

    @Override
    public Boolean update(Long id, BigDecimal price, int booksAvailableNumber) throws BookNotFoundException {
        Optional<Book> foundBookById = bookRepository.findById(id);
        if (foundBookById.isEmpty()) {
            throw new BookNotFoundException("Book id " + id + " does not exist");
        }
        Book result = foundBookById.get();
        result.setPrice(price);
        result.setBooksAvailableNumber(booksAvailableNumber);
        return true;
    }

    @Override
    public Boolean delete(Long id) throws BookNotFoundException {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException("Comment by id " + id + " not found");
        }
        bookRepository.delete(book.get());
        return true;
    }
}

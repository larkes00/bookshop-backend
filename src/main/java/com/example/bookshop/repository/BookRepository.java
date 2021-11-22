package com.example.bookshop.repository;

import com.example.bookshop.model.Book;
import com.example.bookshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByName(String name);
    List<Book> findByCategory(Category category);
}

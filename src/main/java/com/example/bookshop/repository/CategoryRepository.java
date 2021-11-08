package com.example.bookshop.repository;

import com.example.bookshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c.id, c.name from Category c")
    Collection<String> getAllCategoriesNames();

    Optional<Category> findByName(String name);
}

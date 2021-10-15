package com.example.bookshop.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "books_available_number", nullable = false, columnDefinition = "integer  default 0")
    private int booksAvailableNumber;

    @Column(nullable = false)
    private String image;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Comment comment;

    public Book() {
    }

    public Book(
            String name, String description, BigDecimal price,
            int booksAvailableNumber, String image, Category category,
            Comment comment) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.booksAvailableNumber = booksAvailableNumber;
        this.image = image;
        this.category = category;
        this.comment = comment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getBooksAvailableNumber() {
        return booksAvailableNumber;
    }

    public void setBooksAvailableNumber(int booksAvailableNumber) {
        this.booksAvailableNumber = booksAvailableNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}

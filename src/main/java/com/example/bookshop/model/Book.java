package com.example.bookshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    @JsonIgnore
    private Category category;

    @ManyToOne
    @JsonIgnore
    private Comment comment;
}

package com.example.bookshop.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "books")
@JsonIdentityInfo(scope = Book.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "bookId")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonAlias("id")
    private long bookId;

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

    @OneToMany(mappedBy = "book")
    private List<Comment> comments;
}

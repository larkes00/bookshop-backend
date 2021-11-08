package com.example.bookshop.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "comments")
@JsonIdentityInfo(scope = Comment.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "commentId")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long commentId;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    private Book book;

    @ManyToOne
    private User user;
}

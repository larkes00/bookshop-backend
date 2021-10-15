package com.example.bookshop.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "comment")
    private List<Book> category;

    @ManyToOne
    private User user;

    public Comment() {
    }

    public Comment(String content, List<Book> category, User user) {
        this.content = content;
        this.category = category;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Book> getCategory() {
        return category;
    }

    public void setCategory(List<Book> category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

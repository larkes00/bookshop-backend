package com.example.bookshop.service;

import com.example.bookshop.exception.BookNotFoundException;
import com.example.bookshop.exception.CommentNotFoundException;
import com.example.bookshop.exception.UserNotFoundException;
import com.example.bookshop.model.Comment;

import java.util.List;

public interface CommentService {
    Boolean create(Comment comment) throws BookNotFoundException, UserNotFoundException;

    List<Comment> list();

    Boolean update(Long id, Comment comment) throws CommentNotFoundException, BookNotFoundException, UserNotFoundException;

    Boolean delete(Long id) throws CommentNotFoundException;
}

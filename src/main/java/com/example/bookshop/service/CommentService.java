package com.example.bookshop.service;

import com.example.bookshop.exception.BookNotFoundException;
import com.example.bookshop.exception.CommentNotFoundException;
import com.example.bookshop.exception.UserNotFoundException;
import com.example.bookshop.model.Comment;

import java.util.List;
import java.util.Map;

public interface CommentService {
    Boolean create(Comment comment) throws BookNotFoundException, UserNotFoundException;

    List<Map<String, String>> list();

    Boolean update(Long id, Comment comment) throws CommentNotFoundException, BookNotFoundException, UserNotFoundException;

    Boolean delete(Long id) throws CommentNotFoundException;

    List<Map<String, String>> getBookComments(Long id) throws BookNotFoundException;
}

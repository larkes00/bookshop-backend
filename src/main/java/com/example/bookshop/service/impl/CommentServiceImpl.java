package com.example.bookshop.service.impl;

import com.example.bookshop.exception.BookNotFoundException;
import com.example.bookshop.exception.CommentNotFoundException;
import com.example.bookshop.exception.UserNotFoundException;
import com.example.bookshop.model.Book;
import com.example.bookshop.model.Comment;
import com.example.bookshop.model.User;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.repository.CommentRepository;
import com.example.bookshop.repository.UserRepository;
import com.example.bookshop.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Boolean create(Comment comment) throws BookNotFoundException, UserNotFoundException {
        Optional<Book> book = bookRepository.findById(comment.getBook().getBookId());
        Optional<User> user = userRepository.findById(comment.getUser().getUserId());
        if (book.isEmpty()) {
            throw new BookNotFoundException("Book by id " + comment.getBook().getBookId() + " not found");
        } else if (user.isEmpty()) {
            throw new UserNotFoundException("User by id " + comment.getUser().getUserId() + " not found");
        }
        commentRepository.save(comment);
        return true;
    }

    @Override
    public List<Map<String, String>> list() {
        List<Comment> list = commentRepository.findAll();
        List<Map<String, String>> result = new ArrayList<>();
        for(Comment comment: list) {
            Map<String, String> map = new HashMap<>();
            map.put("id", String.valueOf(comment.getCommentId()));
            map.put("content", comment.getContent());
            map.put("userId", String.valueOf(comment.getUser().getUserId()));
            map.put("bookId", String.valueOf(comment.getBook().getBookId()));
            result.add(map);
        }
        return result;
    }

    @Override
    public Boolean update(Long id, Comment comment) throws CommentNotFoundException {
        Optional<Comment> foundCommentById = commentRepository.findById(id);
        if (foundCommentById.isEmpty()) {
            throw new CommentNotFoundException("Comment by id " + id + " not found");
        }
        Comment result = foundCommentById.get();
        result.setContent(comment.getContent());
        commentRepository.save(result);
        return true;
    }

    @Override
    public Boolean delete(Long id) throws CommentNotFoundException {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty()) {
            throw new CommentNotFoundException("Comment by id " + id + " not found");
        }
        commentRepository.delete(comment.get());
        return true;
    }

    @Override
    public List<Map<String, String>> getBookComments(Long id) throws BookNotFoundException {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()) {
            throw new BookNotFoundException("Book with id " + id + " not found");
        }
        List<Map<String, String>> response = new LinkedList<>();
        List<Comment> comments = commentRepository.findByBook(book.get());
        comments.forEach(comment -> {
            Map<String, String> map = new HashMap<>();
            map.put("commentId", String.valueOf(comment.getCommentId()));
            map.put("content", comment.getContent());
            map.put("userName", comment.getUser().getUserName());
            response.add(map);
        });
        return response;
    }
}

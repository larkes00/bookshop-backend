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

import java.util.List;
import java.util.Optional;

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
            throw new BookNotFoundException("Book with id " + comment.getBook().getBookId() + " do not exists");
        } else if (user.isEmpty()) {
            throw new UserNotFoundException("User with id " + comment.getUser().getUserId() + " do not exists");
        }
        commentRepository.save(comment);
        return true;
    }

    @Override
    public List<Comment> list() {
        return commentRepository.findAll();
    }

    @Override
    public Boolean update(Long id, Comment comment) throws CommentNotFoundException {
        Optional<Comment> foundCommentById = commentRepository.findById(id);
        if (foundCommentById.isEmpty()) {
            throw new CommentNotFoundException("Comment id " + id + " does not exist");
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
}

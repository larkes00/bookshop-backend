package com.example.bookshop.service.impl;

import com.example.bookshop.exception.BookNotFoundException;
import com.example.bookshop.exception.OrderNotFoundException;
import com.example.bookshop.exception.UserNotFoundException;
import com.example.bookshop.model.Book;
import com.example.bookshop.model.Order;
import com.example.bookshop.model.User;
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.repository.OrderRepository;
import com.example.bookshop.repository.UserRepository;
import com.example.bookshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Order> list() {
        List<Order> order = orderRepository.findAll();
        order.removeIf(ord -> ord.getStatus().equals("CREATED"));
        for (Order ord : order){
            ord.setUser(null);
            for (Book book : ord.getBooks()) {
                book.setCategory(null);
                book.setComments(null);
            }
        }
        return order;
    }

    @Override
    public boolean addItem(Long userId, Long bookId) throws UserNotFoundException, BookNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        Optional<Book> book = bookRepository.findById(bookId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id " + userId + " not found");
        }
        if (book.isEmpty()) {
            throw new BookNotFoundException("Book with id " + bookId + " not found");
        }
        Order order = orderRepository.getOrderByUserAndStatus(user.get(), "CREATED");
        if (order == null) {
            Order newOrder = new Order();
            newOrder.setStatus("CREATED");
            newOrder.setUser(user.get());
            newOrder.getBooks().add(book.get());
            orderRepository.save(newOrder);
            return true;
        }
        order.getBooks().add(book.get());
        orderRepository.save(order);
        return true;
    }

    @Override
    public Order get(Long id) throws OrderNotFoundException, UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        Order order = orderRepository.getOrderByUserAndStatus(user.get(), "CREATED");
        if (order == null) {
            throw new OrderNotFoundException("User don't have order");
        }
        order.setUser(null);
        for (Book book : order.getBooks()) {
            book.setCategory(null);
            book.setComments(null);
        }
        return order;
    }

    @Override
    public List<Order> getAll(Long id) {
        return null;
    }
}

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
    public List<Map<String, String>> list() {
        List<Order> orders = orderRepository.findAll();
        List<Map<String, String>> result = new ArrayList<>();
        for (Order order : orders) {
            Map<String, String> map = new HashMap<>();
            map.put("orderId", String.valueOf(order.getOrderId()));
            map.put("status", order.getStatus());
            map.put("deliveryAddress", order.getDeliveryAddress());
            List<String> books = new ArrayList<>();
            for (Book book : order.getBooks()) {
                books.add(String.valueOf(book.getBookId()));
            }
            map.put("books", String.valueOf(books));
            result.add(map);
        }
        return result;
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
    public Map<String, List<Book>> get(Long id) throws OrderNotFoundException, UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        Order order = orderRepository.getOrderByUserAndStatus(user.get(), "CREATED");
        if (order == null) {
            throw new OrderNotFoundException("User don't have order");
        }
        Map<String, List<Book>> map = new HashMap<>();
        List<Book> tempBook = new ArrayList<>();
        for (Book book : order.getBooks()) {
            Book temp = new Book();
            temp.setName(book.getName());
            temp.setBookId(book.getBookId());
            temp.setImage(book.getImage());
            temp.setPrice(book.getPrice());
            tempBook.add(temp);
        }
        map.put("books", tempBook);
        return map;
    }

    public List<Map<String, String>> getList(Long id) throws UserNotFoundException, OrderNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        List<Order> orders = orderRepository.getOrderByUser(user.get());
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("User don't have order");
        }
        List<Map<String, String>> result = new ArrayList<>();
        for (Order order : orders) {
            if (!order.getStatus().equals("CREATED")) {
                Map<String, String> map = new HashMap<>();
                map.put("orderId", String.valueOf(order.getOrderId()));
                map.put("status", order.getStatus());
                map.put("deliveryAddress", order.getDeliveryAddress());
                result.add(map);
            }
        }
        return result;
    }
}

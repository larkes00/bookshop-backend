package com.example.bookshop.repository;

import com.example.bookshop.model.Order;
import com.example.bookshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order getOrderByUserAndStatus(User user, String string);
}

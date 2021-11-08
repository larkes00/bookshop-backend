package com.example.bookshop.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "orders")
@JsonIdentityInfo(scope = Order.class, generator = ObjectIdGenerators.PropertyGenerator.class, property = "orderId")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonAlias("id")
    private long orderId;

    @Column(nullable = false)
    private String status;

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @ManyToMany
    private List<Book> books;

    @ManyToOne
    private User user;
}

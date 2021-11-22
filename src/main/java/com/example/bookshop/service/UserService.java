package com.example.bookshop.service;

import com.example.bookshop.model.Role;
import com.example.bookshop.model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String userName, String roleName);

    User getUser(String username);

    List<User> getUsers();
}

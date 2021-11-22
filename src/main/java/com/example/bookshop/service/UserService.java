package com.example.bookshop.service;

import com.example.bookshop.model.Role;
import com.example.bookshop.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String userName, String roleName);

    Map<String, String> getUser(String username);

    List<User> getUsers();
}

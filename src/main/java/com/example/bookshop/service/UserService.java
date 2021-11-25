package com.example.bookshop.service;

import com.example.bookshop.exception.UserNotFoundException;
import com.example.bookshop.model.Role;
import com.example.bookshop.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String userName, String roleName);

    User getUser(String username);

    List<Map<String, String>> getUsers();

    Boolean delete(Long id) throws UserNotFoundException;
}

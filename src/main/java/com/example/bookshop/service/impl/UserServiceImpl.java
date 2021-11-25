package com.example.bookshop.service.impl;

import com.example.bookshop.exception.UserNotFoundException;
import com.example.bookshop.model.Role;
import com.example.bookshop.model.User;
import com.example.bookshop.repository.RoleRepository;
import com.example.bookshop.repository.UserRepository;
import com.example.bookshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("User with name " + userName + " not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName("USER");
        user.getRoles().add(role);
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        User user = userRepository.findByUserName(userName);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username " + username + " not found");
        }
        return userRepository.findByUserName(username);
    }

    @Override
    public List<Map<String, String>> getUsers() {
        List<User> list = userRepository.findAll();
        List<Map<String, String>> result = new ArrayList<>();
        if (!list.isEmpty()) {
            for (User user : list) {
                Map<String, String> map = new HashMap<>();
                map.put("userId", String.valueOf(user.getUserId()));
                map.put("username", user.getUserName());
                map.put("email", user.getEmailAddress());
                map.put("phoneNumber", user.getPhoneNumber());
                result.add(map);
            }
            return result;
        }
        return null;
    }

    @Override
    public Boolean delete(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        userRepository.delete(user.get());
        return true;
    }
}

package com.example.TaskManagerApp.service;

import com.example.TaskManagerApp.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    List<User> getAllUsers();
    User getUserById(String id);
    void deleteUserById(String id);
}

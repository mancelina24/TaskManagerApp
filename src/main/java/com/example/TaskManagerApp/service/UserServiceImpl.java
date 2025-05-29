package com.example.TaskManagerApp.service;

import com.example.TaskManagerApp.dto.UserRequestDTO;
import com.example.TaskManagerApp.entity.Role;
import com.example.TaskManagerApp.entity.RoleEnum;
import com.example.TaskManagerApp.entity.User;
import com.example.TaskManagerApp.repository.RoleRepository;
import com.example.TaskManagerApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    // Parola encode
    public User saveUser(User user) {
        // 1. Roller boşsa default olarak USER ata
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Role defaultRole = roleRepository.findByRoleName("USER")
                    .orElseThrow(() -> new RuntimeException("Default role not found"));
            user.setRoles(Set.of(defaultRole));
        } else {
            // 2. Roller varsa, gelen rollerin adlarını kullanarak veritabanından bul
            Set<Role> resolvedRoles = user.getRoles().stream()
                    .map(role -> roleRepository.findByRoleName(role.getRoleName())
                            .orElseThrow(() -> new RuntimeException("Role not found: " + role.getRoleName())))
                    .collect(Collectors.toSet());

            user.setRoles(resolvedRoles);
        }

        // 3. Şifreyi encode et ve kaydet
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
    }

    @Override
    public void deleteUserById(String id) {
    userRepository.deleteById(id);

    }
}

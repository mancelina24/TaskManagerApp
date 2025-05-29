package com.example.TaskManagerApp.controller;

import com.example.TaskManagerApp.dto.AuthRequest;
import com.example.TaskManagerApp.dto.AuthResponse;
import com.example.TaskManagerApp.dto.UserRequestDTO;
import com.example.TaskManagerApp.entity.Role;
import com.example.TaskManagerApp.entity.User;
import com.example.TaskManagerApp.repository.RoleRepository;
import com.example.TaskManagerApp.service.AuthenticationService;
import com.example.TaskManagerApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final RoleRepository roleRepository;



    @Autowired
    public AuthController(UserService userService,
                          AuthenticationService authenticationService,
                          RoleRepository roleRepository) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.roleRepository = roleRepository;
    }



    @PostMapping("/register")
    public User registerUser(@RequestBody UserRequestDTO userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(userDto.getPassword()) // şifre encoding serviste olacak
                .build();

        // Roller verildiyse, string'lerden Role nesnelerine dönüştür
        if (userDto.getRoles() != null && !userDto.getRoles().isEmpty()) {
            Set<Role> roles = userDto.getRoles().stream()
                    .map(roleName -> roleRepository.findByRoleName(roleName)
                            .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }

        return userService.saveUser(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        String token = authenticationService.authenticate(request.getUsername(), request.getPassword());
        return new AuthResponse(token);
    }
/*    public AuthResponse login(@RequestBody AuthRequest request) {
        String token = authenticationService.authenticate(request.getUsername(), request.getPassword());
        return new AuthResponse(token);
    }*/
}

package com.example.TaskManagerApp.config;

import com.example.TaskManagerApp.entity.Role;
import com.example.TaskManagerApp.repository.RoleRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupConfig {

    @Bean
    public ApplicationRunner dataLoader(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByRoleName("USER").isEmpty()) {
                roleRepository.save(Role.builder().roleName("USER").build());
            }
            if (roleRepository.findByRoleName("ADMIN").isEmpty()) {
                roleRepository.save(Role.builder().roleName("ADMIN").build());
            }
        };
    }
}

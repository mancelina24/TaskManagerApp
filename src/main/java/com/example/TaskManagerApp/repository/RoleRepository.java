package com.example.TaskManagerApp.repository;

import com.example.TaskManagerApp.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository <Role, String>{

    Optional<Role> findByRoleName(String roleName);
}

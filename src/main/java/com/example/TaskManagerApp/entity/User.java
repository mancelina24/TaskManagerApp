package com.example.TaskManagerApp.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Document(collection = "users") //MongoDB'deki "users" koleksiyonuna karşılık gelir.
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

    @Id //@Id → MongoDB'nin _id alanı ile eşleşir.
    private String id;
    private String username;
    private String email;
    private String password;

    // Kullanıcının rolleri
    private Set<Role> roles = new HashSet<>();

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.username;  // Spring Security giriş için
    }

    @Override
    public String getPassword() {
        return password;
    }

    // kullanıcı adı gibi davranacak başka bir getter
    public String getUserNameField() {
        return username;

    }


}

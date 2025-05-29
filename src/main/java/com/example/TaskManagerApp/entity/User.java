package com.example.TaskManagerApp.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "users") //MongoDB'deki "users" koleksiyonuna karşılık gelir.
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id //@Id → MongoDB'nin _id alanı ile eşleşir.
    private String id;
    private String username;
    private String email;
    private String password;

    // Kullanıcının rolleri
    private Set<Role> roles = new HashSet<>();


}

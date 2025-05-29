package com.example.TaskManagerApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Tüm endpoint'lere CORS uygulanacak
                .allowedOrigins("http://localhost:5173", "http://localhost:5174","http://localhost:5175") // Sadece bu domain'e izin ver // React portu
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // İzin verilen HTTP metodları
                .allowedHeaders("*") // Tüm header'lara izin ver
                .allowCredentials(true) // Cookie ve Authorization header'a izin ver
                .maxAge(3600); // OPTIONS isteği önbellekte 1 saat tutulur
    }
}

package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/h2-console/**").permitAll() // H2 konsoluna erişimi açar
                .requestMatchers("/api/students/**").permitAll() // Öğrenci API'sine erişimi açar
                .requestMatchers("/api/**").permitAll() // Diğer API endpointlerine erişimi açar
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.disable()) // H2 konsoluyla çalışırken ve POST istekleriyle çalışırken CSRF korumasını devre dışı bırakmak gerekebilir
            .headers(headers -> headers.frameOptions().disable()); // H2 konsolu iframe kullanır, bu nedenle frameOptions'u devre dışı bırakmak gerekebilir

        return http.build();
    }

    @Bean
    public WebMvcConfigurer securityConfigCorsConfigurer() { // Bean adını değiştirdik
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:3000").allowedMethods("*");
            }
        };
    }
}

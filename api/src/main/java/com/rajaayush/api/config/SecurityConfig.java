package com.rajaayush.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF for simplicity; enable it in production with proper config
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/customer/").permitAll() // Open access to this endpoint
                        .anyRequest().authenticated() // Secure all other endpoints
                )
                .httpBasic(); // Use basic authentication for now (or configure JWT as needed)

        return http.build();
    }
}

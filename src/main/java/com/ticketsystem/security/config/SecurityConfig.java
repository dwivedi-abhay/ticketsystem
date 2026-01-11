package com.ticketsystem.security.config;

import com.ticketsystem.security.filter.JwtAuthFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/bookings/**").authenticated()
                        .anyRequest().permitAll()
                )
                .addFilterBefore(
                        new JwtAuthFilter(),
                        org.springframework.security.web.authentication.
                                UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}

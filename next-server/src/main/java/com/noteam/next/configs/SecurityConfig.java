package com.noteam.next.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.noteam.next.filter.SecurityFilter;

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, SecurityFilter debugFilter) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                // .authorizeHttpRequests(auth -> auth
                // .requestMatchers(HttpMethod.POST, "/api/v1/admin").permitAll()
                // .requestMatchers("/api/v1/login").permitAll()
                // .requestMatchers("/api/v1/me").permitAll()
                // .requestMatchers("/api/v1/order/**").hasAnyAuthority("admin", "employee")
                // .anyRequest().authenticated())
                .addFilterBefore(debugFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}

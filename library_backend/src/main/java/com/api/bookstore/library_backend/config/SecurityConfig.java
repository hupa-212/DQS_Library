package com.api.bookstore.library_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class SecurityConfig {

    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request
                        // Public endpoints
                        .requestMatchers("/auth/**").permitAll()
                        
                        // GET endpoints - cho phép tất cả (đã authenticate)
                        .requestMatchers("GET", "/api/categories/**").permitAll()
                        .requestMatchers("GET", "/api/books/**").permitAll()
                        .requestMatchers("GET", "/api/roles/**").authenticated()
                        .requestMatchers("GET", "/api/permissions/**").authenticated()
                        .requestMatchers("GET", "/api/users/**").authenticated()
                        .requestMatchers("GET", "/api/orders/**").authenticated()
                        .requestMatchers("GET", "/api/order-details/**").authenticated()
                        
                        // POST, PUT, DELETE endpoints - chỉ ADMIN
                        .requestMatchers("POST", "/api/categories/**").hasRole("ADMIN")
                        .requestMatchers("PUT", "/api/categories/**").hasRole("ADMIN")
                        .requestMatchers("DELETE", "/api/categories/**").hasRole("ADMIN")
                        
                        .requestMatchers("POST", "/api/books/**").hasRole("ADMIN")
                        .requestMatchers("PUT", "/api/books/**").hasRole("ADMIN")
                        .requestMatchers("DELETE", "/api/books/**").hasRole("ADMIN")
                        
                        .requestMatchers("POST", "/api/roles/**").hasRole("ADMIN")
                        .requestMatchers("PUT", "/api/roles/**").hasRole("ADMIN")
                        .requestMatchers("DELETE", "/api/roles/**").hasRole("ADMIN")
                        
                        .requestMatchers("POST", "/api/permissions/**").hasRole("ADMIN")
                        .requestMatchers("PUT", "/api/permissions/**").hasRole("ADMIN")
                        .requestMatchers("DELETE", "/api/permissions/**").hasRole("ADMIN")
                        
                        .requestMatchers("POST", "/api/users/**").hasRole("ADMIN")
                        .requestMatchers("PUT", "/api/users/**").hasRole("ADMIN")
                        .requestMatchers("DELETE", "/api/users/**").hasRole("ADMIN")
                        
                        .requestMatchers("POST", "/api/orders/**").authenticated()
                        .requestMatchers("PUT", "/api/orders/**").hasRole("ADMIN")
                        .requestMatchers("DELETE", "/api/orders/**").hasRole("ADMIN")
                        
                        .requestMatchers("POST", "/api/order-details/**").hasRole("ADMIN")
                        .requestMatchers("PUT", "/api/order-details/**").hasRole("ADMIN")
                        .requestMatchers("DELETE", "/api/order-details/**").hasRole("ADMIN")
                        
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "http://localhost:5173",
                "http://localhost:8080",
                "http://127.0.0.1:3000",
                "http://127.0.0.1:5173"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
}
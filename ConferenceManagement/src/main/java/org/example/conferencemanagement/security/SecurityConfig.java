package org.example.conferencemanagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())   // disable CSRF (for testing APIs)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()   // allow all requests
                )
                .formLogin(form -> form.disable())  // disable default login page
                .httpBasic(basic -> basic.disable()); // disable basic auth popup

        return http.build();
    }
}


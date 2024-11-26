package com.example.Caver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .requestMatchers("/", "/board/**", "/news/**", "/weather/**", "/stock/**", "/css/**", "/img/**", "/js/**")
                .permitAll()
                .anyRequest().authenticated()
            .and()
            .csrf().disable();

        return http.build();
    }
}

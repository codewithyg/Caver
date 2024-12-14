package com.example.Caver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 사이트 접근 보안 처리
 */

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .requestMatchers("/", "/board/**", "/news/**", "/global-news/**", "/weather/**", "/stock/**", "/css/**", "/img/**", "/js/**")
                .permitAll()
                .anyRequest().authenticated()
            .and()
            .csrf().disable();

        return http.build();
    }
}

package com.kr.realworldspringboot2.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService () {
        return new InMemoryUserDetailsManager();
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder())
                .and()
                .inMemoryAuthentication().withUser("user1")
                .password("$2a$10$0W1VWLXgCZXsMUHbDXoJj.NiQgKl21UaZVHs331eArkI7UfHcjIaO") //1111
                .roles("USER");

        http
            .csrf().disable()
            .formLogin().permitAll()
            .and()
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .authenticationManager(authenticationManagerBuilder.build());

        return http.build();
    }




}

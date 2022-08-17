package com.kr.realworldspringboot2.config;


import com.kr.realworldspringboot2.security.ApiCheckFilter;
import com.kr.realworldspringboot2.security.ApiLoginFilter;
import com.kr.realworldspringboot2.security.AuthUserDetailsService;
import com.kr.realworldspringboot2.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {
    @Value("${real-world.allowed-Origin}")
    String allowedOrigin;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private AuthUserDetailsService authUserDetailsService;

    @Bean
    public ApiCheckFilter apiCheckFilter() {
        return new ApiCheckFilter(jwtUtil());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin(allowedOrigin);
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //AuthenticationManager설정
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(authUserDetailsService).passwordEncoder(passwordEncoder());
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http
            .csrf().disable()
            .cors().configurationSource(corsConfigurationSource())
            .and()
            .authorizeRequests()
            .antMatchers("/all").permitAll()
            .antMatchers("/api/profiles/*").permitAll()
            .antMatchers("/api/articles").permitAll()
            .antMatchers("/api/articles/*").permitAll()
            .antMatchers("/api/articles/*/comments").permitAll()
            .antMatchers("/api/users").permitAll()
            .antMatchers("/api/tags").permitAll()
            .antMatchers("/user").hasRole("USER")
            .antMatchers("/manager").hasRole("MANAGER")
            .antMatchers("/admin").hasRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authenticationManager(authenticationManager)
            .addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(apiLoginFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    public ApiLoginFilter apiLoginFilter(AuthenticationManager authenticationManager) {
        ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/api/users/login",jwtUtil());
        apiLoginFilter.setAuthenticationManager(authenticationManager);
        return apiLoginFilter;
    }

    @Bean
    public JWTUtil jwtUtil() {
        return new JWTUtil();
    }


}

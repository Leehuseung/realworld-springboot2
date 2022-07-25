package com.kr.realworldspringboot2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RealworldSpringboot2Application {

    public static void main(String[] args) {
        SpringApplication.run(RealworldSpringboot2Application.class, args);
    }

}

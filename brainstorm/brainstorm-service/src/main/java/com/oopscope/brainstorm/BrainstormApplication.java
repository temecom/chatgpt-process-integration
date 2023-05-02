package com.oopscope.brainstorm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableWebFluxSecurity
public class BrainstormApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrainstormApplication.class, args);
    }
}


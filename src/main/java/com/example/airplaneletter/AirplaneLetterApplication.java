package com.example.airplaneletter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AirplaneLetterApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirplaneLetterApplication.class, args);
    }

}

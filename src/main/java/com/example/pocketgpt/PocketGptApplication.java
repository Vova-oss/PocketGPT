package com.example.pocketgpt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PocketGptApplication {

    public static void main(String[] args) {
        SpringApplication.run(PocketGptApplication.class, args);
    }

}

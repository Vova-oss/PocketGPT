package com.example.pocketgpt.gpt.temptest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/test")
public class TestController {

    @GetMapping
    public String testService() {
        return "Service is working correctly!";
    }

}

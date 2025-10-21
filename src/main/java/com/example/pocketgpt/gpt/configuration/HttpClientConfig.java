package com.example.pocketgpt.gpt.configuration;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class HttpClientConfig {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)  // подключение к серверу
                .writeTimeout(30, TimeUnit.SECONDS)    // время отправки запроса
                .readTimeout(90, TimeUnit.SECONDS)     // ожидание ответа
                .callTimeout(150, TimeUnit.SECONDS)    // максимальное время всего запроса
                .build();
    }
}

package com.example.pocketgpt.gpt.service;

import com.example.pocketgpt.gpt.model.GptMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpenAiService {
    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";
    private final OkHttpClient client;
    private final ObjectMapper mapper;

    @Value("${openai.api.key}")
    private String apiKey;

    public OpenAiService(OkHttpClient client, ObjectMapper mapper) {
        this.client = client;
        this.mapper = mapper;
    }

//    protected String askGpt(String message) {
//        try {
//            String json = """
//                {
//                  "model": "gpt-4o-mini",
//                  "messages": [{"role": "user", "content": "%s"}]
//                }
//                """.formatted(message);
//
//            RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
//            Request request = new Request.Builder()
//                    .url(OPENAI_URL)
//                    .header("Authorization", "Bearer " + apiKey)
//                    .post(body)
//                    .build();
//
//            try (Response response = client.newCall(request).execute()) {
//                if (!response.isSuccessful())
//                    return "Error: " + response;
//
//                JsonNode root = mapper.readTree(response.body().string());
//                return root.path("choices").get(0).path("message").path("content").asText();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Error: " + e.getMessage();
//        }
//    }

    protected String askGpt(List<GptMessage> messages) {
        try {
            String json = """
                {
                  "model": "gpt-4o-mini",
                  "messages": %s
                }
                """.formatted(messages);

            RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
            Request request = new Request.Builder()
                    .url(OPENAI_URL)
                    .header("Authorization", "Bearer " + apiKey)
                    .post(body)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful())
                    return "Error: " + response;

                JsonNode root = mapper.readTree(response.body().string());
                return root.path("choices").get(0).path("message").path("content").asText();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}


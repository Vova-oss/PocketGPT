package com.example.pocketgpt.utils;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

public class StringUtils {

    public static String trimToBytes(String input, int maxBytes) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (int i = 0; i < input.length(); i++) {
            String ch = input.substring(i, i + 1);
            byte[] b = ch.getBytes(StandardCharsets.UTF_8);
            if (out.size() + b.length > maxBytes) {
                break;
            }
            out.writeBytes(b);
        }
        return out.toString(StandardCharsets.UTF_8);
    }


}

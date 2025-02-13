package com.et.clerk.service;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ClerkService {

    @Value("${clerk.api-key}")
    private String apiKey;

    private final OkHttpClient client = new OkHttpClient();

    public String getUserInfo(String userId) throws IOException {
        Request request = new Request.Builder()
                .url("https://api.clerk.dev/v1/users/" + userId)
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }
    }
}
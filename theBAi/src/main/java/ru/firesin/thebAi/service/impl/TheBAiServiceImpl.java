package ru.firesin.thebAi.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.firesin.thebAi.models.ChatRequest;
import ru.firesin.thebAi.service.TheBAiService;

/**
 * Author:    firesin
 * Date:      30.10.2023
 */

@Service
@Slf4j
public class TheBAiServiceImpl implements TheBAiService {

    private final String apiUrlTheBAi;
    private final String apiKey;

    public TheBAiServiceImpl(@Value("${TheBAi.urlPostTheBAi}") String apiUrlTheBAi,
                             @Value("${TheBAi.api-key}") String apiKey) {
        this.apiUrlTheBAi = apiUrlTheBAi;
        this.apiKey = apiKey;
    }

    @Override
    public String getAnswer(String text) {
        var txt = sendChatCompletionRequest("TheBAi", "user", text, false, 0.8);
        return "null";
    }

    public String sendChatCompletionRequest(String model, String role, String content, boolean stream, double temperature) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the request body
        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setModel(model);
        chatRequest.addMessage(role, content);
        chatRequest.setStream(stream);
        chatRequest.setModelParams(temperature);

        HttpEntity<ChatRequest> requestEntity = new HttpEntity<>(chatRequest, headers);

        // Send the HTTP request
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(apiUrlTheBAi, HttpMethod.POST, requestEntity, String.class);

        return response.getBody();
    }
}

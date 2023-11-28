package ru.firesin.thebAi.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:    firesin
 * Date:      30.10.2023
 */

public class ChatRequest {
    private String model;
    private List<ChatMessage> messages;
    private boolean stream;
    private ModelParams modelParams;

    public ChatRequest() {
        this.messages = new ArrayList<>();
        this.modelParams = new ModelParams();
    }

    // Геттеры и сеттеры для всех полей

    public void addMessage(String role, String content) {
        ChatMessage message = new ChatMessage();
        message.setRole(role);
        message.setContent(content);
        messages.add(message);
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setStream(boolean stream) {
        this.stream = stream;
    }

    public void setModelParams(double temperature) {
        this.modelParams.setTemperature(temperature);
    }
}

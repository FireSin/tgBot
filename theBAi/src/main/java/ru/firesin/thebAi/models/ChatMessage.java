package ru.firesin.thebAi.models;

import lombok.Data;

/**
 * Author:    firesin
 * Date:      30.10.2023
 */

@Data
public class ChatMessage {
    private String role;
    private String content;
}

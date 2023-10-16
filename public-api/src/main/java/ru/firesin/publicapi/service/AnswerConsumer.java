package ru.firesin.publicapi.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * Author:    firesin
 * Date:      16.10.2023
 */
public interface AnswerConsumer {
    void consume(SendMessage sendMessage);
}

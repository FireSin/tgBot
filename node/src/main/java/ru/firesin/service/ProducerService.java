package ru.firesin.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * Author:    firesin
 * Date:      18.10.2023
 */
public interface ProducerService {
    void produceAnswer(SendMessage sendMessage);
}

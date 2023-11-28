package ru.firesin.thebAi.service;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Author:    firesin
 * Date:      30.10.2023
 */
public interface ConsumerService {

    void consumeTheBAiMessage(Update update);
}

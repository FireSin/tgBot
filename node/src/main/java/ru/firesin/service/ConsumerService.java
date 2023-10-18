package ru.firesin.service;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Author:    firesin
 * Date:      18.10.2023
 */
public interface ConsumerService {

    void consumeTextMessage(Update update);
    void consumeDocMessage(Update update);
    void consumePhotoMessage(Update update);
}

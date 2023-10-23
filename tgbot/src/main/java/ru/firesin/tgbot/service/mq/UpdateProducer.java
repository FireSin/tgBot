package ru.firesin.tgbot.service.mq;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Author:    firesin
 * Date:      16.10.2023
 */
public interface UpdateProducer {
    void produce(String rabbitQueue, Update update);
}

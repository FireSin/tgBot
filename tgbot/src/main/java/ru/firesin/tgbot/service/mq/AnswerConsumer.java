package ru.firesin.tgbot.service.mq;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * Author:    firesin
 * Date:      16.10.2023
 */
public interface AnswerConsumer {
    void consume(SendMessage sendMessage);
}

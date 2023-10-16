package ru.firesin.publicapi.service;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * Author:    firesin
 * Date:      16.10.2023
 */

@Slf4j
public class AnswerConsumerImpl implements AnswerConsumer{
    @Override
    public void consume(SendMessage sendMessage) {
    }
}

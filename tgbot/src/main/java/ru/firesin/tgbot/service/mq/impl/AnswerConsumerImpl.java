package ru.firesin.tgbot.service.mq.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.firesin.tgbot.controller.UpdateController;
import ru.firesin.tgbot.service.mq.AnswerConsumer;

import static ru.firesin.feature.rabbitMq.RabbitQueue.ANSWER_MESSAGE;

/**
 * Author:    firesin
 * Date:      16.10.2023
 */

@Slf4j
@AllArgsConstructor
@Service
public class AnswerConsumerImpl implements AnswerConsumer {

    private final UpdateController updateController;
    @Override
    @RabbitListener(queues = ANSWER_MESSAGE)
    public void consume(SendMessage sendMessage) {
        updateController.setView(sendMessage);
    }
}

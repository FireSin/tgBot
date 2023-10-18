package ru.firesin.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.firesin.service.ProducerService;

import static ru.firesin.RabbitQueue.ANSWER_MESSAGE;

/**
 * Author:    firesin
 * Date:      18.10.2023
 */

@Service
@AllArgsConstructor
public class ProducerServiceImpl implements ProducerService {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void produceAnswer(SendMessage sendMessage) {
        rabbitTemplate.convertAndSend(ANSWER_MESSAGE, sendMessage);
    }
}

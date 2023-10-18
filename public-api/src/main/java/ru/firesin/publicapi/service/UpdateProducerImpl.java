package ru.firesin.publicapi.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Author:    firesin
 * Date:      16.10.2023
 */

@Slf4j
@AllArgsConstructor
public class UpdateProducerImpl implements UpdateProducer{

    private final RabbitTemplate rabbitTemplate;
    @Override
    public void produce(String rabbitQueue, Update update) {
      log.info(update.getMessage().getText());
      rabbitTemplate.convertAndSend(rabbitQueue, update);
    }
}

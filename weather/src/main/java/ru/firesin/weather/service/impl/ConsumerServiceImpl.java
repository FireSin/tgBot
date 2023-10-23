package ru.firesin.weather.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.firesin.weather.service.ConsumerService;
import ru.firesin.weather.service.ProducerService;

import static ru.firesin.feature.rabbitMq.RabbitQueue.*;


/**
 * Author:    firesin
 * Date:      18.10.2023
 */
@Service
@Slf4j
@AllArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {

    private final ProducerService producerService;

    @Override
    @RabbitListener(queues = TEXT_MESSAGE_UPDATE)
    public void consumeTextMessage(Update update) {
        log.info("Текстовое сообщение получено");

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChat().getId());
        sendMessage.setMessageThreadId(update.getMessage().getMessageThreadId());
        sendMessage.setText("Hello from Node!");
        producerService.produceAnswer(sendMessage);
    }

    @Override
    @RabbitListener(queues = DOC_MESSAGE_UPDATE)
    public void consumeDocMessage(Update update) {
        log.debug("Документ сообщение получено");
    }

    @Override
    @RabbitListener(queues = PHOTO_MESSAGE_UPDATE)
    public void consumePhotoMessage(Update update) {
        log.debug("Фото сообщение получено");
    }
}

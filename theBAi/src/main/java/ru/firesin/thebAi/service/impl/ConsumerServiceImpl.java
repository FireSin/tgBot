package ru.firesin.thebAi.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.firesin.rabbitMq.producer.UpdateProducer;
import ru.firesin.thebAi.service.ConsumerService;
import ru.firesin.thebAi.service.TheBAiService;

import static ru.firesin.rabbitMq.Queue.RabbitQueue.*;

/**
 * Author:    firesin
 * Date:      30.10.2023
 */

@Service
@AllArgsConstructor
@Slf4j
public class ConsumerServiceImpl implements ConsumerService {

    private final TheBAiService chat;
    private final UpdateProducer updateProducer;
    @Override
    @RabbitListener(queues = CHAT_MESSAGE_UPDATE)
    public void consumeTheBAiMessage(Update update) {
        log.debug("Текстовое сообщение получено в TheBAi получено");
        String result;
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChat().getId());
        sendMessage.setMessageThreadId(update.getMessage().getMessageThreadId());
        result = chat.getAnswer(update.getMessage().getText());
        sendMessage.setText(result);
        updateProducer.produce(CHAT_MESSAGE_ANSWER, sendMessage);
    }
}

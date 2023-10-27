package ru.firesin.weather.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.firesin.weather.service.ConsumerService;
import ru.firesin.weather.service.ProducerService;
import ru.firesin.weather.service.WeatherService;

import static ru.firesin.rabbitMq.RabbitQueue.*;


/**
 * Author:    firesin
 * Date:      18.10.2023
 */
@Service
@Slf4j
@AllArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {

    private final ProducerService producerService;
    private final WeatherService weatherService;

    @Override
    @RabbitListener(queues = WEATHER_MESSAGE_UPDATE)
    public void consumeWeatherMessage(Update update) {
        log.info("Текстовое сообщение получено в погоде получено");

        var result = weatherService.getWeather(update.getMessage().getText());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChat().getId());
        sendMessage.setMessageThreadId(update.getMessage().getMessageThreadId());
        sendMessage.setText(result);
        producerService.produceAnswer(sendMessage);
    }
}

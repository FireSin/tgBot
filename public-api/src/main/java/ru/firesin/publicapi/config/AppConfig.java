package ru.firesin.publicapi.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.firesin.publicapi.controller.TelegramBot;
import ru.firesin.publicapi.controller.UpdateController;
import ru.firesin.publicapi.service.UpdateProducer;
import ru.firesin.publicapi.service.UpdateProducerImpl;
import ru.firesin.publicapi.utils.MessageUtils;

import static ru.firesin.RabbitQueue.*;

/**
 * Author:    firesin
 * Date:      16.10.2023
 */

@Configuration
@ConfigurationProperties(prefix = "bot")
@PropertySource("classpath:bot-config.yml")
public class AppConfig {

    @Value("${token}")
    private String token;

    @Value("${name}")
    private String name;


    @Bean
    public UpdateProducer updateProducer(RabbitTemplate rabbitTemplate){
        return new UpdateProducerImpl(rabbitTemplate);
    }

    @Bean
    public MessageUtils messageUtils(){
        return new MessageUtils();
    }

    @Bean
    public UpdateController updateController(MessageUtils messageUtils, UpdateProducer updateProducer){
        return new UpdateController(messageUtils, updateProducer);
    }

    @Bean
    public TelegramBot telegramBot(UpdateController updateController) {
        return new TelegramBot(name, token, updateController);
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue textMessageQueue() {
        return new Queue(TEXT_MESSAGE_UPDATE);
    }

    @Bean
    public Queue photoMessageQueue() {
        return new Queue(PHOTO_MESSAGE_UPDATE);
    }

    @Bean
    public Queue docMessageQueue() {
        return new Queue(DOC_MESSAGE_UPDATE);
    }

    @Bean
    public Queue answerMessageQueue() {
        return new Queue(ANSWER_MESSAGE_UPDATE);
    }
}

package ru.firesin.publicapi.service;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Author:    firesin
 * Date:      16.10.2023
 */

@Slf4j
public class UpdateProducerImpl implements UpdateProducer{
    @Override
    public void produce(String rabbitQueue, Update update) {
      log.info(update.getMessage().getText());
    }
}

package ru.firesin.publicapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.firesin.publicapi.service.UpdateProducer;
import ru.firesin.publicapi.utils.MessageUtils;

/**
 * Author:    firesin
 * Date:      16.10.2023
 */

@Slf4j
public class UpdateController {

    private final MessageUtils messageUtils;
    private final UpdateProducer updateProducer;
    private TelegramBot telegramBot;


    public UpdateController(MessageUtils messageUtils, UpdateProducer updateProducer) {
        this.messageUtils = messageUtils;
        this.updateProducer = updateProducer;
    }

    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void proccesUpdate(Update update) {
        var sndMsg = messageUtils.generateSendMessage(update, "Куку епта!");
        setView(sndMsg);
    }

    private void setView(SendMessage sndMsg) {
        telegramBot.sendAnswer(sndMsg);
    }
}






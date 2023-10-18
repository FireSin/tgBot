package ru.firesin.publicapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.firesin.publicapi.service.UpdateProducer;
import ru.firesin.publicapi.utils.MessageUtils;

import static ru.firesin.RabbitQueue.*;

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

    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Полученный Update null");
            return;
        }
        if (update.getMessage() != null) {
            distributeMessageByType(update);
        } else {
            log.error("Не поддерживаемый формат сообщения " + update);
        }
    }

    private void distributeMessageByType(Update update) {
        var msg = update.getMessage();
        if (msg.getText() != null) {
            processTextMessage(update);
        } else if (msg.getDocument() != null) {
            processDocumentMessage(update);
        } else if (msg.getPhoto() != null) {
            processPhotoMessage(update);
        } else {
            setUnsupportedTypeMessage(update);
        }
    }

    private void setUnsupportedTypeMessage(Update update) {
        var sendMessage = messageUtils.generateSendMessage(update, "Неподдерживаемый формат сообщения");
        setView(sendMessage);
    }

    private void processPhotoMessage(Update update) {
        updateProducer.produce(PHOTO_MESSAGE_UPDATE, update);
    }

    private void processDocumentMessage(Update update) {
        updateProducer.produce(DOC_MESSAGE_UPDATE, update);
    }

    private void processTextMessage(Update update) {
        updateProducer.produce(TEXT_MESSAGE_UPDATE, update);
    }

    public void setView(SendMessage sndMsg) {
        telegramBot.sendAnswer(sndMsg);
    }
}






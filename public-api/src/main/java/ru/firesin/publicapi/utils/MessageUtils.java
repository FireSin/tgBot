package ru.firesin.publicapi.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Author:    firesin
 * Date:      16.10.2023
 */

@Component
public class MessageUtils {

    public SendMessage generateSendMessage(Update update, String text) {
        var message = update.getMessage();
        var sendMsg = new SendMessage();
        sendMsg.setChatId(message.getChatId());
        sendMsg.setText(text);
        return sendMsg;
    }
}

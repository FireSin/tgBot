package ru.firesin.utils;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Author:    firesin
 * Date:      16.10.2023
 */

@Component
public class MessageUtilsImpl implements MessageUtils{

    public SendMessage generateSendMessage(Update update, String text) {
        SendMessage sendMsg = new SendMessage();
        if (update.hasMessage()) {
            sendMsg.setChatId(update.getMessage().getChatId());
        } else if (update.hasCallbackQuery()) {
            sendMsg.setChatId(update.getCallbackQuery().getFrom().getId());
        } else if (update.hasEditedMessage()) {
            sendMsg.setChatId(update.getEditedMessage().getChatId());
        }
        sendMsg.setText(text);
        return sendMsg;
    }

    public SendMessage generateSendMessage(Message msg, String text) {
        SendMessage toSend = new SendMessage();
        toSend.setChatId(msg.getChatId());
        toSend.setText(text);
        return toSend;
    }
}

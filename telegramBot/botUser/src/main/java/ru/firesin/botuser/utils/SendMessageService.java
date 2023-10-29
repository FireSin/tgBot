package ru.firesin.botuser.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Author:    firesin
 * Date:      28.10.2023
 */
public interface SendMessageService {
    void sendMessage(Update update, String text);

    void sendMessage(SendMessage msg);

    void sendMessage(SendMessage sendMessage, String s);
}

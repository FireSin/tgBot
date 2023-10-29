package ru.firesin.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Author:    firesin
 * Date:      28.10.2023
 */
public interface MessageUtils {

    SendMessage generateSendMessage(Update update, String text);

    SendMessage generateSendMessage(Message msg, String text);
}

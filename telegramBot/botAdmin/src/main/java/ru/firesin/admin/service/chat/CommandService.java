package ru.firesin.admin.service.chat;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Author:    firesin
 * Date:      26.10.2023
 */
public interface CommandService {
    Boolean itsCommand(Update update);

    SendMessage processCommand(Update update);
}

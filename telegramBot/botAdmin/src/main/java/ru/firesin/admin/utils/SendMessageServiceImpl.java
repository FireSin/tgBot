package ru.firesin.admin.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.firesin.admin.controller.TelegramBot;
import ru.firesin.utils.MessageUtils;

/**
 * Author:    firesin
 * Date:      28.10.2023
 */

@Service
@AllArgsConstructor
public class SendMessageServiceImpl implements SendMessageService {

    TelegramBot telegramBot;
    MessageUtils messageUtils;

    @Override
    public void sendMessage(Update update, String text) {
        var sendMessage = messageUtils.generateSendMessage(update, text);
        telegramBot.sendAnswer(sendMessage);
    }

    @Override
    public void sendMessage(SendMessage msg, String text) {
        msg.setText(text);
        telegramBot.sendAnswer(msg);
    }

    @Override
    public void sendMessage(SendMessage msg) {
        telegramBot.sendAnswer(msg);
    }
}

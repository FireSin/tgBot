package ru.firesin.tgbot.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Author:    firesin
 * Date:      16.10.2023
 */

@Slf4j
@Controller
public class TelegramBot extends TelegramWebhookBot {

    private final UpdateProcessor updateProcessor;
    private final String botUsername;
    private final String botUri;

    public TelegramBot(@Value("${bot.name}") String botUsername, @Value("${bot.token}") String botToken, @Value("${bot.uri}") String botUri, UpdateProcessor updateProcessor) {
        super(botToken);
        this.updateProcessor = updateProcessor;
        this.botUsername = botUsername;
        this.botUri = botUri;
    }

    @PostConstruct
    private void initializeBot() throws TelegramApiException {
        updateProcessor.registerBot(this);
        this.setWebhook(new SetWebhook(botUri));
        log.info("Бот " + botUsername + " создан");
    }

    public void sendAnswer(SendMessage msg){
        try {
            execute(msg);
            log.info("Ответ отправлен");
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения");
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        updateProcessor.processUpdate(update);
        return null;
    }

    @Override
    public String getBotPath() {
        return "/update";
    }
}

package ru.firesin.publicapi.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Author:    firesin
 * Date:      16.10.2023
 */

@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private final UpdateController updateController;
    private final String botUsername;

    public TelegramBot(String botUsername, String botToken, UpdateController updateController) {
        super(botToken);
        this.botUsername = botUsername;
        this.updateController = updateController;
    }

    @PostConstruct
    public void initializeBot() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(this);
        updateController.registerBot(this);
        log.info("Бот " + botUsername + " создан");
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("Получено сообщение от " + update.getMessage().getFrom().getUserName());
        updateController.processUpdate(update);
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
}

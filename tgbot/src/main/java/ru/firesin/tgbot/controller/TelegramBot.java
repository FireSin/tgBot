package ru.firesin.tgbot.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.firesin.tgbot.config.CommandConfig;

/**
 * Author:    firesin
 * Date:      16.10.2023
 */

@Slf4j
@Controller
public class TelegramBot extends TelegramLongPollingBot {

    private final CommandConfig commandConfig;
    private final UpdateController updateController;
    private final String botUsername;

    public TelegramBot(@Value("${bot.name}") String botUsername, @Value("${bot.token}") String botToken, CommandConfig commandConfig, UpdateController updateController) {
        super(botToken);
        this.commandConfig = commandConfig;
        this.updateController = updateController;
        this.botUsername = botUsername;
    }

    @PostConstruct
    private void initializeBot() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(this);
        updateController.registerBot(this);
        this.execute(new SetMyCommands(commandConfig.getBotCommands(), new BotCommandScopeDefault(), null));
        log.info("Бот " + botUsername + " создан");
    }

    @Override
    public void onUpdateReceived(Update update) {
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

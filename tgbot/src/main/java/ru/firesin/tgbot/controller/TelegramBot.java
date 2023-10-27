package ru.firesin.tgbot.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.firesin.tgbot.utils.BotCommands;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:    firesin
 * Date:      16.10.2023
 */

@Slf4j
@Controller
public class TelegramBot extends TelegramWebhookBot {
    private final String botUsername;

    public TelegramBot(@Value("${bot.name}") String botUsername,
                       @Value("${bot.token}") String botToken,
                       @Value("${bot.uri}") String botUri) throws TelegramApiException {
        super(botToken);
        this.botUsername = botUsername;
        try {
            this.setWebhook(new SetWebhook(botUri));
        } catch (TelegramApiException e) {
            log.error("Ошибка создания бота: " + e.getMessage());
            throw e;
        }
        log.debug("Бот " + botUsername + " создан");
    }

    @PostConstruct
    private void setMenu() {
        List<BotCommand> commands = new ArrayList<>();
        List<BotCommands> userCommands = BotCommands.getUserCommands();

        for (BotCommands botCommand : userCommands) {
            commands.add(new BotCommand(botCommand.getCommand(), botCommand.getDescription()));
        }
        try {
            this.execute(new SetMyCommands(commands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Ошибка создания меню: " + e.getMessage());
            throw new RuntimeException(e);
        }
        log.debug("Меню бота создано");
    }


    public void sendAnswer(SendMessage msg){
        try {
            execute(msg);
            log.debug("Ответ отправлен");
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
        return null;
    }

    @Override
    public String getBotPath() {
        return "/update";
    }
}

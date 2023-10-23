package ru.firesin.tgbot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import ru.firesin.tgbot.service.mq.UpdateProducer;
import ru.firesin.tgbot.config.CommandConfig;
import ru.firesin.tgbot.utils.MessageUtils;

import java.util.Optional;

import static ru.firesin.feature.rabbitMq.RabbitQueue.*;

/**
 * Author:    firesin
 * Date:      16.10.2023
 */

@Slf4j
@Controller
public class UpdateController {

    private final MessageUtils messageUtils;
    private final UpdateProducer updateProducer;
    private final CommandConfig commandConfig;
    private TelegramBot telegramBot;



    public UpdateController(MessageUtils messageUtils, UpdateProducer updateProducer, CommandConfig commandConfig) {
        this.messageUtils = messageUtils;
        this.updateProducer = updateProducer;
        this.commandConfig = commandConfig;
    }

    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Полученный Update null");
            return;
        }
        if (update.getMessage() != null) {
            distributeMessageByType(update);
        } else {
            log.error("Не поддерживаемый формат сообщения " + update);
        }
    }

    private void distributeMessageByType(Update update) {
        var msg = update.getMessage();
        if (msg.hasText()) {
            processTextMessage(update);
        } else {
            setUnsupportedTypeMessage(update);
        }
    }

    private void setUnsupportedTypeMessage(Update update) {
        var sendMessage = messageUtils.generateSendMessage(update, "Неподдерживаемый формат сообщения");
        setView(sendMessage);
    }

    private void processTextMessage(Update update) {
        Optional<BotCommand> command = commandConfig.itsCommand(update);
        switch (command.map(BotCommand::getCommand).orElse("")) {
            case "/weather":
                System.out.println("Switch to weather");
                break;
            case "/chat":
                System.out.println("Switch to chat");
                break;
            case "/help":
                System.out.println("Info help");
                break;
            default:
                updateProducer.produce(TEXT_MESSAGE_UPDATE, update);
        }
    }

    public void setView(SendMessage sndMsg) {
        telegramBot.sendAnswer(sndMsg);
    }
}






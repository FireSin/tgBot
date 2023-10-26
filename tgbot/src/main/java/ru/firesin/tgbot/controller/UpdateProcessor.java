package ru.firesin.tgbot.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.firesin.tgbot.exceptions.MuExp;
import ru.firesin.tgbot.exceptions.apiHandler;
import ru.firesin.tgbot.service.chat.CommandService;
import ru.firesin.tgbot.service.mq.UpdateProducer;
import ru.firesin.tgbot.service.users.UserService;
import ru.firesin.tgbot.utils.MessageUtils;
import ru.firesin.tgbot.utils.ReplayKeyboard;

import static ru.firesin.feature.rabbitMq.RabbitQueue.TEXT_MESSAGE_UPDATE;
import static ru.firesin.tgbot.jpa.enums.UserRole.ADMIN;

/**
 * Author:    firesin
 * Date:      16.10.2023
 */

@Slf4j
@Controller
public class UpdateProcessor {

    private final MessageUtils messageUtils;
    private final UpdateProducer updateProducer;
    private final UserService userService;
    private final CommandService commandService;
    private final ReplayKeyboard replayKeyboard;
    private TelegramBot telegramBot;



    public UpdateProcessor(MessageUtils messageUtils, UpdateProducer updateProducer, UserService userService, CommandService commandService, ReplayKeyboard replayKeyboard) {
        this.messageUtils = messageUtils;
        this.updateProducer = updateProducer;
        this.userService = userService;
        this.commandService = commandService;
        this.replayKeyboard = replayKeyboard;
    }

    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void processUpdate(Update update) {
        throw new MuExp(update.getMessage().getChatId(), "Поехали");
//        if (update == null) {
//            log.error("Полученный Update null");
//            return;
//        }
//        if (update.getMessage() != null) {
//            distributeMessageByType(update);
//        } else {
//            log.error("Не поддерживаемый формат сообщения " + update);
//        }
    }

    private void distributeMessageByType(Update update) {
        if (update.getMessage().hasContact() && userService.getRole(update.getMessage().getFrom()).equals(ADMIN)) {
            userService.addUser(update.getMessage().getContact());
            log.info("Добавлен контакт с id " + update.getMessage().getContact().getUserId());
            setView(new SendMessage(update.getMessage().getChatId().toString(), "Добавлен контакт с id " + update.getMessage().getContact().getUserId()));
        } else if (commandService.itsCommand(update)) {
            setView(commandService.processCommand(update));
        } else if (update.hasCallbackQuery()) {
            setView(new SendMessage(update.getMessage().getChatId().toString(), "Тыкнули кнопку"));
        } else if (update.getMessage().hasText()) {
            processTextMessage(update);
        } else {
            setUnsupportedTypeMessage(update);
        }
    }

    private void setUnsupportedTypeMessage(Update update) {
        var sendMessage = messageUtils.generateSendMessage(update, "Неподдерживаемый формат сообщения");
        setView(sendMessage);
    }

    private void setUnauthorizedUserMessage(Update update) {
        var sendMessage = messageUtils.generateSendMessage(update, "У вас нет доступа. Для получения доступа напишите @SinnerFire");
        setView(sendMessage);
    }

    private void processTextMessage(Update update) {
        updateProducer.produce(TEXT_MESSAGE_UPDATE, update);
    }

    public void setView(SendMessage sndMsg) {
        sndMsg.setReplyMarkup(replayKeyboard.getReplyKeyboardMarkup());
        telegramBot.sendAnswer(sndMsg);
    }
}






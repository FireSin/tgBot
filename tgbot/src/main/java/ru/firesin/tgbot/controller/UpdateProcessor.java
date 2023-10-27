package ru.firesin.tgbot.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.firesin.tgbot.service.chat.CommandService;
import ru.firesin.tgbot.service.mq.UpdateProducer;
import ru.firesin.tgbot.utils.MessageUtils;
import ru.firesin.users.UserService;

import static ru.firesin.rabbitMq.RabbitQueue.WEATHER_MESSAGE_UPDATE;
import static ru.firesin.users.enums.UserRole.ADMIN;

/**
 * Author:    firesin
 * Date:      16.10.2023
 */

@Slf4j
@Controller
@AllArgsConstructor
public class UpdateProcessor {

    private final MessageUtils messageUtils;
    private final UpdateProducer updateProducer;
    private final UserService userService;
    private final CommandService commandService;
    private final TelegramBot telegramBot;


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
        if (update.getMessage().hasContact() && userService.getRole(update.getMessage().getFrom()).equals(ADMIN)) {
            userService.addUser(update.getMessage().getContact());
            log.info("Добавлен контакт с id " + update.getMessage().getContact().getUserId());
            telegramBot.sendAnswer(new SendMessage(update.getMessage().getChatId().toString(), "Добавлен контакт с id " + update.getMessage().getContact().getUserId()));
        } else if (commandService.itsCommand(update)) {
            telegramBot.sendAnswer(commandService.processCommand(update));
        } else if (update.hasCallbackQuery()) {
            telegramBot.sendAnswer(new SendMessage(update.getMessage().getChatId().toString(), "Тыкнули кнопку"));
        } else if (update.getMessage().hasText()) {
            processTextMessage(update);
        } else {
            setUnsupportedTypeMessage(update);
        }
    }

    private void setUnsupportedTypeMessage(Update update) {
        var sendMessage = messageUtils.generateSendMessage(update, "Неподдерживаемый формат сообщения");
        telegramBot.sendAnswer(sendMessage);
    }

    private void setUnauthorizedUserMessage(Update update) {
        var sendMessage = messageUtils.generateSendMessage(update, "У вас нет доступа.\nДля получения доступа напишите @SinnerFire");
        telegramBot.sendAnswer(sendMessage);
    }

    private void processTextMessage(Update update) {
        updateProducer.produce(WEATHER_MESSAGE_UPDATE, update);
    }

    public void setView(SendMessage sndMsg) {
        telegramBot.sendAnswer(sndMsg);
    }
}






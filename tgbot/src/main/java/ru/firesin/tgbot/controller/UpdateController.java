package ru.firesin.tgbot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.firesin.tgbot.service.mq.UpdateProducer;
import ru.firesin.tgbot.config.CommandConfig;
import ru.firesin.tgbot.service.users.UserService;
import ru.firesin.tgbot.utils.MessageUtils;

import java.util.ArrayList;
import java.util.List;
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

    private final UserService userService;
    private TelegramBot telegramBot;



    public UpdateController(MessageUtils messageUtils, UpdateProducer updateProducer, CommandConfig commandConfig, UserService userService) {
        this.messageUtils = messageUtils;
        this.updateProducer = updateProducer;
        this.commandConfig = commandConfig;
        this.userService = userService;
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
        if (update.getMessage().getFrom().getUserName().equals("SinnerFire")){
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId());
            message.setText("adm: ");
            InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

            // Создание кнопки
            InlineKeyboardButton button1 = new InlineKeyboardButton();
            button1.setText("Кнопка 1");
            button1.setCallbackData("button1");
            InlineKeyboardButton button2 = new InlineKeyboardButton();
            button2.setText("Кнопка 2");
            button2.setCallbackData("button2");

            // Добавление кнопок в ряд
            List<InlineKeyboardButton> row1 = new ArrayList<>();
            row1.add(button1);
            row1.add(button2);
            rowsInline.add(row1);

            markupInline.setKeyboard(rowsInline);
            message.setReplyMarkup(markupInline);

            try {
                telegramBot.execute(message); // Отправка сообщения с инлайн-клавиатурой
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
            }
        }
        Optional<BotCommand> command = commandConfig.itsCommand(update);
        if (command.isPresent()) {
            String commandText = command.get().getCommand();
            if (commandText.startsWith("/weather")) {
                System.out.println("Switch to weather");
            } else if (commandText.startsWith("/chat")) {
                System.out.println("Switch to chat");
            } else if (commandText.startsWith("/help")) {
                System.out.println("Info help");
            } else if (commandText.startsWith("/add_user")) {
                System.out.println("add");
                userService.addUser(update.getMessage().getFrom());
            } else if (commandText.startsWith("/rm_user")) {
                System.out.println("rm");
            }
        } else {
            updateProducer.produce(TEXT_MESSAGE_UPDATE, update);
        }
    }

    public void setView(SendMessage sndMsg) {
        telegramBot.sendAnswer(sndMsg);
    }
}






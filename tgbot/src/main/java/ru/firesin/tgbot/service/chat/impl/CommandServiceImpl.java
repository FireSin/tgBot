package ru.firesin.tgbot.service.chat.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.firesin.tgbot.service.chat.CommandService;
import ru.firesin.tgbot.utils.BotCommand;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Author:    firesin
 * Date:      26.10.2023
 */

@Service
public class CommandServiceImpl implements CommandService {

    private final Map<BotCommand, Function<Message, SendMessage>> commandHandlers = new EnumMap<>(BotCommand.class);

    public CommandServiceImpl() {
        commandHandlers.put(BotCommand.START, this::processStartCommand);
        commandHandlers.put(BotCommand.WEATHER, this::processWeatherCommand);
        commandHandlers.put(BotCommand.CHAT, this::processChatCommand);
        commandHandlers.put(BotCommand.HELP, this::processHelpCommand);
        commandHandlers.put(BotCommand.ADD_USER, this::processAddUserCommand);
        commandHandlers.put(BotCommand.RM_USER, this::processRemoveUserCommand);
    }

    @Override
    public Boolean itsCommand(Update update){
        return Arrays.stream(BotCommand.values())
                .map(BotCommand::getCommand)
                .anyMatch(update.getMessage().getText()::startsWith);
    }

    @Override
    public SendMessage processCommand(Update update) {
        String commandText = update.getMessage().getText();

        Optional<SendMessage> result = Arrays.stream(BotCommand.values())
                .filter(cmd -> commandText.startsWith(cmd.getCommand()))
                .map(cmd -> commandHandlers.get(cmd).apply(update.getMessage()))
                .findFirst();

        return result.orElse(errorMessage(update.getMessage()));
    }

    private SendMessage processStartCommand(Message message) {
        return null;
    }

    private SendMessage processChatCommand(Message message) {
        return null;
    }

    private SendMessage processHelpCommand(Message message) {
        return null;
    }

    private SendMessage processWeatherCommand(Message message) {
        return null;
    }

    private SendMessage processAddUserCommand(Message message) {
        return null;
    }

    private SendMessage processRemoveUserCommand(Message message) {
        return null;
    }

    private SendMessage errorMessage(Message msg) {
        SendMessage toSend = new SendMessage();
        toSend.setChatId(msg.getChatId());
        toSend.setText("Ошибка обработки команды: " + msg.getText());
        return toSend;
    }
}

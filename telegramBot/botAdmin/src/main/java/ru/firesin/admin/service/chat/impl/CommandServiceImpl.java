package ru.firesin.admin.service.chat.impl;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.firesin.admin.service.chat.CommandService;
import ru.firesin.admin.utils.BotCommands;
import ru.firesin.users.UserService;
import ru.firesin.utils.MessageUtilsImpl;

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

    private final Map<BotCommands, Function<Message, SendMessage>> commandHandlers = new EnumMap<>(BotCommands.class);
    private final UserService userService;

    private final MessageUtilsImpl messageUtils;

    public CommandServiceImpl(UserService userService, MessageUtilsImpl messageUtils) {
        this.userService = userService;
        this.messageUtils = messageUtils;
        commandHandlers.put(BotCommands.START, this::processStartCommand);
        commandHandlers.put(BotCommands.LS_USER, this::processLsCommand);
        commandHandlers.put(BotCommands.RM_USER, this::processRemoveUserCommand);
        commandHandlers.put(BotCommands.ADD_USER, this::processAddUserCommand);
    }

    @Override
    public Boolean itsCommand(Update update){
        return Arrays.stream(BotCommands.values())
                .map(BotCommands::getCommand)
                .anyMatch(update.getMessage().getText()::startsWith);
    }

    @Override
    public SendMessage processCommand(Update update) {
        String commandText = update.getMessage().getText();

        Optional<SendMessage> result = Arrays.stream(BotCommands.values())
                .filter(cmd -> commandText.startsWith(cmd.getCommand()))
                .map(cmd -> commandHandlers.get(cmd).apply(update.getMessage()))
                .findFirst();

        return result.orElse(messageUtils.generateSendMessage(update, "Ошибка обработки команды."));
    }

    private SendMessage processLsCommand(Message message) {
        return messageUtils.generateSendMessage(message, "ls");
    }

    private SendMessage processAddUserCommand(Message message) {
        return messageUtils.generateSendMessage(message, "Add");
    }

    private SendMessage processStartCommand(Message message) {
        var user = userService.findUser(message.getFrom());
        user.setUsername(message.getFrom().getUserName());
        userService.saveUser(user);
        return messageUtils.generateSendMessage(message, "Добро пожаловать!");
    }

    private SendMessage processRemoveUserCommand(Message message) {
        return messageUtils.generateSendMessage(message, "rm");
    }

    private SendMessage errorMessage(Message msg) {
        SendMessage toSend = new SendMessage();
        toSend.setChatId(msg.getChatId());
        toSend.setText("Ошибка обработки команды: ");
        return toSend;
    }
}

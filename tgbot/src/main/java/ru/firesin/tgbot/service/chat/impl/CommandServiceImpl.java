package ru.firesin.tgbot.service.chat.impl;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.firesin.tgbot.service.chat.CommandService;
import ru.firesin.users.UserService;
import ru.firesin.tgbot.utils.BotCommands;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static ru.firesin.users.enums.UserState.CHAT;
import static ru.firesin.users.enums.UserState.WEATHER;

/**
 * Author:    firesin
 * Date:      26.10.2023
 */

@Service
public class CommandServiceImpl implements CommandService {

    private final Map<BotCommands, Function<Message, SendMessage>> commandHandlers = new EnumMap<>(BotCommands.class);
    private final UserService userService;

    public CommandServiceImpl(UserService userService) {
        this.userService = userService;
        commandHandlers.put(BotCommands.START, this::processStartCommand);
        commandHandlers.put(BotCommands.WEATHER, this::processWeatherCommand);
        commandHandlers.put(BotCommands.CHAT, this::processChatCommand);
        commandHandlers.put(BotCommands.HELP, this::processHelpCommand);
        commandHandlers.put(BotCommands.RM_USER, this::processRemoveUserCommand);
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

        return result.orElse(errorMessage(update.getMessage()));
    }

    private SendMessage processStartCommand(Message message) {
        var user = userService.findUser(message.getFrom());
        user.setUsername(message.getFrom().getUserName());
        userService.saveUser(user);
        return successMessage(message, "Добро пожаловать! \nВы сейчас находитесь в боте общения. Чтобы сменить бота, введите команду или тыкните в меню.");
    }

    private SendMessage processChatCommand(Message message) {
        userService.setState(message.getFrom(), CHAT);
        return successMessage(message, "Переключен на чат. Просто пишите что хотите узнать");
    }

    private SendMessage processHelpCommand(Message message) {
        var help = new StringBuilder();
        help.append("Список всех комманд:\n");
        for (var cmd : BotCommands.getUserCommands()) {
            help.append(cmd.getCommand())
                .append(" - ")
                .append(cmd.getDescription())
                .append("\n");
        }
        return successMessage(message, help.toString());
    }

    private SendMessage processWeatherCommand(Message message) {
        userService.setState(message.getFrom(), WEATHER);
        return successMessage(message, "Переключен на погоду.\nЧтобы узнать погоду напишите город.");
    }

    private SendMessage processRemoveUserCommand(Message message) {
        return null;
    }

    private SendMessage errorMessage(Message msg) {
        SendMessage toSend = new SendMessage();
        toSend.setChatId(msg.getChatId());
        toSend.setText("Ошибка обработки команды: ");
        return toSend;
    }

    private SendMessage successMessage(Message msg, String text) {
        SendMessage toSend = new SendMessage();
        toSend.setChatId(msg.getChatId());
        toSend.setText(text);
        return toSend;
    }
}

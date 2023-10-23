package ru.firesin.tgbot.config;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Author:    firesin
 * Date:      19.10.2023
 */

@Getter
@Component
public class CommandConfig {
    private final List<BotCommand> botCommands;

    public CommandConfig() {
        botCommands = new ArrayList<>();
        botCommands.add(new BotCommand("/weather", "Канал запроса погоды"));
        botCommands.add(new BotCommand("/chat", "Канал общения с чатом"));
        botCommands.add(new BotCommand("/help", "Где я, кто я..."));
    }

    public Optional<BotCommand> itsCommand(Update update){
        return botCommands.stream()
                .filter(cmd -> cmd.getCommand().equals(update.getMessage().getText()))
                .findFirst();
    }
}

package ru.firesin.tgbot.utils;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:    firesin
 * Date:      19.10.2023
 */

@Getter
@Component
public class CommandConfiguration {
    private final List<BotCommand> botCommands;

    public CommandConfiguration() {
        botCommands = new ArrayList<>();
        botCommands.add(new BotCommand("/weather", "Канал запроса погоды"));
        botCommands.add(new BotCommand("/chat", "Канал общения с чатом"));
        botCommands.add(new BotCommand("/help", "Где я, кто я..."));
    }

}

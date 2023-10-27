package ru.firesin.tgbot.utils;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:    firesin
 * Date:      26.10.2023
 */

@Getter
public enum BotCommands {
    START("/start", "Инициализация бота"),
    WEATHER("/weather", "Переключение на канал погоды"),
    CHAT("/chat", "Переключение на канал чата"),
    HELP("/help", "Тыкни для информации"),
    ADD_USER("/add_user", "Добавить пользователя"),
    RM_USER("/rm_user", "Удалить пользователя");

    private final String command;
    private final String description;

    BotCommands(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public static List<BotCommands> getUserCommands() {
        List<BotCommands> commands = new ArrayList<>();
        commands.add(START);
        commands.add(WEATHER);
        commands.add(CHAT);
        commands.add(HELP);
        return commands;
    }

}

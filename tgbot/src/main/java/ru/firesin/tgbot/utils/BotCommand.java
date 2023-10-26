package ru.firesin.tgbot.utils;

/**
 * Author:    firesin
 * Date:      26.10.2023
 */
public enum BotCommand {
    START("/start"),
    WEATHER("/weather"),
    CHAT("/chat"),
    HELP("/help"),
    ADD_USER("/add_user"),
    RM_USER("/rm_user");

    private final String command;

    BotCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}

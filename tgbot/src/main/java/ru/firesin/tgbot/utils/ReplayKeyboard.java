package ru.firesin.tgbot.utils;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collections;

/**
 * Author:    firesin
 * Date:      26.10.2023
 */

@Component
@Getter
public class ReplayKeyboard {

    private final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    public ReplayKeyboard() {
        replyKeyboardMarkup.setResizeKeyboard(true);

        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("Вариант 1"));
        row.add(new KeyboardButton("Вариант 2"));
        row.add(new KeyboardButton("Вариант 3"));

        replyKeyboardMarkup.setKeyboard(Collections.singletonList(row));
    }
}

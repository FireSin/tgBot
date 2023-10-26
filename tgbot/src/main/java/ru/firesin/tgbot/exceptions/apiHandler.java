package ru.firesin.tgbot.exceptions;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.firesin.tgbot.controller.UpdateProcessor;

/**
 * Author:    firesin
 * Date:      26.10.2023
 */

@ControllerAdvice
@AllArgsConstructor
public class apiHandler {

    private UpdateProcessor updateProcessor;

    @ExceptionHandler(MuExp.class)
    public void myApiHandler(MuExp e) {
        var tmp = new SendMessage();
        tmp.setChatId(e.getId());
        tmp.setText(e.getTxt());
        updateProcessor.setView(tmp);
    }
}

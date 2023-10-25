package ru.firesin.tgbot.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Author:    firesin
 * Date:      24.10.2023
 */

@RestController
@AllArgsConstructor
public class WebHookController {

    private final TelegramBot telegramBot;

    @RequestMapping(value = "/callback/update", method = RequestMethod.POST)
    public ResponseEntity<?> onUpdateReceived(@RequestBody Update update){
        telegramBot.onWebhookUpdateReceived(update);
        return ResponseEntity.ok().build();
    }
}

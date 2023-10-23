package ru.firesin.tgbot.service.users.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.firesin.feature.jpa.dao.BotUserDAO;
import ru.firesin.tgbot.service.users.UserService;

/**
 * Author:    firesin
 * Date:      23.10.2023
 */

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final BotUserDAO botUserDAO;
    @Override
    public Boolean check(Update update) {
        return botUserDAO.findBotUserById(update.getMessage().getFrom().getId()) != null;
    }

    @Override
    public void addUser(Update update) {

    }

    @Override
    public void deleteUser(Update update) {

    }
}

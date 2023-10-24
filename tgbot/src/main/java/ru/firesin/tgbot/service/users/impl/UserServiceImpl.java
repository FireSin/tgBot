package ru.firesin.tgbot.service.users.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.firesin.tgbot.jpa.BotUser;
import ru.firesin.tgbot.jpa.dao.BotUserDAO;
import ru.firesin.tgbot.service.users.UserService;

import static ru.firesin.tgbot.jpa.enums.UserState.CHAT;

/**
 * Author:    firesin
 * Date:      23.10.2023
 */

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final BotUserDAO botUserDAO;
    @Override
    public Boolean check(User botUser) {
        return botUserDAO.findBotUserById(botUser.getId()) != null;
    }

    @Override
    public BotUser addUser(User botUser) {
        BotUser user = botUserDAO.findBotUserById(botUser.getId());

        return user != null ? user : botUserDAO.save(BotUser.builder()
                .id(botUser.getId())
                .firstname(botUser.getFirstName())
                .lastname(botUser.getLastName())
                .username(botUser.getUserName())
                .userState(CHAT)
                .build());
    }

    @Override
    public void deleteUser(User botUser) {
        BotUser user = botUserDAO.findBotUserById(botUser.getId());
        if (user != null) {
            botUserDAO.delete(user);
        }
    }
}

package ru.firesin.tgbot.service.users.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.firesin.tgbot.jpa.BotUser;
import ru.firesin.tgbot.jpa.dao.BotUserDAO;
import ru.firesin.tgbot.jpa.enums.UserRole;
import ru.firesin.tgbot.jpa.enums.UserState;
import ru.firesin.tgbot.service.users.UserService;

import static ru.firesin.tgbot.jpa.enums.UserRole.USER;
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
        return botUserDAO.save(BotUser.builder()
                .id(botUser.getId())
                .firstname(botUser.getFirstName())
                .lastname(botUser.getLastName())
                .username(botUser.getUserName())
                .userRole(USER)
                .userState(CHAT)
                .build());
    }

    @Override
    public void deleteUser(User botUser) {
        botUserDAO.deleteBotUserById(botUser.getId());
    }

    public UserRole getRole(User botUser) {
        return botUserDAO.findUserRoleById(botUser.getId());
    }

    @Override
    public BotUser addUser(Contact contact) {
        BotUser user = botUserDAO.findBotUserById(contact.getUserId());

        return user != null ? user : botUserDAO.save(BotUser.builder()
                .id(contact.getUserId())
                .firstname(contact.getFirstName())
                .lastname(contact.getLastName())
                .userRole(USER)
                .userState(CHAT)
                .build());
    }

    @Override
    public BotUser setState(User botUser, UserState userState) {
        BotUser user = botUserDAO.findBotUserById(botUser.getId());
        user.setUserState(userState);
        return botUserDAO.save(user);
    }
}

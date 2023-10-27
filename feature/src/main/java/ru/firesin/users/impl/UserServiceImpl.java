package ru.firesin.users.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.firesin.users.UserService;
import ru.firesin.users.entity.BotUser;
import ru.firesin.users.repository.BotUserDAO;
import ru.firesin.users.enums.UserRole;
import ru.firesin.users.enums.UserState;

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
                .userRole(UserRole.USER)
                .userState(UserState.CHAT)
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
                .userRole(UserRole.USER)
                .userState(UserState.CHAT)
                .build());
    }

    @Override
    public BotUser setState(User botUser, UserState userState) {
        BotUser user = botUserDAO.findBotUserById(botUser.getId());
        user.setUserState(userState);
        return botUserDAO.save(user);
    }

    @Override
    public BotUser findUser(User botUser) {
        return botUserDAO.findBotUserById(botUser.getId());
    }

    @Override
    public void saveUser(BotUser botUser) {
        botUserDAO.save(botUser);
    }
}

package ru.firesin.tgbot.service.users;

import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.firesin.tgbot.jpa.BotUser;
import ru.firesin.tgbot.jpa.enums.UserRole;
import ru.firesin.tgbot.jpa.enums.UserState;

/**
 * Author:    firesin
 * Date:      23.10.2023
 */
public interface UserService {
    Boolean check(User botUser);

    BotUser addUser(User botUser);

    void deleteUser(User botUser);

    UserRole getRole(User botUser);

    BotUser addUser(Contact contact);

    BotUser setState(User botUser, UserState userState);
}

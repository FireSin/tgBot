package ru.firesin.users;

import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.firesin.users.entity.BotUser;
import ru.firesin.users.enums.UserRole;
import ru.firesin.users.enums.UserState;

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

    BotUser findUser(User botUser);

    void saveUser(BotUser botUser);
}

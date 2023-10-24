package ru.firesin.tgbot.service.users;

import org.telegram.telegrambots.meta.api.objects.User;
import ru.firesin.tgbot.jpa.BotUser;

/**
 * Author:    firesin
 * Date:      23.10.2023
 */
public interface UserService {
    Boolean check(User botUser);

    BotUser addUser(User botUser);

    void deleteUser(User botUser);
}

package ru.firesin.tgbot.service.users;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Author:    firesin
 * Date:      23.10.2023
 */
public interface UserService {
    Boolean check(Update update);
    void addUser(Update update);

    void deleteUser(Update update);
}

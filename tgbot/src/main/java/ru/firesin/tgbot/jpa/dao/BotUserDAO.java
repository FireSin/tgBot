package ru.firesin.tgbot.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.firesin.tgbot.jpa.BotUser;

/**
 * Author:    firesin
 * Date:      23.10.2023
 */

public interface BotUserDAO extends JpaRepository<BotUser, Long> {
    public BotUser findBotUserById(Long id);
}
